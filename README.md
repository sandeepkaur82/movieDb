# movieDb
API tests for https://www.themoviedb.org/


This project contains early implementations of the tests for themoviedb API using rest assured framework.

Steps to run the tests:
1. Clone the repo: movieDb and checkout branch: master.
2. Add your apiKey to com.sofi.moviedb.constants.TestConstants.java file.
3. Runnable java test files: 'AuthenticationTests.java' and 'SearchTests.java'
4. Click the name of the runnable file in the IDE project structure -> right click -> click run.


Notes:
1. 'CompaniesTests.java' and 'MovieTests.java' contains just stub out tests.
2. 'AuthenticationTests.java' contains 2 tests related to authentication. All of them are expected to pass.
3. 'SearchTests.java' cotains 4 search related tests. All of them are expected to pass.
    Uncomment line#57 for SearchTests.testSearchMoviesByYear() to fail.