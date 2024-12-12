package com.assesment.spring.EmployeDepartmentService.exception;

public class EmployeeServiceException extends RuntimeException {
    private String errorCode;

    public EmployeeServiceException(String message) {
        super(message);
    }

    public EmployeeServiceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public EmployeeServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getErrorCode() {
        return errorCode;
    }
}

