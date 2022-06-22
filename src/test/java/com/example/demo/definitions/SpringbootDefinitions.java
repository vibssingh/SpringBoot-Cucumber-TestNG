package com.example.demo.definitions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import org.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringbootDefinitions {

	private final static String BASE_URI = "http://localhost";

	@LocalServerPort
	private int port;

	private ValidatableResponse validatableResponse, validatableResponse1;

	private void configureRestAssured() {
		RestAssured.baseURI = BASE_URI;
		RestAssured.port = port;
	}

	protected RequestSpecification requestSpecification() {
		configureRestAssured();
		return given();
	}

	@Given("I send a request to the URL {string} to get user details")
	public void getStudentDetails(String endpoint) throws Throwable {
		validatableResponse = requestSpecification().contentType(ContentType.JSON).when().get(endpoint).then();
		System.out.println("RESPONSE :" + validatableResponse.extract().asString());
	}

	@Given("I send a request to the URL {string} to create a user with name {string} and passportNo {string}")
	public void createStudent(String endpoint, String studentName, String studentPassportNumber) throws Throwable {

		JSONObject student = new JSONObject();
		student.put("name", studentName);
		student.put("passportNumber", studentPassportNumber);

		validatableResponse = requestSpecification().contentType(ContentType.JSON).body(student.toString()).when()
				.post(endpoint).then();
		System.out.println("RESPONSE :" + validatableResponse.extract().asString());
	}

	@Then("The response will return status {int}")
	public void verifyStatusCodeResponse(int status) {
		validatableResponse.assertThat().statusCode(equalTo(status));

	}

	@Then("The response contains id {int} and names {string} and passport_no {string}")
	public void verifyResponse(int id, String studentName, String passportNo) {
		validatableResponse.assertThat().body("id", hasItem(id)).body(containsString(studentName))
				.body(containsString(passportNo));

	}

	@Then("Resend the request to the URL {string} and the response returned contains name {string} and passport_no {string}")
	public void verifyNewStudent(String endpoint, String studentName, String passportNo) {

		validatableResponse1 = requestSpecification().contentType(ContentType.JSON).when().get(endpoint).then();
		System.out.println("RESPONSE :" + validatableResponse1.extract().asString());
		validatableResponse1.assertThat().body(containsString(studentName)).body(containsString(passportNo));

	}
}
