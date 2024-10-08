@file:Suppress("PackageDirectoryMismatch")

package godot.core

import godot.core.memory.MemoryManager
import godot.core.memory.TransferContext
import godot.util.IndexedIterator
import godot.util.VoidPtr

@Suppress("MemberVisibilityCanBePrivate", "unused")
class PackedVector3Array : NativeCoreType, Iterable<Vector3> {

    //INTERNALS
    internal constructor(_handle: VoidPtr) {
        this._handle = _handle
        MemoryManager.registerNativeCoreType(this, VariantType.PACKED_VECTOR3_ARRAY)
    }

    //PROPERTIES
    val size: Int
        get() {
            Bridge.engine_call_size(_handle)
            return TransferContext.readReturnValue(VariantType.JVM_INT) as Int
        }

    //CONSTRUCTOR
    constructor() {
        _handle = Bridge.engine_call_constructor()
        MemoryManager.registerNativeCoreType(this, VariantType.PACKED_VECTOR3_ARRAY)
    }

    /**
     * Constructs a [PackedVector3Array] as a copy of the given [PackedVector3Array].
     */
    constructor(from: PackedVector3Array) {
        TransferContext.writeArguments(VariantType.PACKED_VECTOR3_ARRAY to from)
        _handle = Bridge.engine_call_constructor_packed_array()
        MemoryManager.registerNativeCoreType(this, VariantType.PACKED_VECTOR3_ARRAY)
    }

    /**
     * Constructs a new [PackedVector3Array] by converting a [VariantArray]<[Vector3]>.
     */
    constructor(from: VariantArray<Vector3>) {
        TransferContext.writeArguments(VariantType.ARRAY to from)
        _handle = Bridge.engine_call_constructor_array()
        MemoryManager.registerNativeCoreType(this, VariantType.PACKED_VECTOR3_ARRAY)
    }

    //POOL ARRAY API SHARED
    /**
     * Appends an element at the end of the array (alias of push_back).
     */
    fun append(vector3: Vector3) {
        TransferContext.writeArguments(VariantType.VECTOR3 to vector3)
        Bridge.engine_call_append(_handle)
    }


    /**
     * Appends a PoolVector3Array at the end of this array.
     */
    fun appendArray(array: PackedVector3Array) {
        TransferContext.writeArguments(VariantType.PACKED_VECTOR3_ARRAY to array)
        Bridge.engine_call_appendArray(_handle)
    }

    /**
     * Finds the index of an existing value (or the insertion index that maintains sorting order, if the value is not
     * yet present in the array) using binary search. Optionally, a `before` specifier can be passed. If `false`, the
     * returned index comes after all existing entries of the value in the array.
     *
     * Note: Calling [bsearch] on an unsorted array results in unexpected behavior.
     */
    fun bsearch(value: Vector3, before: Boolean = true): Int {
        TransferContext.writeArguments(VariantType.VECTOR3 to value, VariantType.BOOL to before)
        Bridge.engine_call_bsearch(_handle)
        return TransferContext.readReturnValue(VariantType.JVM_INT) as Int
    }

    /**
     * Clears the array. This is equivalent to using [resize] with a size of `0`.
     */
    fun clear() {
        Bridge.engine_call_clear(_handle)
    }

    /**
     * Returns the number of times an element is in the array.
     */
    fun count(value: Vector3): Int {
        TransferContext.writeArguments(VariantType.VECTOR3 to value)
        Bridge.engine_call_count(_handle)
        return TransferContext.readReturnValue(VariantType.JVM_INT) as Int
    }

    /**
     * Creates a copy of the array, and returns it.
     */
    fun duplicate(): PackedVector3Array {
        Bridge.engine_call_duplicate(_handle)
        return TransferContext.readReturnValue(VariantType.PACKED_VECTOR3_ARRAY) as PackedVector3Array
    }

    /**
     * Assigns the given value to all elements in the array. This can typically be used together with [resize] to create
     * an array with a given size and initialized elements.
     */
    fun fill(value: Vector3) {
        TransferContext.writeArguments(VariantType.VECTOR3 to value)
        Bridge.engine_call_fill(_handle)
    }

    /**
     * Searches the array for a value and returns its index or `-1` if not found. Optionally, the initial search index can
     * be passed.
     */
    fun find(value: Vector3): Int {
        TransferContext.writeArguments(VariantType.VECTOR3 to value)
        Bridge.engine_call_find(_handle)
        return TransferContext.readReturnValue(VariantType.JVM_INT) as Int
    }

    /**
     *  Retrieve the element at the given index.
     */
    operator fun get(idx: Int): Vector3 {
        TransferContext.writeArguments(VariantType.JVM_INT to idx)
        Bridge.engine_call_get(_handle)
        return TransferContext.readReturnValue(VariantType.VECTOR3) as Vector3
    }

    /**
     * Returns `true` if the array contains [value].
     */
    fun has(value: Vector3): Boolean {
        TransferContext.writeArguments(VariantType.VECTOR3 to value)
        Bridge.engine_call_has(_handle)
        return TransferContext.readReturnValue(VariantType.BOOL) as Boolean
    }

    /**
     * Inserts a new element at a given position in the array.
     * The position must be valid, or at the end of the array (idx == size()).
     */
    fun insert(idx: Int, data: Vector3) {
        TransferContext.writeArguments(VariantType.JVM_INT to idx, VariantType.VECTOR3 to data)
        Bridge.engine_call_insert(_handle)
    }

    /**
     * Returns true if the array is empty.
     */
    fun isEmpty(): Boolean {
        Bridge.engine_call_is_empty(_handle)
        return TransferContext.readReturnValue(VariantType.BOOL) as Boolean
    }

    /**
     * Appends a value to the array.
     */
    fun pushBack(data: Vector3) {
        TransferContext.writeArguments(VariantType.VECTOR3 to data)
        Bridge.engine_call_pushback(_handle)
    }

    /**
     * Removes an element from the array by index.
     */
    fun removeAt(idx: Int) {
        TransferContext.writeArguments(VariantType.JVM_INT to idx)
        Bridge.engine_call_remove_at(_handle)
    }

    /**
     * Sets the size of the array. If the array is grown, reserves elements at the end of the array.
     * If the array is shrunk, truncates the array to the new size.
     */
    fun resize(size: Int) {
        TransferContext.writeArguments(VariantType.JVM_INT to size)
        Bridge.engine_call_resize(_handle)
    }

    /**
     * Reverses the order of the elements in the array.
     */
    fun reverse() {
        Bridge.engine_call_reverse(_handle)
    }

    /**
     * Searches the array in reverse order. Optionally, a start search index can be passed. If negative, the start index
     * is considered relative to the end of the array.
     */
    fun rfind(value: Vector3, from: Int = -1): Int {
        TransferContext.writeArguments(VariantType.VECTOR3 to value, VariantType.JVM_INT to from)
        Bridge.engine_call_rfind(_handle)
        return TransferContext.readReturnValue(VariantType.JVM_INT) as Int
    }

    /**
     * Changes the integer at the given index.
     */
    operator fun set(idx: Int, data: Vector3) {
        TransferContext.writeArguments(VariantType.JVM_INT to idx, VariantType.VECTOR3 to data)
        Bridge.engine_call_set(_handle)
    }

    /**
     * Returns the slice of the [PackedVector3Array], from begin (inclusive) to end (exclusive), as a new [PackedVector3Array].
     *
     * The absolute value of begin and end will be clamped to the array size, so the default value for end makes it
     * slice to the size of the array by default (i.e. arr.slice(1) is a shorthand for arr.slice(1, arr.size())).
     *
     * If either begin or end are negative, they will be relative to the end of the array (i.e. arr.slice(0, -2) is a
     * shorthand for arr.slice(0, arr.size() - 2)).
     */
    fun slice(begin: Int, end: Int = Int.MAX_VALUE): PackedVector3Array {
        TransferContext.writeArguments(VariantType.JVM_INT to begin, VariantType.JVM_INT to end)
        Bridge.engine_call_slice(_handle)
        return TransferContext.readReturnValue(VariantType.PACKED_VECTOR3_ARRAY) as PackedVector3Array
    }

    fun sort() {
        Bridge.engine_call_sort(_handle)
    }

    fun toPackedByteArray(): PackedByteArray {
        Bridge.engine_call_to_byte_array(_handle)
        return TransferContext.readReturnValue(VariantType.PACKED_BYTE_ARRAY) as PackedByteArray
    }

    //UTILITIES
    operator fun plus(other: Vector3) {
        this.append(other)
    }

    operator fun plus(other: PackedVector3Array) {
        this.appendArray(other)
    }

    override fun toString(): String {
        return "PoolVector3Array(${size})"
    }

    override fun iterator(): Iterator<Vector3> {
        return IndexedIterator(this::size, this::get, this::removeAt)
    }

    /**
     * WARNING: no equals function is available in the Gdnative API for this Coretype.
     * This methods implementation works but is not the fastest one.
     */
    override fun equals(other: Any?): Boolean {
        return if (other is PackedVector3Array) {
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


    @Suppress("FunctionName")
    private object Bridge {
        external fun engine_call_constructor(): VoidPtr
        external fun engine_call_constructor_packed_array(): VoidPtr
        external fun engine_call_constructor_array(): VoidPtr

        external fun engine_call_append(_handle: VoidPtr)
        external fun engine_call_appendArray(_handle: VoidPtr)
        external fun engine_call_bsearch(_handle: VoidPtr)
        external fun engine_call_clear(_handle: VoidPtr)
        external fun engine_call_count(_handle: VoidPtr)
        external fun engine_call_duplicate(_handle: VoidPtr)
        external fun engine_call_fill(_handle: VoidPtr)
        external fun engine_call_find(_handle: VoidPtr)
        external fun engine_call_get(_handle: VoidPtr)
        external fun engine_call_has(_handle: VoidPtr)
        external fun engine_call_is_empty(_handle: VoidPtr)
        external fun engine_call_insert(_handle: VoidPtr)
        external fun engine_call_reverse(_handle: VoidPtr)
        external fun engine_call_pushback(_handle: VoidPtr)
        external fun engine_call_remove_at(_handle: VoidPtr)
        external fun engine_call_resize(_handle: VoidPtr)
        external fun engine_call_rfind(_handle: VoidPtr)
        external fun engine_call_set(_handle: VoidPtr)
        external fun engine_call_size(_handle: VoidPtr)
        external fun engine_call_slice(_handle: VoidPtr)
        external fun engine_call_sort(_handle: VoidPtr)
        external fun engine_call_to_byte_array(_handle: VoidPtr)
    }
}
