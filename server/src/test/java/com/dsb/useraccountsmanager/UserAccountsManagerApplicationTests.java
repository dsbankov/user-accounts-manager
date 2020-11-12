package com.dsb.useraccountsmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserAccountsManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserAccountsManagerApplicationTests {
	
	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();
	
	@Test
	public void testListAndAdd() {
		ResponseEntity<UserAccount[]> getResponse = exchangeGetRequest();
		assertEquals(HttpStatus.OK, getResponse.getStatusCode());
		assertTrue(getResponse.getBody().length > 0);
		
		int userAccounntsSize = getResponse.getBody().length;
		
		UserAccount newUserAccount = new UserAccount("Georgi", "Georgiev", "ggioegiev@abv.bg", Date.valueOf("2010-02-01"));
		ResponseEntity<?> postResponse = exchangePostRequest(newUserAccount);
		System.out.println(postResponse);
		assertEquals(HttpStatus.OK, postResponse.getStatusCode());
		
		getResponse = exchangeGetRequest();
		assertEquals(HttpStatus.OK, getResponse.getStatusCode());
		assertEquals(userAccounntsSize + 1, getResponse.getBody().length);
	}
	
	@Test
	public void testInvalidMail() {
		UserAccount newUserAccount = new UserAccount("Georgi", "Georgiev", "INVALID_MAIL", Date.valueOf("2010-02-01"));
		ResponseEntity<?> postResponse = exchangePostRequest(newUserAccount);
		assertEquals(HttpStatus.BAD_REQUEST, postResponse.getStatusCode());
	}
	
	@Test
	public void testDuplicateMail() {
		String duplicateEmail = "duplicate@gmail.com";
		
		UserAccount newUserAccount1 = new UserAccount("Georgi", "Georgiev", duplicateEmail, Date.valueOf("2010-02-01"));
		ResponseEntity<?> postResponse1 = exchangePostRequest(newUserAccount1);
		assertEquals(HttpStatus.OK, postResponse1.getStatusCode());
		
		UserAccount newUserAccount2 = new UserAccount("Ivan", "Ivanov", duplicateEmail, Date.valueOf("2012-03-01"));
		ResponseEntity<?> postResponse2 = exchangePostRequest(newUserAccount2);
		assertEquals(HttpStatus.BAD_REQUEST, postResponse2.getStatusCode());
	}

	private ResponseEntity<?> exchangePostRequest(UserAccount newUserAccount) {
		return restTemplate.exchange(createURLWithPort("/accounts"), HttpMethod.POST, new HttpEntity<UserAccount>(newUserAccount), Object.class);
	}

	private ResponseEntity<UserAccount[]> exchangeGetRequest() {
		return restTemplate.exchange(createURLWithPort("/accounts"), HttpMethod.GET, null, UserAccount[].class);
	}
	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
