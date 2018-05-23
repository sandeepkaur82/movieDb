package com.sofi.moviedb.helpers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

/**
 * Created by sandeepkaur on 5/28/18.
 */
public class ValidatableObject{
	private Response response;

	/**
	 * Constructor to build the ValidatableObject class
	 * @param validatableResponse - Response used to retrieve JSON object
	 */
	public ValidatableObject(Response validatableResponse)
	{
		response = validatableResponse;
	}

	/**
	 * Validates the statusCode
	 * @param statusCode - Integer value for standard http status code
	 */
	public void validateStatusCode(int statusCode) {
		response.then().assertThat().statusCode(statusCode);
	}

	/**
	 * Validates the ContentType
	 * @param contentType - RestAssured contentType enumeration
	 */
	public void validateContentType(ContentType contentType) {
		response.then().assertThat().contentType(contentType);
	}

	/**
	 * Validates that the request token exists and is not blank
	 */
	public void validateRequestToken() {
		response.then().assertThat()
			.body("$", hasKey("request_token"))
			.body("request_token", is(notNullValue()))
			.body("$", hasKey("success"))
			.body("success", is(true));
	}

	/**
	 * Fetches Request Token as String
	 */
	public String getRequestToken() {
		return response.then().extract().response().path("request_token").toString();
	}

	/**
	 * Validates Company Name
	 */
	public void validateCompanyName(String companyName) {
		response.then().assertThat().body(String.format("results[%d].name", 0), equalTo(companyName));
	}

	/**
	 * Validates Movie Title
	 */
	public void validateMovieTitle(String movieTitle) {
		response.then().assertThat().body(String.format("results[%d].title", 0), equalTo(movieTitle));
	}

	/**
	 * Validates Movie Release Date
	 */
	public void validateMovieReleaseDate(String releaseDate) {
		response.then().assertThat().body(String.format("results[%d].release_date", 0), equalTo(releaseDate));
	}

	/**
	 * Validates Object Adult Status
	 */
	public void validateAdultStatus(boolean adultStatus) {
		response.then().assertThat().body(String.format("results[%d].adult", 0), equalTo(adultStatus));
	}

	/**
	 * Validates Person Name
	 */
	public void validatePersonName(String name) {
		response.then().assertThat().body(String.format("results[%d].name", 0), is(notNullValue())).log().all();
	}

	public void validateMovieListSize(int size) {
		response.then().assertThat().body("$", hasKey("total_results"))
			.body("total_results", equalTo(size));
		assert getMoviesList().size() == size;
	}

	private ArrayList<HashMap> getMoviesList() {
		ArrayList<HashMap> listOfMovies = null;
		Map<Object, Object> responseMap = response.jsonPath().getMap("$");
		for(Map.Entry<Object, Object> entry : responseMap.entrySet()) {
			if (entry.getKey().equals("results")) {
				listOfMovies = (ArrayList) entry.getValue();
				break;
			}
		}
		return listOfMovies;
	}

	public boolean validateIfMovieIsInList(String movie) {
		boolean foundMovie = false;
				ArrayList<HashMap> listOfMovies = getMoviesList();
				outerloop:
				for(Map<Object, Object> movieMap: listOfMovies) {
					for(Map.Entry<Object, Object> movieMapEntry : movieMap.entrySet()) {
						if(movieMapEntry.getKey().equals("title") && movieMapEntry.getValue().equals(movie)) {
							foundMovie = true;
							break outerloop;
						}
					}
				}
		return foundMovie;
	}
}
