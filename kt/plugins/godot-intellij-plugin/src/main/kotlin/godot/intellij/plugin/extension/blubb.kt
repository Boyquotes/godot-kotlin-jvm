package godot.intellij.plugin.extension

import com.intellij.openapi.progress.ModalTaskOwner.project
import org.gradle.tooling.*
import org.jetbrains.plugins.gradle.settings.GradleExtensionsSettings
import org.jetbrains.plugins.gradle.settings.GradleProjectSettings
import java.io.File


fun blabb() {
    val projectDir = "/home/cedric/projects/04_godot/4/modules/kotlin_jvm/harness/tests"
    val extensionName = "myPlugin"

    val connector = GradleConnector.newConnector()
        .forProjectDirectory(File(projectDir))
    val connection = connector.connect()

    val build = connection.newBuild()
        .forTasks("assemble")
        .setStandardOutput(System.out)
        .setStandardError(System.err)

    try {
        val project = connection.getModel(GradleExtensionsSettings.GradleProject::class.java)

//        // Get the extension configuration
//        val extensions = project.extensions.findByType(MyPluginExtension::class.java)
//        val someConfiguration = extensions.someConfiguration
        val blubb = ""
    } finally {
        connection.close()
    }
}
