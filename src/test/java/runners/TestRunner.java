package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


// This says our testing is going to be run with the Cucumber class
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "definitions",
        plugin = {"pretty"},
        dryRun = true // This means when we run our test, Cucumber is going to check all the steps inside the features file and check that we have them written inside the TestDefinitions.java
)
public class TestRunner {
}