package com.MyCompany.interviewProject.exception;

import java.time.LocalDateTime;

public class ErrorObject {
    private Integer errorCode;
    private String errorMessage;
    private LocalDateTime timestamp;

    // Constructor that sets timestamp automatically
    public ErrorObject(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.timestamp = LocalDateTime.now();
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

}
