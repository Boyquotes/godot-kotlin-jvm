package godot.intellij.plugin.gradle

import godot.gradle.GodotExtension
import org.gradle.api.Project

fun Project.findGodotExtension() = extensions.findByType(GodotExtension::class.java)
