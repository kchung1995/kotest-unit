package blog.katfun.global.config

import io.kotest.core.annotation.Tags
import io.kotest.core.filter.SpecFilter
import io.kotest.core.filter.SpecFilterResult
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

/**
 * Active only if kotest.filter.unit-only is set to true.
 *
 * Collects only tests with either `@Tags("unit")` or `@UnitTest` (meta-annotation).
 * Others are filtered out before instantiation.
 *
 * This filter is to resolve the behaviour when using filtering test with tags in kotest still includes no-tagged tests.
 */
object UnitOnlySpecFilter : SpecFilter {
    private const val UNIT_TAG = "unit"
    private const val PROPERTY = "kotest.filter.unit-only"
    private const val TRUE = "true"

    override fun filter(kclass: KClass<*>): SpecFilterResult {
        if (System.getProperty(PROPERTY) != TRUE) return SpecFilterResult.Include

        val tags = collectTags(kclass)
        return if (UNIT_TAG in tags) {
            SpecFilterResult.Include
        } else {
            SpecFilterResult.Exclude("Not annotated with @UnitTest / @Tags(\"$UNIT_TAG\")")
        }
    }

    private fun collectTags(kclass: KClass<*>): Set<String> {
        val direct = kclass.findAnnotation<Tags>()?.values?.toSet().orEmpty()
        val viaMeta = kclass.annotations
            .mapNotNull { it.annotationClass.findAnnotation<Tags>() }
            .flatMap { it.values.toList() }
            .toSet()
        return direct + viaMeta
    }
}
