package com.sofi.moviedb.helpers;

import io.restassured.response.Response;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import static com.sofi.moviedb.constants.TestConstants.*;
import static io.restassured.RestAssured.*;

/**
 * Created by sandeepkaur on 5/28/18.
 * This class provides setup and tear down functionality for all tests.
 */
public class SetupAndTearDown {

	private static Response response;
	public static String searchURL = null;

	/**
	 * This method runs before test suite execution is started.
	 */
	@BeforeSuite
	public void suiteSetUp() {
	}

	/**
	 * This method runs before each test to do some basic setup.
	 * If both apiObject and query is null then it creates a Response interface with authentication url
	 * else create a Response interface with search url for the given params and passes it
	 * as parameter to the ValidatableObject class.
	 *
	 * @param apiObject - Object to be searched for
	 * @param query     - query to be searched for
	 * @return - Returns ValidatableObject
	 */
	@BeforeTest
	public static ValidatableObject testSetUp(String apiObject, String query, String year) {
		searchURL = String.format("3/search/");

		if (apiObject != null && query != null) {
			if(year != null) {
				searchURL = String.format(searchURL.concat("%s?api_key=%s&query=%s&year=%s"), apiObject, apiKey, query, year);
			}
			else {
				searchURL = String.format(searchURL.concat("%s?api_key=%s&query=%s"), apiObject, apiKey, query);
			}
			response = given().when().get(baseURL + searchURL);
		} else {
			response = given().when().get(baseURL + generateTokenURL);
		}
		return new ValidatableObject(response);
	}

	/**
	 * This method runs after each test.
	 */
	@AfterTest
	public void testTearDown() {
	}

	/**
	 * This method runs after test suite execution is completed.
	 */
	@AfterSuite
	public void suiteTearDown() {
	}
}
