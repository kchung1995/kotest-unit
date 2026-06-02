package blog.katfun.global.config

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension

class KotestProjectConfig : AbstractProjectConfig() {
    override fun extensions(): List<Extension> = listOf(UnitOnlySpecFilter)
}
