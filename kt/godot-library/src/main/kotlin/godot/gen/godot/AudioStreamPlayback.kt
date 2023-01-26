// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY!
@file:Suppress("PackageDirectoryMismatch", "unused", "FunctionName", "RedundantModalityModifier",
    "UNCHECKED_CAST", "JoinDeclarationAndAssignment", "USELESS_CAST",
    "RemoveRedundantQualifierName", "NOTHING_TO_INLINE", "NON_FINAL_MEMBER_IN_OBJECT",
    "RedundantVisibilityModifier", "RedundantUnitReturnType", "MemberVisibilityCanBePrivate")

package godot

import godot.`annotation`.GodotBaseType
import kotlin.Boolean
import kotlin.Double
import kotlin.Int
import kotlin.NotImplementedError
import kotlin.Suppress
import kotlin.Unit

/**
 * Meta class for playing back audio.
 *
 * Tutorials:
 * [https://godotengine.org/asset-library/asset/526](https://godotengine.org/asset-library/asset/526)
 *
 * Can play, loop, pause a scroll through audio. See [godot.AudioStream] and [godot.AudioStreamOggVorbis] for usage.
 */
@GodotBaseType
public open class AudioStreamPlayback : RefCounted() {
  public override fun new(scriptIndex: Int): Boolean {
    callConstructor(ENGINECLASS_AUDIOSTREAMPLAYBACK, scriptIndex)
    return true
  }

  /**
   *
   */
  public open fun _start(fromPos: Double): Unit {
  }

  /**
   *
   */
  public open fun _stop(): Unit {
  }

  /**
   *
   */
  public open fun _isPlaying(): Boolean {
    throw NotImplementedError("_is_playing is not implemented for AudioStreamPlayback")
  }

  /**
   *
   */
  public open fun _getLoopCount(): Int {
    throw NotImplementedError("_get_loop_count is not implemented for AudioStreamPlayback")
  }

  /**
   *
   */
  public open fun _getPlaybackPosition(): Double {
    throw NotImplementedError("_get_playback_position is not implemented for AudioStreamPlayback")
  }

  /**
   *
   */
  public open fun _seek(position: Double): Unit {
  }

  /**
   *
   */
  public open fun _tagUsedStreams(): Unit {
  }

  public companion object
}
