package com.infiniteink.exceptions;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;

@ControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String errorMessage = error.getDefaultMessage();
			String fieldName = ((FieldError) error).getField();
			errors.put(fieldName, errorMessage);
		}

		);
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Map<String, String>> PostNotFoundException(PostNotFoundException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage("resource.notfound", new Object[] { ex.getMessage() }, locale);
        Map<String, String> error = new HashMap<>();
        error.put("message", message);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
  
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(UserNotFoundException ex) {

		Locale locale = LocaleContextHolder.getLocale();

		String message = messageSource.getMessage("user.notfound", new Object[] { ex.getMessage() }, locale);
		Map<String, String> error = new HashMap<>();
		error.put("message", message);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler(CommentNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(CommentNotFoundException ex) {

		Locale locale = LocaleContextHolder.getLocale();

		String message = messageSource.getMessage("comment.notfound", new Object[] { ex.getMessage() }, locale);
		Map<String, String> error = new HashMap<>();
		error.put("message", message);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleAllExceptions(Exception ex) {
		
	     
		Map<String,String>error=new HashMap<>();

		error.put("message", ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

	}
}