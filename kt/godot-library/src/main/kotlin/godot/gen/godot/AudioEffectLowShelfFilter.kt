// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY!
@file:Suppress("PackageDirectoryMismatch", "unused", "FunctionName", "RedundantModalityModifier",
    "UNCHECKED_CAST", "JoinDeclarationAndAssignment", "USELESS_CAST",
    "RemoveRedundantQualifierName", "NOTHING_TO_INLINE")

package godot

import godot.`annotation`.GodotBaseType
import kotlin.Suppress
import kotlin.Unit

/**
 * Reduces all frequencies below the [godot.AudioEffectFilter.cutoffHz].
 *
 * Tutorials:
 * [https://docs.godotengine.org/en/3.3/tutorials/audio/audio_buses.html](https://docs.godotengine.org/en/3.3/tutorials/audio/audio_buses.html)
 */
@GodotBaseType
public open class AudioEffectLowShelfFilter : AudioEffectFilter() {
  public override fun __new(): Unit {
    callConstructor(ENGINECLASS_AUDIOEFFECTLOWSHELFFILTER)
  }
}
