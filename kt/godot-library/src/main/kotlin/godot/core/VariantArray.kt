package godot.core

import godot.annotation.CoreTypeHelper
import godot.core.memory.MemoryManager
import godot.core.memory.TransferContext
import godot.util.IndexedIterator
import godot.util.VoidPtr
import kotlin.reflect.KClass


@Suppress("unused", "UNCHECKED_CAST")
class VariantArray<T> : NativeCoreType, MutableCollection<T> {

    internal var variantType: VariantType = VariantType.NIL

    @PublishedApi
    internal constructor(handle: VoidPtr) {
        variantType = VariantType.ANY
        _handle = handle
        MemoryManager.registerNativeCoreType(this, VariantType.ARRAY)
    }

    @PublishedApi
    internal constructor(variantType: VariantType, parameterClazz: KClass<*>) {
        this.variantType = variantType
        _handle = if (variantType != VariantType.ANY) {
            TransferContext.writeArguments(
                VariantType.LONG to variantType.id,
                VariantType.JVM_INT to (TypeManager.engineTypeToId[parameterClazz] ?: -1),
                VariantType.JVM_INT to (TypeManager.userTypeToId[parameterClazz] ?: -1)
            )
            Bridge.engine_call_constructor_typed()
        } else {
            Bridge.engine_call_constructor()
        }
        MemoryManager.registerNativeCoreType(this, VariantType.ARRAY)
    }

//########################PUBLIC###############################
    //PROPERTIES
    /**
     * Returns the number of elements in the array.
     */
    override val size: Int
        get() {
            Bridge.engine_call_size(_handle)
            return TransferContext.readReturnValue(VariantType.JVM_INT) as Int
        }

    /**
     * Create a shallow copy of the Array
     */
    constructor(other: VariantArray<T>) {
        this.variantType = other.variantType
        this._handle = other._handle
        MemoryManager.registerNativeCoreType(this, VariantType.ARRAY)
    }

    //COMMON API
    override fun add(element: T): Boolean {
        append(element)
        return true
    }

    override fun addAll(elements: Collection<T>): Boolean {
        elements.forEach {
            append(it)
        }
        return true
    }

    /**
     * Clears the array. This is equivalent to using resize with a size of 0.
     */
    override fun clear() {
        Bridge.engine_call_clear(_handle)
    }

    /**
     * Returns true if the array is empty.
     */
    override fun isEmpty(): Boolean {
        Bridge.engine_call_isEmpty(_handle)
        return TransferContext.readReturnValue(VariantType.BOOL) as Boolean
    }

    /**
     * Returns true if the array is read-only.
     */
    fun isReadOnly(): Boolean {
        Bridge.engine_call_isReadOnly(_handle)
        return TransferContext.readReturnValue(VariantType.BOOL) as Boolean
    }

    /**
     * Returns `true` if the array is typed. Typed arrays can only store elements of their associated type and provide
     * type safety for the [get] operator.
     */
    fun isTyped(): Boolean {
        Bridge.engine_call_isTyped(_handle)
        return TransferContext.readReturnValue(VariantType.BOOL) as Boolean
    }

    /**
     * Returns a hashed integer value representing the array contents.
     */
    fun hash(): Int {
        Bridge.engine_call_hash(_handle)
        return TransferContext.readReturnValue(VariantType.JVM_INT) as Int
    }

    /**
     * Reverses the order of the elements in the array.
     */
    fun invert() {
        Bridge.engine_call_reverse(_handle)
    }

    override fun remove(element: T): Boolean {
        if (has(element)) {
            erase(element)
            return true
        }

        return false
    }

    /**
     * Removes an element from the array by index.
     */
    fun remove(position: Int) {
        TransferContext.writeArguments(VariantType.JVM_INT to position)
        Bridge.engine_call_removeAt(_handle)
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        var ret = false
        elements.forEach {
            ret = remove(it) || ret
        }
        return ret
    }

    /**
     * Resizes the array to contain a different number of elements.
     * If the array size is smaller, elements are cleared, if bigger, new elements are null.
     */
    fun resize(size: Int) {
        TransferContext.writeArguments(VariantType.JVM_INT to size)
        Bridge.engine_call_resize(_handle)
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        var ret = false
        val iter = this.iterator()
        while (iter.hasNext()) {
            val value = iter.next()
            if (value !in elements) {
                iter.remove()
                ret = true
            }
        }
        return ret
    }

    /**
     * Shuffles the array such that the items will have a random order.
     * This method uses the global random number generator common to methods such as @randi.
     * Call @randomize to ensure that a new seed will be used each time if you want non-reproducible shuffling.
     */
    fun shuffle() {
        Bridge.engine_call_shuffle(_handle)
    }


    /**
     * Sorts the array.
     */
    fun sort() {
        Bridge.engine_call_sort(_handle)
    }

    /**
     * Sorts the array using a custom method. The arguments are an object that holds the method and the name of such method.
     * The custom method receives two arguments (a pair of elements from the array) and must return either true or false.
     */
    fun sortCustom(obj: KtObject, func: String) {
        TransferContext.writeArguments(VariantType.OBJECT to obj, VariantType.STRING to func)
        Bridge.engine_call_sortCustom(_handle)
    }

    //API

    /**
     * Calls the provided [Callable] on each element in the array and returns `true` if the Callable returns `true` for
     * all elements in the array. If the [Callable] returns `false` for one array element or more, this method returns
     * `false`.
     *
     * The callable's method should take one Variant parameter (the current array element) and return a boolean value.
     *
     * See also [any], [filter], [map] and [reduce].
     *
     * **Note**: Unlike relying on the size of an array returned by [filter], this method will return as early as possible
     * to improve performance (especially with large arrays).
     *
     * **Note**: For an empty array, this method always returns `true`.
     */
    fun all(callable: Callable): Boolean {
        TransferContext.writeArguments(VariantType.CALLABLE to callable)
        Bridge.engine_call_all(_handle)
        return TransferContext.readReturnValue(VariantType.BOOL) as Boolean
    }

    /**
     * Calls the provided [Callable] on each element in the array and returns `true` if the Callable returns `true` for
     * one or more elements in the array. If the [Callable] returns `false` for all elements in the array, this method
     * returns `false`.
     *
     * The callable's method should take one Variant parameter (the current array element) and return a boolean value.
     *
     * See also [all], [filter], [map] and [reduce].
     *
     * Note: Unlike relying on the size of an array returned by filter, this method will return as early as possible to
     * improve performance (especially with large arrays).
     *
     * Note: For an empty array, this method always returns `false`.
     */
    fun any(callable: Callable): Boolean {
        TransferContext.writeArguments(VariantType.CALLABLE to callable)
        Bridge.engine_call_any(_handle)
        return TransferContext.readReturnValue(VariantType.BOOL) as Boolean
    }

    /**
     * Appends an element at the end of the array (alias of push_back).
     */
    fun append(value: T) {
        TransferContext.writeArguments(variantType to value)
        Bridge.engine_call_append(_handle)
    }

    /**
     * Appends another array at the end of this array.
     */
    fun appendArray(array: VariantArray<T>) {
        TransferContext.writeArguments(VariantType.ARRAY to array)
        Bridge.engine_call_appendArray(_handle)
    }

    /**
     * Returns the last element of the array. Prints an error and returns `null` if the array is empty.
     */
    fun back(): T {
        Bridge.engine_call_back(_handle)
        return TransferContext.readReturnValue(variantType) as T
    }

    /**
     * Finds the index of an existing value (or the insertion index that maintains sorting order, if the value is not yet present in the array) using binary search.
     * Optionally, a before specifier can be passed.
     * If false, the returned index comes after all existing entries of the value in the array.
     * Note: Calling bsearch on an unsorted array results in unexpected behavior.
     */
    @JvmOverloads
    fun bsearch(value: T, before: Boolean = true): Int {
        TransferContext.writeArguments(variantType to value, VariantType.BOOL to before)
        Bridge.engine_call_bsearch(_handle)
        return TransferContext.readReturnValue(VariantType.JVM_INT) as Int
    }

    /**
     * Finds the index of an existing value (or the insertion index that maintains sorting order, if the value is not yet present in the array) using binary search and a custom comparison method.
     * Optionally, a before specifier can be passed.
     * If false, the returned index comes after all existing entries of the value in the array.
     * The custom method receives two arguments (an element from the array and the value searched for) and must return true if the first argument is less than the second, and return false otherwise.
     * Note: Calling bsearch on an unsorted array results in unexpected behavior.
     */
    @JvmOverloads
    fun bsearchCustom(value: T, obj: KtObject, func: String, before: Boolean = true): Int {
        TransferContext.writeArguments(
            variantType to value, VariantType.OBJECT to obj,
            VariantType.STRING to func, VariantType.BOOL to before
        )
        Bridge.engine_call_bsearchCustom(_handle)
        return TransferContext.readReturnValue(VariantType.JVM_INT) as Int
    }

    /**
     * Returns the number of times an element is in the array.
     */
    fun count(value: T): Int {
        TransferContext.writeArguments(variantType to value)
        Bridge.engine_call_count(_handle)
        return TransferContext.readReturnValue(VariantType.JVM_INT) as Int
    }


    /**
     * Returns a copy of the array.
     * If deep is true, a deep copy is performed:
     * all nested arrays and dictionaries are duplicated and will not be shared with the original array.
     * If false, a shallow copy is made and references to the original nested arrays and dictionaries are kept, so that modifying a sub-array or dictionary in the copy will also impact those referenced in the source array.
     */
    fun duplicate(deep: Boolean): VariantArray<T> {
        TransferContext.writeArguments(VariantType.BOOL to deep)
        Bridge.engine_call_duplicate(_handle)
        return (TransferContext.readReturnValue(VariantType.ARRAY) as VariantArray<T>).also {
            it.variantType = variantType
        }
    }

    /**
     * Removes the first occurrence of a value from the array.
     */
    fun erase(value: T) {
        TransferContext.writeArguments(variantType to value)
        Bridge.engine_call_erase(_handle)
    }

    /**
     * Assigns the given value to all elements in the array. This can typically be used together with [resize] to create
     * an array with a given size and initialized elements.
     *
     * **Note**: If value is of a reference type ([Object]-derived, [VariantArray], [Dictionary], etc.) then the array is
     * filled with the references to the same object, i.e. no duplicates are created.
     */
    fun fill(value: T) {
        TransferContext.writeArguments(variantType to value)
        Bridge.engine_call_fill(_handle)
    }

    /**
     * Calls the provided [Callable] on each element in the array and returns a new array with the elements for which
     * the method returned `true`.
     *
     * The callable's method should take one Variant parameter (the current array element) and return a boolean value.
     *
     * See also [any], [all], [map] and [reduce].
     */
    fun filter(callable: Callable) : VariantArray<T> {
        TransferContext.writeArguments(VariantType.CALLABLE to callable)
        Bridge.engine_call_filter(_handle)
        return (TransferContext.readReturnValue(VariantType.ARRAY) as VariantArray<T>).also {
            it.variantType = variantType
        }
    }

    /**
     * Searches the array for a value and returns its index or -1 if not found.
     * Optionally, the initial search index can be passed.
     */
    fun find(what: T, from: Int): Int {
        TransferContext.writeArguments(variantType to what, VariantType.JVM_INT to from)
        Bridge.engine_call_find(_handle)
        return TransferContext.readReturnValue(VariantType.JVM_INT) as Int
    }

    /**
     * Returns the first element of the array, or null if the array is empty.
     */
    fun front(): T {
        Bridge.engine_call_front(_handle)
        return TransferContext.readReturnValue(variantType, true) as T
    }

    /**
     * Returns the [VariantType] constant for a typed array. If the [VariantArray] is not typed, returns [VariantType.NIL].
     */
    fun getTypedBuiltin() = variantType.baseOrdinal

    /**
     * Returns a class name of a typed [VariantArray] of type [VariantType.OBJECT].
     */
    fun getTypedClassName(): StringName {
        Bridge.engine_call_getTypedClassName(_handle)
        return TransferContext.readReturnValue(VariantType.STRING_NAME) as StringName
    }

    /**
     * Returns the script associated with a typed array tied to a class name.
     */
    fun getTypedScript(): Any {
        Bridge.engine_call_getTypedScript(_handle)
        return TransferContext.readReturnValue(VariantType.ANY) as Any
    }

    /**
     * Returns true if the array contains the given value.
     */
    fun has(value: T): Boolean {
        TransferContext.writeArguments(variantType to value)
        Bridge.engine_call_has(_handle)
        return TransferContext.readReturnValue(VariantType.BOOL) as Boolean
    }

    /**
     * Inserts a new element at a given position in the array.
     * The position must be valid, or at the end of the array (pos == size()).
     */
    fun insert(position: Int, value: T) {
        TransferContext.writeArguments(VariantType.JVM_INT to position, variantType to value)
        Bridge.engine_call_insert(_handle)
    }

    /**
     * Calls the provided [Callable] for each element in the array and returns a new array filled with values returned
     * by the method.
     *
     * The callable's method should take one Variant parameter (the current array element) and can return any Variant.
     */
    fun map(callable: Callable): VariantArray<Any?> {
        TransferContext.writeArguments(VariantType.CALLABLE to callable)
        Bridge.engine_call_map(_handle)
        return (TransferContext.readReturnValue(VariantType.ARRAY) as VariantArray<Any?>).also {
            it.variantType = VariantType.ANY
        }
    }

    /**
     * Returns the maximum value contained in the array if all elements are of comparable types.
     * If the elements can't be compared, null is returned.
     */
    fun max(): T {
        Bridge.engine_call_max(_handle)
        return TransferContext.readReturnValue(variantType, true) as T
    }

    /**
     * Returns the minimum value contained in the array if all elements are of comparable types.
     * If the elements can't be compared, null is returned.
     */
    fun min(): T {
        Bridge.engine_call_min(_handle)
        return TransferContext.readReturnValue(variantType, true) as T
    }

    /**
     * Returns a random value from the target array.
     */
    fun pickRandom(): T {
        Bridge.engine_call_pickRandom(_handle)
        return TransferContext.readReturnValue(variantType, true) as T
    }

    /**
     * Removes and returns the last element of the array.
     * Returns null if the array is empty.
     */
    fun popBack(): T {
        Bridge.engine_call_popBack(_handle)
        return TransferContext.readReturnValue(variantType, true) as T
    }

    /**
     * Removes and returns the first element of the array.
     * Returns null if the array is empty.
     */
    fun popFront(): T {
        Bridge.engine_call_popFront(_handle)
        return TransferContext.readReturnValue(variantType, true) as T
    }

    /**
     * Appends an element at the end of the array.
     */
    fun pushBack(value: T) {
        TransferContext.writeArguments(variantType to value)
        Bridge.engine_call_pushBack(_handle)
    }

    /**
     * Adds an element at the beginning of the array
     */
    fun pushFront(value: T) {
        TransferContext.writeArguments(variantType to value)
        Bridge.engine_call_pushFront(_handle)
    }

    fun reduce(callable: Callable, accum: Any?): Any? {
        TransferContext.writeArguments(VariantType.CALLABLE to callable, VariantType.ANY to accum)
        Bridge.engine_call_reduce(_handle)
        return TransferContext.readReturnValue(VariantType.ANY, true)
    }

    /**
     * Searches the array in reverse order.
     * Optionally, a start search index can be passed.
     * If negative, the start index is considered relative to the end of the array.
     */
    fun rfind(what: T, from: Int): Int {
        TransferContext.writeArguments(variantType to what, VariantType.JVM_INT to from)
        Bridge.engine_call_rfind(_handle)
        return TransferContext.readReturnValue(VariantType.JVM_INT) as Int
    }

    /**
     * Returns the slice of the [VariantArray], from begin (inclusive) to end (exclusive), as a new [VariantArray].
     *
     * The absolute value of `begin` and `end` will be clamped to the array size, so the default value for `end makes
     * it slice to the size of the array by default (i.e. `arr.slice(1)` is a shorthand for `arr.slice(1, arr.size())`).
     *
     * If either `begin` or `end` are negative, they will be relative to the end of the array (i.e. `arr.slice(0, -2)`
     * is a shorthand for `arr.slice(0, arr.size() - 2)`).
     *
     * If deep is `true`, each element will be copied by value rather than by reference.
     */
    fun slice(begin: Int, end: Int, step: Int, deep: Boolean): VariantArray<T> {
        TransferContext.writeArguments(
            VariantType.JVM_INT to begin, VariantType.JVM_INT to end,
            VariantType.JVM_INT to step, VariantType.BOOL to deep
        )
        Bridge.engine_call_slice(_handle)
        return (TransferContext.readReturnValue(VariantType.ARRAY) as VariantArray<T>).also {
            it.variantType = variantType
        }
    }


    //UTILITIES
    operator fun set(idx: Int, data: T) {
        TransferContext.writeArguments(VariantType.JVM_INT to idx, variantType to data)
        Bridge.engine_call_operator_set(_handle)
    }

    @CoreTypeHelper
    inline fun <R> get(idx: Int, block: T.() -> R): R {
        val localCopy = this[idx]
        val ret = localCopy.block()
        this[idx] = localCopy
        return ret
    }

    operator fun get(idx: Int): T {
        TransferContext.writeArguments(VariantType.JVM_INT to idx)
        Bridge.engine_call_operator_get(_handle)
        return TransferContext.readReturnValue(variantType, true) as T
    }

    operator fun plus(other: T) {
        this.append(other)
    }

    override operator fun contains(element: T) = has(element)

    override fun containsAll(elements: Collection<T>): Boolean {
        elements.forEach {
            if (!has(it)) return false
        }
        return true
    }

    override fun iterator(): MutableIterator<T> {
        return IndexedIterator(this::size, this::get, this::remove)
    }

    /**
     * WARNING: no equals function is available in the Gdnative API for this Coretype.
     * This methods implementation works but is not the fastest one.
     */
    override fun equals(other: Any?): Boolean {
        return if (other is VariantArray<*>) {
            val list1 = this.toList()
            val list2 = other.toList()
            list1 == list2
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return _handle.hashCode()
    }

    override fun toString(): String {
        return "Array(${size})"
    }


    @Suppress("FunctionName")
    private object Bridge {
        external fun engine_call_constructor(): VoidPtr
        external fun engine_call_constructor_typed(): VoidPtr

        external fun engine_call_all(_handle: VoidPtr)
        external fun engine_call_any(_handle: VoidPtr)
        external fun engine_call_append(_handle: VoidPtr)
        external fun engine_call_appendArray(_handle: VoidPtr)
        external fun engine_call_back(_handle: VoidPtr)
        external fun engine_call_bsearch(_handle: VoidPtr)
        external fun engine_call_bsearchCustom(_handle: VoidPtr)
        external fun engine_call_clear(_handle: VoidPtr)
        external fun engine_call_count(_handle: VoidPtr)
        external fun engine_call_duplicate(_handle: VoidPtr)
        external fun engine_call_erase(_handle: VoidPtr)
        external fun engine_call_fill(_handle: VoidPtr)
        external fun engine_call_filter(_handle: VoidPtr)
        external fun engine_call_find(_handle: VoidPtr)
        external fun engine_call_front(_handle: VoidPtr)
        external fun engine_call_getTypedClassName(_handle: VoidPtr)
        external fun engine_call_getTypedScript(_handle: VoidPtr)
        external fun engine_call_has(_handle: VoidPtr)
        external fun engine_call_hash(_handle: VoidPtr)
        external fun engine_call_insert(_handle: VoidPtr)
        external fun engine_call_isEmpty(_handle: VoidPtr)
        external fun engine_call_isReadOnly(_handle: VoidPtr)
        external fun engine_call_isTyped(_handle: VoidPtr)
        external fun engine_call_map(_handle: VoidPtr)
        external fun engine_call_max(_handle: VoidPtr)
        external fun engine_call_min(_handle: VoidPtr)
        external fun engine_call_pickRandom(_handle: VoidPtr)
        external fun engine_call_popAt(_handle: VoidPtr)
        external fun engine_call_popBack(_handle: VoidPtr)
        external fun engine_call_popFront(_handle: VoidPtr)
        external fun engine_call_pushBack(_handle: VoidPtr)
        external fun engine_call_pushFront(_handle: VoidPtr)
        external fun engine_call_reduce(_handle: VoidPtr)
        external fun engine_call_removeAt(_handle: VoidPtr)
        external fun engine_call_resize(_handle: VoidPtr)
        external fun engine_call_reverse(_handle: VoidPtr)
        external fun engine_call_rfind(_handle: VoidPtr)
        external fun engine_call_shuffle(_handle: VoidPtr)
        external fun engine_call_size(_handle: VoidPtr)
        external fun engine_call_slice(_handle: VoidPtr)
        external fun engine_call_sort(_handle: VoidPtr)
        external fun engine_call_sortCustom(_handle: VoidPtr)
        external fun engine_call_operator_set(_handle: VoidPtr)
        external fun engine_call_operator_get(_handle: VoidPtr)
    }
}


//CONSTRUCTOR
@Suppress("FunctionName")
inline fun <reified T> VariantArray(): VariantArray<T> {
    val variantType = variantMapper[T::class]
    checkNotNull(variantType) {
        "Can't create a VariantArray with generic ${T::class}."
    }
    return VariantArray<T>(
        variantType,
        T::class
    )
}

//HELPER
inline fun <reified T> variantArrayOf(vararg args: T) = VariantArray<T>().also { it.addAll(args) }

/**
 * Convert an iterable into a GodotArray
 * Warning: Might be slow if the iterable contains a lot of items because can only append items one by one
 */
inline fun <reified T> Iterable<T>.toVariantArray() = VariantArray<T>().also { arr ->
    forEach {
        arr.append(it)
    }
}
