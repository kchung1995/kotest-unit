package blog.katfun.gradle

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import org.gradle.testfixtures.ProjectBuilder

class KotestUnitPluginTest {
    @Test
    fun `adds kotest unit dependency and registers kotestUnittest task`() {
        val project = ProjectBuilder.builder().build()

        project.pluginManager.apply(KotestUnitPlugin::class.java)

        val dependency =
            project.configurations.getByName("testImplementation").dependencies.single {
                it.group == "blog.katfun" && it.name == "kotest-unit"
            }
        assertEquals("2.0.0", dependency.version)

        val task =
            project.tasks
                .named("kotestUnittest", org.gradle.api.tasks.testing.Test::class.java)
                .get()
        assertEquals("true", task.systemProperties["kotest.filter.unit-only"])
        assertEquals(
            "blog.katfun.global.config.KotestProjectConfig",
            task.systemProperties["kotest.framework.config.fqn"],
        )
        assertNotNull(task.testClassesDirs)
        assertNotNull(task.classpath)
    }
}
