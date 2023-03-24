package godot.entrygenerator.checks

import godot.entrygenerator.model.SourceFile
import godot.entrygenerator.utils.Logger
import godot.tools.common.constants.MAX_ARG_COUNT

class ConstructorArgCountCheck(logger: Logger, sourceFiles: List<SourceFile>): BaseCheck(logger, sourceFiles) {
    override fun execute(): Boolean {
        var hasIssue = false
        sourceFiles
            .flatMap { it.registeredClasses }
            .flatMap { it.constructors }
            .forEach { registeredConstructor ->
                // keep in sync with VARIANT_ARG_MAX in transfer_context.cpp!
                if (registeredConstructor.parameters.size > MAX_ARG_COUNT) {
                    hasIssue = true
                    logger.error("RegisteredConstructor ${registeredConstructor.fqName} has more than $MAX_ARG_COUNT arguments")
                }
            }
        return hasIssue
    }
}
