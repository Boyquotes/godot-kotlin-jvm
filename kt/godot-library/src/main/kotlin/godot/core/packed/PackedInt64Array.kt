@file:Suppress("PackageDirectoryMismatch")

package godot.core

import godot.core.memory.MemoryManager
import godot.core.memory.TransferContext
import godot.util.IndexedIterator
import godot.util.VoidPtr

@Suppress("MemberVisibilityCanBePrivate", "unused")
class PackedInt64Array : NativeCoreType, Iterable<Long> {

    //PROPERTIES
    val size: Int
        get() {
            Bridge.engine_call_size(_handle)
            return TransferContext.readReturnValue(VariantType.JVM_INT) as Int
        }

    //CONSTRUCTOR
    constructor() {
        _handle = Bridge.engine_call_constructor()
        MemoryManager.registerNativeCoreType(this, VariantType.PACKED_INT_32_ARRAY)
    }

    internal constructor(_handle: VoidPtr) {
        this._handle = _handle
        MemoryManager.registerNativeCoreType(this, VariantType.PACKED_INT_32_ARRAY)
    }


    //POOL ARRAY API SHARED
    /**
     * Appends an element at the end of the array (alias of push_back).
     */
    fun append(i: Long) {
        TransferContext.writeArguments(VariantType.LONG to i)
        Bridge.engine_call_append(_handle)
    }


    /**
     * Appends a PoolIntArray at the end of this array.
     */
    fun appendArray(array: PackedInt64Array) {
        TransferContext.writeArguments(VariantType.PACKED_INT_32_ARRAY to array)
        Bridge.engine_call_appendArray(_handle)
    }

    /**
     * Returns true if the array is empty.
     */
    fun empty(): Boolean {
        Bridge.engine_call_is_empty(_handle)
        return TransferContext.readReturnValue(VariantType.BOOL) as Boolean
    }

    /**
     *  Retrieve the element at the given index.
     */
    operator fun get(idx: Int): Long {
        TransferContext.writeArguments(VariantType.JVM_INT to idx)
        Bridge.engine_call_get(_handle)
        return TransferContext.readReturnValue(VariantType.LONG) as Long
    }

    /**
     * Inserts a new element at a given position in the array.
     * The position must be valid, or at the end of the array (idx == size()).
     */
    fun insert(idx: Int, data: Long) {
        TransferContext.writeArguments(VariantType.JVM_INT to idx, VariantType.LONG to data)
        Bridge.engine_call_insert(_handle)
    }

    /**
     * Reverses the order of the elements in the array.
     */
    fun invert() {
        Bridge.engine_call_reverse(_handle)
    }

    /**
     * Appends a value to the array.
     */
    fun pushBack(data: Long) {
        TransferContext.writeArguments(VariantType.LONG to data)
        Bridge.engine_call_pushback(_handle)
    }

    /**
     * Removes an element from the array by index.
     */
    fun remove(idx: Int) {
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
     * Changes the integer at the given index.
     */
    operator fun set(idx: Int, data: Long) {
        TransferContext.writeArguments(VariantType.JVM_INT to idx, VariantType.LONG to data)
        Bridge.engine_call_set(_handle)
    }


    //UTILITIES
    operator fun plus(other: Long) {
        this.append(other)
    }

    operator fun plus(other: PackedInt64Array) {
        this.appendArray(other)
    }

    override fun toString(): String {
        return "PoolIntArray(${size})"
    }

    override fun iterator(): Iterator<Long> {
        return IndexedIterator(this::size, this::get, this::remove)
    }

    /**
     * WARNING: no equals function is available in the Gdnative API for this Coretype.
     * This methods implementation works but is not the fastest one.
     */
    override fun equals(other: Any?): Boolean {
        return if (other is PackedInt64Array) {
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

        external fun engine_call_append(_handle: VoidPtr)
        external fun engine_call_appendArray(_handle: VoidPtr)
        external fun engine_call_is_empty(_handle: VoidPtr)
        external fun engine_call_get(_handle: VoidPtr)
        external fun engine_call_insert(_handle: VoidPtr)
        external fun engine_call_reverse(_handle: VoidPtr)
        external fun engine_call_pushback(_handle: VoidPtr)
        external fun engine_call_remove_at(_handle: VoidPtr)
        external fun engine_call_resize(_handle: VoidPtr)
        external fun engine_call_set(_handle: VoidPtr)
        external fun engine_call_size(_handle: VoidPtr)
    }
}
