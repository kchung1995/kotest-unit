package blog.katfun.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test

class KotestUnitPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply(JavaPlugin::class.java)

        project.dependencies.add("testImplementation", KOTEST_UNIT_DEPENDENCY)

        val sourceSets = project.extensions.getByType(SourceSetContainer::class.java)
        val testSourceSet = sourceSets.named(SourceSet.TEST_SOURCE_SET_NAME)

        project.tasks.register("kotestUnittest", Test::class.java) { task ->
            task.group = JavaBasePlugin.VERIFICATION_GROUP
            task.description =
                "Runs kotest tests with @Tags(\"unit\") attached, filtered through UnitOnlySpecFilter."

            task.testClassesDirs = testSourceSet.get().output.classesDirs
            task.classpath = testSourceSet.get().runtimeClasspath

            task.useJUnitPlatform { options -> options.includeEngines("kotest") }

            task.systemProperty("kotest.filter.unit-only", "true")
            task.systemProperty(
                "kotest.framework.config.fqn",
                "blog.katfun.global.config.KotestProjectConfig",
            )

            task.filter { filter -> filter.isFailOnNoMatchingTests = false }
        }
    }

    private companion object {
        const val KOTEST_UNIT_DEPENDENCY = "blog.katfun:kotest-unit:2.0.0"
    }
}
