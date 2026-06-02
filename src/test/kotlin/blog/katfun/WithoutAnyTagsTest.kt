package blog.katfun

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class WithoutAnyTagsTest :
    BehaviorSpec({
        Given("a test with @Tags(\"unit\") annotation") {
            When("runs the test") {
                Then("it passes") {
                    val result = true
                    result shouldBe false
                }
            }
        }
    })
