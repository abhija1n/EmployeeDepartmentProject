package com.assesment.spring.EmployeDepartmentService.dto;

import lombok.Getter;
import lombok.Setter;


public class ApiResponseDto<T> {

    private String errorMessage;
    private T data;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
