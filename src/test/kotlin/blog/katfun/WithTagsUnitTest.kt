package blog.katfun

import io.kotest.core.annotation.Tags
import io.kotest.core.spec.style.BehaviorSpec

@Tags("unit")
class WithTagsUnitTest : BehaviorSpec({
    Given("a test with @Tags(\"unit\") annotation") {
        When("runs the test") {
            Then("it passes") {
            }
        }
    }
})