// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY!
@file:Suppress("PackageDirectoryMismatch", "unused", "FunctionName", "RedundantModalityModifier",
    "UNCHECKED_CAST", "JoinDeclarationAndAssignment", "USELESS_CAST",
    "RemoveRedundantQualifierName", "NOTHING_TO_INLINE", "NON_FINAL_MEMBER_IN_OBJECT",
    "RedundantVisibilityModifier", "RedundantUnitReturnType", "MemberVisibilityCanBePrivate")

package godot

import godot.`annotation`.GodotBaseType
import godot.core.Transform2D
import godot.core.VariantArray
import godot.core.VariantType.ARRAY
import godot.core.VariantType.BOOL
import godot.core.VariantType.DOUBLE
import godot.core.VariantType.NIL
import godot.core.VariantType.OBJECT
import godot.core.VariantType.TRANSFORM2D
import godot.core.VariantType.VECTOR2
import godot.core.Vector2
import godot.core.memory.TransferContext
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.Suppress
import kotlin.Unit
import kotlin.jvm.JvmOverloads

/**
 * Base class for all objects affected by physics in 2D space.
 *
 * Tutorials:
 * [$DOCS_URL/tutorials/physics/physics_introduction.html]($DOCS_URL/tutorials/physics/physics_introduction.html)
 *
 * PhysicsBody2D is an abstract base class for implementing a physics body. All *Body2D types inherit from it.
 */
@GodotBaseType
public open class PhysicsBody2D internal constructor() : CollisionObject2D() {
  public override fun new(scriptIndex: Int): Boolean {
    callConstructor(ENGINECLASS_PHYSICSBODY2D, scriptIndex)
    return true
  }

  /**
   * Moves the body along the vector [motion]. In order to be frame rate independent in [godot.Node.PhysicsProcess] or [godot.Node.Process], [motion] should be computed using `delta`.
   *
   * Returns a [godot.KinematicCollision2D], which contains information about the collision when stopped, or when touching another body along the motion.
   *
   * If [testOnly] is `true`, the body does not move but the would-be collision information is given.
   *
   * [safeMargin] is the extra margin used for collision recovery (see [godot.CharacterBody2D.safeMargin] for more details).
   *
   * If [recoveryAsCollision] is `true`, any depenetration from the recovery phase is also reported as a collision; this is used e.g. by [godot.CharacterBody2D] for improving floor detection during floor snapping.
   */
  @JvmOverloads
  public fun moveAndCollide(
    motion: Vector2,
    testOnly: Boolean = false,
    safeMargin: Double = 0.08,
    recoveryAsCollision: Boolean = false,
  ): KinematicCollision2D? {
    TransferContext.writeArguments(VECTOR2 to motion, BOOL to testOnly, DOUBLE to safeMargin, BOOL to recoveryAsCollision)
    TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_PHYSICSBODY2D_MOVE_AND_COLLIDE,
        OBJECT)
    return TransferContext.readReturnValue(OBJECT, true) as KinematicCollision2D?
  }

  /**
   * Checks for collisions without moving the body. In order to be frame rate independent in [godot.Node.PhysicsProcess] or [godot.Node.Process], [motion] should be computed using `delta`.
   *
   * Virtually sets the node's position, scale and rotation to that of the given [godot.core.Transform2D], then tries to move the body along the vector [motion]. Returns `true` if a collision would stop the body from moving along the whole path.
   *
   * [collision] is an optional object of type [godot.KinematicCollision2D], which contains additional information about the collision when stopped, or when touching another body along the motion.
   *
   * [safeMargin] is the extra margin used for collision recovery (see [godot.CharacterBody2D.safeMargin] for more details).
   *
   * If [recoveryAsCollision] is `true`, any depenetration from the recovery phase is also reported as a collision; this is useful for checking whether the body would *touch* any other bodies.
   */
  @JvmOverloads
  public fun testMove(
    from: Transform2D,
    motion: Vector2,
    collision: KinematicCollision2D? = null,
    safeMargin: Double = 0.08,
    recoveryAsCollision: Boolean = false,
  ): Boolean {
    TransferContext.writeArguments(TRANSFORM2D to from, VECTOR2 to motion, OBJECT to collision, DOUBLE to safeMargin, BOOL to recoveryAsCollision)
    TransferContext.callMethod(rawPtr, ENGINEMETHOD_ENGINECLASS_PHYSICSBODY2D_TEST_MOVE, BOOL)
    return TransferContext.readReturnValue(BOOL, false) as Boolean
  }

  /**
   * Returns an array of nodes that were added as collision exceptions for this body.
   */
  public fun getCollisionExceptions(): VariantArray<PhysicsBody2D> {
    TransferContext.writeArguments()
    TransferContext.callMethod(rawPtr,
        ENGINEMETHOD_ENGINECLASS_PHYSICSBODY2D_GET_COLLISION_EXCEPTIONS, ARRAY)
    return TransferContext.readReturnValue(ARRAY, false) as VariantArray<PhysicsBody2D>
  }

  /**
   * Adds a body to the list of bodies that this body can't collide with.
   */
  public fun addCollisionExceptionWith(body: Node): Unit {
    TransferContext.writeArguments(OBJECT to body)
    TransferContext.callMethod(rawPtr,
        ENGINEMETHOD_ENGINECLASS_PHYSICSBODY2D_ADD_COLLISION_EXCEPTION_WITH, NIL)
  }

  /**
   * Removes a body from the list of bodies that this body can't collide with.
   */
  public fun removeCollisionExceptionWith(body: Node): Unit {
    TransferContext.writeArguments(OBJECT to body)
    TransferContext.callMethod(rawPtr,
        ENGINEMETHOD_ENGINECLASS_PHYSICSBODY2D_REMOVE_COLLISION_EXCEPTION_WITH, NIL)
  }

  public companion object
}
