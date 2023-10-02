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
// This configures the web environment to use a random port in order to avoid port number conflicts in the test environment and indicates that our main method is in the AuthorBookApiApplication class
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AuthorBookApiApplication.class)
public class AuthorControllerTestDefinitions {

    private static final String BASE_URL = "http://localhost:";
    private static final Logger log = Logger.getLogger(AuthorControllerTestDefinitions.class.getName());

    // This injects the random port used by the test into the class at runtime
    @LocalServerPort
    String port;

    private static Response response;


    /**
     * This test checks if the HTTP status code from the GET all method is OK, otherwise, it catches the exception
     */
    // GET /api/authors/
    @Given("A list of authors are available")
    public void aListOfAuthorsAreAvailable() {
        log.info("Calling aListOfAuthorsAreAvailable");

        try {
            // This uses the exchange method to get data by executing the request of the HTTP GET method and returning a ResponseEntity instance, which we can get the response status, body and headers from. To query data for the given properties, we can pass them as URI variables.
            ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL + port + "/api/authors/", HttpMethod.GET, null, String.class);
            List<Map<String, String>> authors = JsonPath.from(String.valueOf(response.getBody())).get("data");

            Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
            // Assert.assertTrue(authors.size() > 0);

        } catch (HttpClientErrorException e) {
            e.printStackTrace();
        }
    }


    /**
     * These tests check that when you call the POST method, the HTTP status code is 201 (created)
     */
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


    /**
     * These tests check that when you call the GET method for a specific author, the HTTP status code is 200 (ok)
     */
    // GET /api/authors/{authorId}/
    @When("I get a specific author")
    public void iGetASpecificAuthor() {
        log.info("Calling iGetASpecificAuthor");

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        response = request.get(BASE_URL + port + "/api/authors/1/");
    }

    @Then("The specific author is available")
    public void theSpecificAuthorIsAvailable() {
        log.info("Calling theSpecificAuthorIsAvailable");

        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(200, response.getStatusCode());
    }


    /**
     * These tests check that when you call the PUT method for a specific author, the HTTP status code is 200 (ok)
     */
    // PUT /api/authors/{authorId}/
    @When("I edit an author from my list")
    public void iEditAnAuthorFromMyList() throws JSONException {
        log.info("Calling iEditAnAuthorFromMyList");

        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("firstName", "First Name");
        requestBody.put("lastName", "Last Name");
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).put(BASE_URL + port + "/api/authors/1/");
    }

    @Then("The author content is edited")
    public void theAuthorContentIsEdited() {
        log.info("Calling theAuthorContentIsEdited");

        JsonPath jsonPath = response.jsonPath();
        String message = jsonPath.get("message");
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("author with id 1 has been successfully updated", message);
    }


    /**
     * These tests check that when you call the DELETE method for a specific author, the HTTP status code is 200 (ok)
     */
    // DELETE /api/authors/{authorId}/
    @When("I remove author from my list")
    public void iRemoveAuthorFromMyList() {
        log.info("Calling iRemoveAuthorFromMyList");

        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        response = request.delete(BASE_URL + port + "/api/authors/1/");
    }

    @Then("The author is removed")
    public void theAuthorIsRemoved() {
        log.info("Calling theAuthorIsRemoved");

        JsonPath jsonPath = response.jsonPath();
        String message = jsonPath.get("message");
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("author with id 1 has been successfully deleted", message);
    }





//    // GET /api/authors/books/
//    @Given("A list of books are available")
//    public void aListOfBooksAreAvailable() {
//        log.info("Calling aListOfBooksAreAvailable");
//
//        try {
//
//            ResponseEntity<String> response = new RestTemplate().exchange(BASE_URL + port + "/api/authors/books/", HttpMethod.GET, null, String.class);
//            List<Map<String, String>> books = JsonPath.from(String.valueOf(response.getBody())).get("data");
//
//            Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
//            // Assert.assertTrue(books.size() > 0);
//
//        } catch (HttpClientErrorException e) {
//            e.printStackTrace();
//        }
//        throw new io.cucumber.java.PendingException();
//    }
//
//
//    // POST /api/authors/{authorId}/books/
//    @When("I add a book to my list")
//    public void iAddABookToMyList() throws JSONException {
//        log.info("Calling iAddABookToMyList");
//
//        RestAssured.baseURI = BASE_URL;
//        RequestSpecification request = RestAssured.given();
//        JSONObject requestBody = new JSONObject();
//        requestBody.put("name", "Name");
//        requestBody.put("description", "Description");
//        requestBody.put("isbn", "Isbn");
//        requestBody.put("author", "Author");
//        request.header("Content-Type", "application/json");
//        response = request.body(requestBody.toString()).post(BASE_URL + port + "/api/authors/1/books/");
//
//        throw new io.cucumber.java.PendingException();
//    }
//
//    @Then("The book is added")
//    public void theBookIsAdded() {
//        log.info("Calling theBookIsAdded");
//        Assert.assertEquals(201, response.getStatusCode());
//
//        throw new io.cucumber.java.PendingException();
//    }
//
//
//    // GET /api/authors/{authorId}/books/{bookId}/
//    @When("I get a specific book")
//    public void iGetASpecificBook() {
//        log.info("Calling iGetASpecificBook");
//
//        RestAssured.baseURI = BASE_URL;
//        RequestSpecification request = RestAssured.given();
//        response = request.get(BASE_URL + port + "/api/authors/1/books/1/");
//
//        throw new io.cucumber.java.PendingException();
//    }
//
//    @Then("The specific book is available")
//    public void theSpecificBookIsAvailable() {
//        log.info("Calling theSpecificBookIsAvailable");
//
//        JsonPath jsonPath = response.jsonPath();
//        Assert.assertEquals(200, response.getStatusCode());
//
//        throw new io.cucumber.java.PendingException();
//    }
//
//
//    // PUT /api/authors/{authorId}/books/{bookId}/
//    @When("I edit a book from my list")
//    public void iEditABookFromMyList() throws JSONException {
//        log.info("Calling iEditABookFromMyList");
//
//        RestAssured.baseURI = BASE_URL;
//        RequestSpecification request = RestAssured.given();
//        JSONObject requestBody = new JSONObject();
//        requestBody.put("name", "Name");
//        requestBody.put("description", "Description");
//        requestBody.put("isbn", "Isbn");
//        requestBody.put("author", "Author");
//        request.header("Content-Type", "application/json");
//        response = request.body(requestBody.toString()).post(BASE_URL + port + "/api/authors/1/books/1/");
//
//        throw new io.cucumber.java.PendingException();
//    }
//
//    @Then("The book content is edited")
//    public void theBookContentIsEdited() {
//        log.info("Calling theBookContentIsEdited");
//
//        JsonPath jsonPath = response.jsonPath();
//        String message = jsonPath.get("message");
//        Assert.assertEquals(200, response.getStatusCode());
//        Assert.assertEquals("book with id 1 has been successfully updated", message);
//
//        throw new io.cucumber.java.PendingException();
//    }
//
//
//    // DELETE /api/authors/{authorId}/books/{bookId}/
//    @When("I remove a book from my list")
//    public void iRemoveABookFromMyList() {
//        log.info("Calling iRemoveABookFromMyList");
//
//        RequestSpecification request = RestAssured.given();
//        request.header("Content-Type", "application/json");
//        response = request.delete(BASE_URL + port + "/api/authors/1/books/1/");
//
//        throw new io.cucumber.java.PendingException();
//    }
//
//    @Then("The book is removed")
//    public void theBookIsRemoved() {
//        log.info("Calling theBookIsRemoved");
//
//        JsonPath jsonPath = response.jsonPath();
//        String message = jsonPath.get("message");
//        Assert.assertEquals(200, response.getStatusCode());
//        Assert.assertEquals("book with id 1 has been successfully deleted", message);
//
//        throw new io.cucumber.java.PendingException();
//    }
}
