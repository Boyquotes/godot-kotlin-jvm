package godot.intellij.plugin.extension

import godot.gradle.projectExt.godotJvmExtension
import godot.tools.common.constants.GodotKotlinJvmTypes
import org.gradle.api.Project
import org.jetbrains.kotlin.idea.util.findAnnotation
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtValueArgumentList
import org.jetbrains.kotlin.psi.KtValueArgumentName

/**
 * Gets custom name defined in `@RegisterClass` annotation if defined, fqName or simple name (depending on the gradle setting) otherwise
 *
 * @return fqName to registered class name or `null` if the classes fqName cannot be resolved
 */
fun KtClass.getRegisteredClassName(): Pair<String, String>? {
    val isFqNameRegistrationEnabled = false //project.godotJvmExtension.isAndroidExportEnabled.getOrElse(false) // FIXME after merge of https://github.com/utopia-rise/godot-kotlin-jvm/pull/441

    // the whole `@RegisterClass(...)` annotation
    val ktAnnotationEntry = annotationEntries
        .firstOrNull { it.shortName?.asString() == GodotKotlinJvmTypes.Annotations.registerClass }

    val fqName = fqName?.asString()

    if (ktAnnotationEntry == null || fqName == null) {
        return null
    }

    val simpleName = fqName.substringAfterLast(".")

    val lastChildOfRegisterClassAnnotation = ktAnnotationEntry.lastChild
    val customName = if (lastChildOfRegisterClassAnnotation is KtValueArgumentList) { // if (...) present in `@RegisterClass(...)`
        lastChildOfRegisterClassAnnotation
            .children
            .firstOrNull { it.firstChild is KtValueArgumentName && it.firstChild.text == "className" } // named; position not relevant
            ?.children
            ?.lastOrNull()
            ?.text
            ?.removeSurrounding("\"")
            ?: lastChildOfRegisterClassAnnotation
                .children
                .firstOrNull() // not named; first position
                ?.text
                ?.removeSurrounding("\"")
    } else { // just registered as `@RegisterClass` without constructor params
        null
    }

    val registeredClassName = customName ?: if (isFqNameRegistrationEnabled) {
        fqName
    } else {
        simpleName
    }

    return fqName to registeredClassName
}

fun KtClass.anyFunctionHasAnnotation(annotationFqName: String) = this
    .declarations
    .any { declaration ->
        declaration.findAnnotation(FqName(annotationFqName)) != null
    }

fun KtClass.anyPropertyHasAnnotation(annotationFqName: String) = this
    .getProperties()
    .any { ktProperty ->
        ktProperty.findAnnotation(FqName(annotationFqName)) != null
    }
