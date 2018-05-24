package com.sofi.moviedb.tests;

import com.sofi.moviedb.helpers.SetupAndTearDown;
import com.sofi.moviedb.helpers.ValidatableObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.HTML;


/**
 * Created by sandeepkaur on 5/28/18.
 */
public class AuthenticationTests {
	private ValidatableObject validatableObject;

	@Test
	public void testRequestTokenGeneration() {
		validatableObject = SetupAndTearDown.testSetUp(null, null, null);
		validatableObject.validateStatusCode(200);
		validatableObject.validateContentType(JSON);
		validatableObject.validateRequestToken();
	}

	@Test
	public void testAuthorization() {
		validatableObject = SetupAndTearDown.testSetUp(null, null, null);
		String token = validatableObject.getRequestToken();
		String authenticationURL = String.format("https://www.themoviedb.org/authenticate/%s", token);
		validatableObject = new ValidatableObject(given().when().get(authenticationURL));
		validatableObject.validateStatusCode(200);
		validatableObject.validateContentType(HTML);
	}
}
