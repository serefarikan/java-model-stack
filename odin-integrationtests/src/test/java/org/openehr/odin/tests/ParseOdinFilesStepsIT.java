package org.openehr.odin.tests;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.AfterClass;
import org.openehr.odin.CompositeOdinObject;
import org.openehr.odin.OdinAttribute;
import org.openehr.odin.loader.OdinLoaderImpl;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;

public class ParseOdinFilesStepsIT {
    private InputStream odinContentInputStream;
    private OdinLoaderImpl loader;
    private CompositeOdinObject parseResult;
    private String ATTRIBUTE_PACKAGES = "packages";

    @Given("^An ODIN file exists on the disk$")
    public void anODINFileExistsOnTheDisk() throws Throwable {
        odinContentInputStream =
                ParseOdinFilesStepsIT.class
                        .getResourceAsStream("/odin/CIMI-RM-3.0.5.bmm");
        assertTrue(odinContentInputStream != null);
    }

    @When("^I load the file to parser$")
    public void iLoadTheFileToParser() throws Throwable {
        loader =
                new OdinLoaderImpl()
                        .initParser(odinContentInputStream);
    }

    @And("^parse the file contents$")
    public void parseTheFileContents() throws Throwable {
        parseResult = loader.parse();
    }

    @Then("^I should have a representation of the file contents in memory in ODIN form$")
    public void iShouldHaveRepresentationOfTheFileContentsInMemoryInODINForm() throws Throwable {
        OdinAttribute packages = parseResult.getAttribute(ATTRIBUTE_PACKAGES);
        assertTrue(packages != null);
    }

    @AfterClass
    public void tearDown() throws IOException {
        if (odinContentInputStream != null) {
            odinContentInputStream.close();
        }
    }
}
