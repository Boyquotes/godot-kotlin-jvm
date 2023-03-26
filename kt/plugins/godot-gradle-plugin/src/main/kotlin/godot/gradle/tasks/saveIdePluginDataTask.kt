package godot.gradle.tasks

import godot.gradle.GodotExtension
import godot.gradle.projectExt.godotJvmExtension
import godot.tools.common.constants.Files
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import java.io.File

fun Project.saveIdePluginDataTask(
): TaskProvider<out Task> {
    return tasks.register("saveIdePluginData", DefaultTask::class.java) {
        with(it) {
            doLast {
                File(System.getProperty("java.io.tmpdir"))
                    .resolve(project.name)
                    .apply { mkdirs() }
                    .resolve(Files.idePluginDataPropertiesFile)
                    .writeText(
                        """
                            ${GodotExtension::isFqNameRegistrationEnabled.name}=${project.godotJvmExtension.isFqNameRegistrationEnabled.getOrElse(false)}
                        """.trimIndent()
                    )
            }
        }
    }
}
