// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY!
@file:Suppress("PackageDirectoryMismatch", "unused", "FunctionName", "RedundantModalityModifier",
    "UNCHECKED_CAST", "JoinDeclarationAndAssignment", "USELESS_CAST",
    "RemoveRedundantQualifierName", "NOTHING_TO_INLINE", "NON_FINAL_MEMBER_IN_OBJECT",
    "RedundantVisibilityModifier", "RedundantUnitReturnType", "MemberVisibilityCanBePrivate")

package godot

import godot.`annotation`.GodotBaseType
import godot.core.VariantType.BOOL
import godot.core.VariantType.DOUBLE
import godot.core.VariantType.NIL
import godot.core.memory.TransferContext
import kotlin.Boolean
import kotlin.Double
import kotlin.Float
import kotlin.Int
import kotlin.Suppress

/**
 * Separation ray shape resource for 2D physics.
 *
 * 2D separation ray shape to be added as a *direct* child of a [godot.PhysicsBody2D] or [godot.Area2D] using a [godot.CollisionShape2D] node. A ray is not really a collision body; instead, it tries to separate itself from whatever is touching its far endpoint. It's often useful for characters.
 *
 * **Performance:** Being a primitive collision shape, [godot.SeparationRayShape2D] is fast to check collisions against.
 */
@GodotBaseType
public open class SeparationRayShape2D : Shape2D() {
  /**
   * The ray's length.
   */
  public var length: Float
    get() {
      TransferContext.writeArguments()
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_SEPARATIONRAYSHAPE2D_GET_LENGTH,
          DOUBLE)
      return (TransferContext.readReturnValue(DOUBLE, false) as Double).toFloat()
    }
    set(`value`) {
      TransferContext.writeArguments(DOUBLE to value.toDouble())
      TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_SEPARATIONRAYSHAPE2D_SET_LENGTH,
          NIL)
    }

  /**
   * If `false` (default), the shape always separates and returns a normal along its own direction.
   *
   * If `true`, the shape can return the correct normal and separate in any direction, allowing sliding motion on slopes.
   */
  public var slideOnSlope: Boolean
    get() {
      TransferContext.writeArguments()
      TransferContext.callMethod(rawPtr,
          ENGINEMETHOD_ENGINECLASS_SEPARATIONRAYSHAPE2D_GET_SLIDE_ON_SLOPE, BOOL)
      return (TransferContext.readReturnValue(BOOL, false) as Boolean)
    }
    set(`value`) {
      TransferContext.writeArguments(BOOL to value)
      TransferContext.callMethod(rawPtr,
          ENGINEMETHOD_ENGINECLASS_SEPARATIONRAYSHAPE2D_SET_SLIDE_ON_SLOPE, NIL)
    }

  public override fun new(scriptIndex: Int): Boolean {
    callConstructor(ENGINECLASS_SEPARATIONRAYSHAPE2D, scriptIndex)
    return true
  }

  public companion object
}
