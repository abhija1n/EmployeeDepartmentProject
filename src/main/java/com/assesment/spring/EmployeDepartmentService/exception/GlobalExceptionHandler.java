package com.assesment.spring.EmployeDepartmentService.exception;


import com.assesment.spring.EmployeDepartmentService.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeServiceException.class)
    public ResponseEntity<ApiResponseDto<String>> handleEmployeeServiceException(EmployeeServiceException ex) {
        ApiResponseDto<String> response = new ApiResponseDto<>();
        response.setErrorMessage(ex.getMessage());
        response.setData(null);

        HttpStatus status = ex.getErrorCode() != null && ex.getErrorCode().equals("NO_DATA_FOUND")
                ? HttpStatus.NOT_FOUND
                : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<String>> handleGenericException(Exception ex) {
        ApiResponseDto<String> response = new ApiResponseDto<>();
        response.setErrorMessage("An unexpected error occurred");
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

