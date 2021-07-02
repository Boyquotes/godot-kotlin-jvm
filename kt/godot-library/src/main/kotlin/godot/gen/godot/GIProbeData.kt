// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY!
@file:Suppress("PackageDirectoryMismatch", "unused", "FunctionName", "RedundantModalityModifier",
    "UNCHECKED_CAST", "JoinDeclarationAndAssignment", "USELESS_CAST",
    "RemoveRedundantQualifierName", "NOTHING_TO_INLINE")

package godot

import godot.`annotation`.CoreTypeHelper
import godot.`annotation`.GodotBaseType
import godot.core.AABB
import godot.core.PoolIntArray
import godot.core.TransferContext
import godot.core.Transform
import godot.core.VariantType.BOOL
import godot.core.VariantType.DOUBLE
import godot.core.VariantType.LONG
import godot.core.VariantType.NIL
import godot.core.VariantType.POOL_INT_ARRAY
import godot.core.VariantType.TRANSFORM
import kotlin.Boolean
import kotlin.Double
import kotlin.Long
import kotlin.Suppress
import kotlin.Unit

/**
 *
 *
 * Tutorials:
 * [https://godotengine.org/asset-library/asset/678](https://godotengine.org/asset-library/asset/678)
 */
@GodotBaseType
public open class GIProbeData : Resource() {
  /**
   *
   */
  public open var bias: Double
    get() {
      TransferContext.writeArguments()
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_GET_BIAS, DOUBLE)
      return TransferContext.readReturnValue(DOUBLE, false) as Double
    }
    set(`value`) {
      TransferContext.writeArguments(DOUBLE to value)
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_SET_BIAS, NIL)
    }

  /**
   *
   */
  public open var bounds: AABB
    get() {
      TransferContext.writeArguments()
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_GET_BOUNDS,
          godot.core.VariantType.AABB)
      return TransferContext.readReturnValue(godot.core.VariantType.AABB, false) as AABB
    }
    set(`value`) {
      TransferContext.writeArguments(godot.core.VariantType.AABB to value)
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_SET_BOUNDS, NIL)
    }

  /**
   *
   */
  public open var cellSize: Double
    get() {
      TransferContext.writeArguments()
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_GET_CELL_SIZE, DOUBLE)
      return TransferContext.readReturnValue(DOUBLE, false) as Double
    }
    set(`value`) {
      TransferContext.writeArguments(DOUBLE to value)
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_SET_CELL_SIZE, NIL)
    }

  /**
   *
   */
  public open var compress: Boolean
    get() {
      TransferContext.writeArguments()
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_GET_COMPRESS, BOOL)
      return TransferContext.readReturnValue(BOOL, false) as Boolean
    }
    set(`value`) {
      TransferContext.writeArguments(BOOL to value)
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_SET_COMPRESS, NIL)
    }

  /**
   *
   */
  public open var dynamicData: PoolIntArray
    get() {
      TransferContext.writeArguments()
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_GET_DYNAMIC_DATA,
          POOL_INT_ARRAY)
      return TransferContext.readReturnValue(POOL_INT_ARRAY, false) as PoolIntArray
    }
    set(`value`) {
      TransferContext.writeArguments(POOL_INT_ARRAY to value)
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_SET_DYNAMIC_DATA, NIL)
    }

  /**
   *
   */
  public open var dynamicRange: Long
    get() {
      TransferContext.writeArguments()
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_GET_DYNAMIC_RANGE,
          LONG)
      return TransferContext.readReturnValue(LONG, false) as Long
    }
    set(`value`) {
      TransferContext.writeArguments(LONG to value)
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_SET_DYNAMIC_RANGE,
          NIL)
    }

  /**
   *
   */
  public open var energy: Double
    get() {
      TransferContext.writeArguments()
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_GET_ENERGY, DOUBLE)
      return TransferContext.readReturnValue(DOUBLE, false) as Double
    }
    set(`value`) {
      TransferContext.writeArguments(DOUBLE to value)
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_SET_ENERGY, NIL)
    }

  /**
   *
   */
  public open var interior: Boolean
    get() {
      TransferContext.writeArguments()
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_GET_INTERIOR, BOOL)
      return TransferContext.readReturnValue(BOOL, false) as Boolean
    }
    set(`value`) {
      TransferContext.writeArguments(BOOL to value)
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_SET_INTERIOR, NIL)
    }

  /**
   *
   */
  public open var normalBias: Double
    get() {
      TransferContext.writeArguments()
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_GET_NORMAL_BIAS,
          DOUBLE)
      return TransferContext.readReturnValue(DOUBLE, false) as Double
    }
    set(`value`) {
      TransferContext.writeArguments(DOUBLE to value)
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_SET_NORMAL_BIAS, NIL)
    }

  /**
   *
   */
  public open var propagation: Double
    get() {
      TransferContext.writeArguments()
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_GET_PROPAGATION,
          DOUBLE)
      return TransferContext.readReturnValue(DOUBLE, false) as Double
    }
    set(`value`) {
      TransferContext.writeArguments(DOUBLE to value)
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_SET_PROPAGATION, NIL)
    }

  /**
   *
   */
  public open var toCellXform: Transform
    get() {
      TransferContext.writeArguments()
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_GET_TO_CELL_XFORM,
          TRANSFORM)
      return TransferContext.readReturnValue(TRANSFORM, false) as Transform
    }
    set(`value`) {
      TransferContext.writeArguments(TRANSFORM to value)
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_GIPROBEDATA_SET_TO_CELL_XFORM,
          NIL)
    }

  public override fun __new(): Unit {
    callConstructor(ENGINECLASS_GIPROBEDATA)
  }

  @CoreTypeHelper
  public open fun bounds(schedule: AABB.() -> Unit): AABB = bounds.apply{
      schedule(this)
      bounds = this
  }


  @CoreTypeHelper
  public open fun toCellXform(schedule: Transform.() -> Unit): Transform = toCellXform.apply{
      schedule(this)
      toCellXform = this
  }

}
