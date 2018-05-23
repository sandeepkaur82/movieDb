package com.sofi.moviedb.tests;

import com.sofi.moviedb.helpers.SetupAndTearDown;
import com.sofi.moviedb.helpers.ValidatableObject;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

/**
 * Created by sandeepkaur on 5/28/18.
 */
public class SearchTests {
	private ValidatableObject validatableObject;

	private ValidatableObject search(String apiObject, String query) {
		return search(apiObject, query, null);
	}

	private ValidatableObject search(String apiObject, String query, String year) {
		return SetupAndTearDown.testSetUp(apiObject, query, year);
	}

	@Test
	public void testSearchCompanyByName() {
		validatableObject = search("company", "Walt Disney Productions");
		validatableObject.validateStatusCode(200);
		validatableObject.validateContentType(ContentType.JSON);
		validatableObject.validateCompanyName("Walt Disney Productions");
	}

	@Test
	public void testSearchPeopleByName() {
		validatableObject = search("person", "Jennifer Lopez");
		validatableObject.validateStatusCode(200);
		validatableObject.validateContentType(ContentType.JSON);
		validatableObject.validatePersonName("Jennifer Lopez");
		validatableObject.validateAdultStatus(false);
	}

	@Test
	public void testSearchMovieByName() {
		validatableObject = search("movie", "Incredibles 2");
		validatableObject.validateStatusCode(200);
		validatableObject.validateContentType(ContentType.JSON);
		validatableObject.validateMovieTitle("Incredibles 2");
		validatableObject.validateMovieReleaseDate("2018-06-14");
		validatableObject.validateAdultStatus(false);
	}

	@Test
	public void testSearchMoviesByYear() {
		validatableObject = search("movie", "Star Wars: The Last Jedi", "2018");
		validatableObject.validateMovieListSize(3);
		assert validatableObject.validateIfMovieIsInList("Star Wars: The Last Jedi");
		assert validatableObject.validateIfMovieIsInList("Star Wars: The Last Jedi - Scene Breakdowns");
		assert validatableObject.validateIfMovieIsInList("Star Wars: The Last Jedi - All Deleted Scenes");
//		uncomment this line to see test failure since the list does not contain any movie with name 'The Last Jedi'
//		assert validatableObject.validateIfMovieIsInList("The Last Jedi");
	}

}
