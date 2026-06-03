package blog.katfun.global.config

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension

class KotestProjectConfig : AbstractProjectConfig() {
    override val extensions: List<Extension> = listOf(UnitOnlySpecFilter)
}
