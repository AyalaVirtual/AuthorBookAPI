package definitions;

import com.example.authorbookapi.AuthorBookApiApplication;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import io.restassured.response.Response;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@CucumberContextConfiguration
// This configures the web environment to use a random port in order to avoid port number conflicts in the test environment
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AuthorBookApiApplication.class)
public class AuthorControllerTestDefinitions {

    private static final String BASE_URL = "http://localhost:";
    private static final Logger log = Logger.getLogger(AuthorControllerTestDefinitions.class.getName());

    // This injects the random port used by the test into the class
    @LocalServerPort
    String port;

    private static Response response;


    // GET /api/authors/
    @Given("A list of authors are available")
    public void aListOfAuthorsAreAvailable() {
        log.info("Calling aListOfAuthorsAreAvailable");

        try {
            // This uses the exchange method to get data by executing the request of the HTTP GET method and returning a ResponseEntity instance, which we can get the response status, body and headers from. To query data for the given properties, we can pass them as URI variables.
            ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL + port + "/api/authors/", HttpMethod.GET, null, String.class);
            List<Map<String, String>> authors = JsonPath.from(String.valueOf(response.getBody())).get("data");

            Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }


    // POST /api/authors/
    @When("I add an author to my list")
    public void iAddAnAuthorToMyList() throws JSONException {
        log.info("Calling iAddAnAuthorToMyList");

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("firstName", "First Name");
        requestBody.put("lastName", "Last Name");
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).post(BASE_URL + port + "/api/authors/");
    }

    @Then("The author is added")
    public void theAuthorIsAdded() {
        log.info("Calling theAuthorIsAdded");
        Assert.assertEquals(201, response.getStatusCode());
    }


    // GET /api/authors/1/
    @When("I get a specific author")
    public void iGetASpecificAuthor() {
        log.info("Calling iGetASpecificAuthor");


    }

    @Then("The specific author is available")
    public void theSpecificAuthorIsAvailable() {
        log.info("Calling theSpecificAuthorIsAvailable");


    }


    // PUT /api/authors/1/
    @When("I edit an author from my list")
    public void iEditAnAuthorFromMyList() throws JSONException {
        log.info("Calling iEditAnAuthorFromMyList");


    }

    @Then("The author content is edited")
    public void theAuthorContentIsEdited() {
        log.info("Calling theAuthorContentIsEdited");


    }


    // DELETE /api/authors/1/
    @When("I remove author from my list")
    public void iRemoveAuthorFromMyList() {
        log.info("Calling iRemoveAuthorFromMyList");


    }

    @Then("The author is removed")
    public void theAuthorIsRemoved() {
        log.info("Calling theAuthorIsRemoved");


    }
}
