package blog.katfun.global.config

import io.kotest.common.ExperimentalKotest
import io.kotest.core.annotation.Tags
import io.kotest.core.extensions.SpecRefExtension
import io.kotest.core.spec.SpecRef
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

/**
 * Active only if kotest.filter.unit-only is set to true.
 *
 * Collects only tests with either `@Tags("unit")` or `@UnitTest` (meta-annotation). Others are
 * filtered out before instantiation.
 *
 * This filter is to resolve the behaviour when using filtering test with tags in kotest still
 * includes no-tagged tests.
 */
@OptIn(ExperimentalKotest::class)
object UnitOnlySpecFilter : SpecRefExtension {
    private const val UNIT_TAG = "unit"
    private const val PROPERTY = "kotest.filter.unit-only"
    private const val TRUE = "true"

    override suspend fun intercept(ref: SpecRef, process: suspend () -> Unit) {
        if (System.getProperty(PROPERTY) != TRUE) {
            process()
            return
        }

        val tags = collectTags(ref.kclass)
        if (UNIT_TAG in tags) process()
    }

    private fun collectTags(kclass: KClass<*>): Set<String> {
        val direct = kclass.findAnnotation<Tags>()?.values?.toSet().orEmpty()
        val viaMeta =
            kclass.annotations
                .mapNotNull { it.annotationClass.findAnnotation<Tags>() }
                .flatMap { it.values.toList() }
                .toSet()
        return direct + viaMeta
    }
}
