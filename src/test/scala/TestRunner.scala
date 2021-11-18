import io.cucumber.junit.{Cucumber, CucumberOptions}
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(
    features = Array("src/test/scala/resources/features/"),
  //  glue =
  plugin = Array(
    "pretty",
    "html:target/cucumber-reports/report.html"
  )
)
class TestRunner {}

object TestRunner {

}