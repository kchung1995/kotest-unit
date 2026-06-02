package blog.katfun

import blog.katfun.global.config.UnitTest
import io.kotest.core.spec.style.BehaviorSpec

@UnitTest
class WithUnitTestAnnotationTest : BehaviorSpec({
    Given("a test with @Tags(\"unit\") annotation") {
        When("runs the test") {
            Then("it passes") {
            }
        }
    }
})