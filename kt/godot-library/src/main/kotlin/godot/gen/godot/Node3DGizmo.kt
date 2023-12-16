// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY!
@file:Suppress("PackageDirectoryMismatch", "unused", "FunctionName", "RedundantModalityModifier",
    "UNCHECKED_CAST", "JoinDeclarationAndAssignment", "USELESS_CAST",
    "RemoveRedundantQualifierName", "NOTHING_TO_INLINE", "NON_FINAL_MEMBER_IN_OBJECT",
    "RedundantVisibilityModifier", "RedundantUnitReturnType", "MemberVisibilityCanBePrivate")

package godot

import godot.`annotation`.GodotBaseType
import kotlin.Boolean
import kotlin.Int
import kotlin.Suppress

/**
 * Abstract class to expose editor gizmos for [godot.Node3D].
 *
 * This abstract class helps connect the [godot.Node3D] scene with the editor-specific [godot.EditorNode3DGizmo] class.
 *
 * [godot.Node3DGizmo] by itself has no exposed API, refer to [godot.Node3D.addGizmo] and pass it an [godot.EditorNode3DGizmo] instance.
 */
@GodotBaseType
public open class Node3DGizmo internal constructor() : RefCounted() {
  public override fun new(scriptIndex: Int): Boolean {
    callConstructor(ENGINECLASS_NODE3DGIZMO, scriptIndex)
    return true
  }

  public companion object

  internal object MethodBindings
}
