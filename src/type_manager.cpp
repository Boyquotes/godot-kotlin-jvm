#include "type_manager.h"

#include <core/io/resource_loader.h>

JNI_INIT_STATICS_FOR_CLASS(TypeManager, INIT_NATIVE_METHOD("getMethodBindPtr$godot_library", "(Ljava/lang/String;Ljava/lang/String;)J", TypeManager::get_method_bind_ptr))

void TypeManager::clear() {
    engine_type_names.clear();
    java_engine_types_constructors.clear();
    engine_singleton_names.clear();
    named_user_scripts.clear();
    named_user_scripts_map.clear();
    path_user_scripts.clear();
    ;
    filepath_to_name_map.clear();
}

int TypeManager::get_java_engine_type_constructor_index_for_type(const StringName& p_type_name) const {
    return java_engine_types_constructors[p_type_name];
}

bool TypeManager::java_engine_type_constructor_for_type_exists(const StringName& p_type_name) const {
    return java_engine_types_constructors.has(p_type_name);
}

const StringName& TypeManager::get_engine_type_for_index(int p_index) const {
    return engine_type_names[p_index];
}

const String& TypeManager::get_engine_singleton_name_for_index(int p_index) const {
    return engine_singleton_names[p_index];
}

const Ref<KotlinScript>& TypeManager::get_user_script_for_index(int p_index) const {
    // No check. Meant to be a fast operation
    return named_user_scripts[p_index];
}

const Ref<KotlinScript> TypeManager::get_user_script_from_name(StringName name) const {
    if (HashMap<StringName, Ref<KotlinScript>>::ConstIterator element = named_user_scripts_map.find(name)) {
        return element->value;
    }
    return Ref<KotlinScript>();
}

void TypeManager::register_engine_types(jni::Env& p_env, jni::JObjectArray& p_engine_types) {
    for (int i = 0; i < p_engine_types.length(p_env); ++i) {
        jni::JObject type = p_engine_types.get(p_env, i);
        const String& class_name = p_env.from_jstring(static_cast<jni::JString>(type));
        engine_type_names.insert(i, class_name);
        java_engine_types_constructors[class_name] = i;
#ifdef DEV_ENABLED
        LOG_VERBOSE(vformat("Registered %s engine type with index %s.", class_name, i));
#endif
        type.delete_local_ref(p_env);
    }
}

void TypeManager::register_engine_singletons(jni::Env& p_env, jni::JObjectArray& p_singletons) {
    for (int i = 0; i < p_singletons.length(p_env); ++i) {
        jni::JObject name = p_singletons.get(p_env, i);
        const String& singleton_name {p_env.from_jstring(static_cast<jni::JString>(name))};
        engine_singleton_names.insert(i, singleton_name);
        name.delete_local_ref(p_env);
    }
}

void TypeManager::create_and_update_scripts(Vector<KtClass*>& classes) {
    Vector<Ref<KotlinScript>> scripts;

    // We first deal with named scripts.
#ifdef TOOLS_ENABLED
    // This tool-only block handles script reloading.
    // We have to compare the previous scripts to the new ones and create/update/delete accordingly

    HashMap<StringName, Ref<KotlinScript>> script_cache = named_user_scripts_map;
    named_user_scripts.clear();
    named_user_scripts_map.clear();
    filepath_to_name_map.clear();

    for (KtClass* kotlin_class : classes) {
        String script_name = kotlin_class->registered_class_name;
        // First check if the scripts already exist
        Ref<KotlinScript> ref = script_cache[script_name];
        if (!ref.is_null()) {
            delete ref->kotlin_class;
            ref->kotlin_class = kotlin_class;

#ifdef DEV_ENABLED
            LOG_VERBOSE(vformat("JVM Script updated: %s", script_name));
#endif
        } else {
            // Script doesn't exist so we create it.
            ref.instantiate();
            ref->kotlin_class = kotlin_class;
#ifdef DEV_ENABLED
            LOG_VERBOSE(vformat("JVM Script created: %s", script_name));
#endif
        }

        ref->set_path(kotlin_class->compilation_time_relative_registration_file_path, true);

        scripts.push_back(ref);
        script_cache.erase(script_name);

        String source_path = "res://" + kotlin_class->relative_source_path;
        if (FileAccess::exists(source_path)) { filepath_to_name_map[source_path] = script_name; }
    }

    // Only scripts left in the cache are the ones that have been removed or placeholders without associated KtClass
    // We simply delete their kotlin_class if they got one
    for (const KeyValue<StringName, Ref<KotlinScript>>& keyValue : script_cache) {
        Ref<KotlinScript> ref = keyValue.value;
        if (ref->kotlin_class) {
#ifdef DEV_ENABLED
            LOG_VERBOSE(vformat("JVM Script deleted: %s", ref->kotlin_class->registered_class_name));
#endif
            delete ref->kotlin_class;
            ref->kotlin_class = nullptr;
        }

        // We only add them back if they are in use, otherwise we let the Script die.
        if (!ref->placeholders.is_empty()) { scripts.push_back(ref); }
    }

#else
    // This part handles the first and only loading of script happening in exports.
    // It assumes that all scripts are properly built into the .jar and won't be reloaded.
#ifdef DEBUG_ENABLED
    JVM_ERR_FAIL_COND_MSG(named_user_scripts.size() != 0, "JVM scripts are being initialized more than once.");
#endif

    for (KtClass* kotlin_class : classes) {
        Ref<KotlinScript> ref;
        ref.instantiate();
        ref->kotlin_class = kotlin_class;
        ref->mode = KotlinScript::AccessMode::NAME;
        ref->set_path(kotlin_class->compilation_time_relative_registration_file_path, true);
        scripts.push_back(ref);
#ifdef DEV_ENABLED
        LOG_VERBOSE(vformat("Kotlin Script created: %s", kotlin_class->registered_class_name));
#endif
    }
#endif

    for (const Ref<KotlinScript>& script : scripts) {
        named_user_scripts.push_back(script);
        named_user_scripts_map[script->get_global_name()] = script;
    }

    // Now we deal with path script.
    Vector<Ref<KotlinScript>> path_script_cache = path_user_scripts;
    path_user_scripts.clear();
    for (Ref<KotlinScript>& script : path_script_cache) {
        String path = script->get_path();
        // No need to delete the KotlinClass, it has already been done with the namedScript that shares it.
        script->kotlin_class = nullptr;
        if (filepath_to_name_map.has(path)) {
            script->kotlin_class = named_user_scripts_map[filepath_to_name_map[path]]->kotlin_class;
            path_user_scripts.push_back(script);
        }
    }

#ifdef TOOLS_ENABLED
    for (const Ref<KotlinScript>& script : named_user_scripts) {
        // We have to delay the update_export. The engine is not fully initialized and scripts can cause undefined behaviors.
        MessageQueue::get_singleton()->push_callable(callable_mp(script.ptr(), &KotlinScript::update_exports));
    }
    for (const Ref<KotlinScript>& script : path_user_scripts) {
        // We have to delay the update_export. The engine is not fully initialized and scripts can cause undefined behaviors.
        MessageQueue::get_singleton()->push_callable(callable_mp(script.ptr(), &KotlinScript::update_exports));
    }
#endif
}

uintptr_t TypeManager::get_method_bind_ptr(JNIEnv* p_raw_env, jobject j_instance, jstring p_class_name, jstring p_method_name) {
    jni::Env env {p_raw_env};
    String class_name {env.from_jstring(jni::JString(p_class_name))};
    String method_name {env.from_jstring(jni::JString(p_method_name))};
    return reinterpret_cast<uintptr_t>(ClassDB::get_method(class_name, method_name));
}

TypeManager* TypeManager::init() {
    jni::Env env {jni::Jvm::current_env()};
    jni::JObject class_loader {ClassLoader::get_default_loader()};

    jni::JClass type_manager_cls {env.load_class("godot.core.TypeManager", class_loader)};
    jni::FieldId instance_field {type_manager_cls.get_static_field_id(env, "INSTANCE", "Lgodot/core/TypeManager;")};
    jni::JObject type_manager_instance {type_manager_cls.get_static_object_field(env, instance_field)};
    JVM_CRASH_COND_MSG(type_manager_instance.is_null(), "Failed to retreive TypeManager instance");

    auto* native_instance = new TypeManager(type_manager_instance);
    type_manager_cls.delete_local_ref(env);
    return native_instance;
}

TypeManager::TypeManager(jni::JObject p_wrapped) : JavaSingletonWrapper<TypeManager>(p_wrapped) {}