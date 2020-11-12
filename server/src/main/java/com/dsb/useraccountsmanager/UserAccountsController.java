package com.dsb.useraccountsmanager;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserAccountsController {

	@Autowired
	private UserAccountsRepository repository;
	
	// ENDPOINTS

	@GetMapping("/accounts")
	List<UserAccount> getAllAccounts() {
		List<UserAccount> allAccounts = new ArrayList<UserAccount>();
		repository.findAll().forEach(allAccounts::add);
		return allAccounts;
	}

	@PostMapping("/accounts")
	UserAccount addAccount(@Valid @RequestBody UserAccount userAccount) {
		if (repository.existsByEmailAddress(userAccount.getEmailAddress())) {
			throw new IllegalArgumentException(String.format("User with email address %s already exists.", userAccount.getEmailAddress()));
		}
		return repository.save(userAccount);
	}
	
	// EXCEPTION HANDLERS
	
	@ExceptionHandler({Exception.class})
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(new ErrorResponse(httpStatus, e.getMessage()), httpStatus);
    }
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
		String[] validationErrorMessages = allErrors.stream().map(ObjectError::getDefaultMessage).toArray(String[]::new);
        String validationError = String.join(",", validationErrorMessages);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		return new ResponseEntity<>(new ErrorResponse(httpStatus, validationError), httpStatus);
    }
	
	@ExceptionHandler({HttpMessageNotReadableException.class})
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String message = e.getRootCause() instanceof BirthDateDeserializationException ? e.getRootCause().getMessage() : e.getMessage();
		return new ResponseEntity<>(new ErrorResponse(httpStatus, message), httpStatus);
    }

}
