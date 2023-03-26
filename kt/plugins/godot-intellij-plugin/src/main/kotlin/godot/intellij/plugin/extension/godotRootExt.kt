package godot.intellij.plugin.extension

import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import godot.gradle.GodotExtension
import godot.tools.common.constants.Files
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.model.gradle.GradleBuild
import org.jetbrains.kotlin.idea.base.util.isGradleModule
import org.jetbrains.kotlin.idea.core.util.toVirtualFile
import org.jetbrains.kotlin.idea.util.projectStructure.getModule
import org.jetbrains.kotlin.idea.util.projectStructure.module
import org.jetbrains.plugins.gradle.util.GradleUtil
import java.io.File
import java.util.*


typealias GodotRoot = String

fun Module?.getGodotGradleExtension(): Boolean {
    return if (this != null && !isDisposed && this.isGradleModule) {
        @Suppress("UnstableApiUsage")
        GradleUtil.findGradleModuleData(this)?.data?.let { moduleData ->
            val connector = GradleConnector.newConnector()
            val gradleProjectName = connector
                .forProjectDirectory(File(moduleData.linkedExternalProjectPath))
                .connect()
                .use { connection ->
                    connection
                        .getModel(GradleBuild::class.java)
                        .rootProject
                        .name
                }

            File(System.getProperty("java.io.tmpdir"))
                .resolve(gradleProjectName)
                .apply { mkdirs() }
                .resolve(Files.idePluginDataPropertiesFile)
                .let { dataFile ->
                    if (dataFile.exists()) {
                        dataFile.inputStream().use { inputStream ->
                            Properties().apply { load(inputStream) }
                        }
                    } else null
                }
                ?.getProperty(GodotExtension::isFqNameRegistrationEnabled.name, "false")
                ?.toBoolean()
        } ?: false
    } else false
}

fun Module?.getGodotRoot(): GodotRoot? {
    return if (this != null && !isDisposed && this.isGradleModule) {
        val blabb = getGodotGradleExtension()
        @Suppress("UnstableApiUsage")
        GradleUtil.findGradleModuleData(this)?.data?.let { moduleData ->
            File(moduleData.linkedExternalProjectPath).toVirtualFile()?.findChild("project.godot")?.path
        }
    } else null
}

fun VirtualFile.isInGodotRoot(project: Project): Boolean = getModule(project).getGodotRoot() != null
fun VirtualFile.getGodotRoot(project: Project): GodotRoot? = getModule(project).getGodotRoot()
fun PsiFile.isInGodotRoot(): Boolean = module.getGodotRoot() != null
fun PsiFile.getGodotRoot(): GodotRoot? = module.getGodotRoot()
fun PsiElement.isInGodotRoot(): Boolean = module.getGodotRoot() != null
fun PsiElement.getGodotRoot(): GodotRoot? = module.getGodotRoot()
