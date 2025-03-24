package com.MyCompany.interviewProject.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

        private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        @ExceptionHandler(EnvironmentNotFoundException.class)
        public ResponseEntity<ErrorObject> handleNotFoundException(EnvironmentNotFoundException ex) {
            logger.error("Error: {}", ex.getMessage());
            ErrorObject errorObject = new ErrorObject(HttpStatus.NOT_FOUND.value(), ex.getMessage());
            return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorObject> handleBadRequestException(IllegalArgumentException ex) {
            logger.error("Bad request: {}", ex.getMessage());
            ErrorObject errorObject = new ErrorObject(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
            return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorObject> handleGlobalException(Exception ex) {
            logger.error("Internal server error: {}", ex.getMessage());
            ErrorObject errorObject = new ErrorObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred.");
            return new ResponseEntity<>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
        }


}
