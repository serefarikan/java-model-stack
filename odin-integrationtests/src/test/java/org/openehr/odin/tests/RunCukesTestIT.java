package org.openehr.odin.tests;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

//this type connects Cucumber to JUnit so that
//acceptance/integration tests expressed by
//cucumber can be run with Junit.
//Setting the resources via options allows us to use that
//dir for feature files
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/odin-features"},
        glue = {"org.openehr.odin.tests"}
)
public class RunCukesTestIT {
}
