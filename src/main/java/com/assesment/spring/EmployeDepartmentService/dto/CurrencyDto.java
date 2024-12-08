package com.assesment.spring.EmployeDepartmentService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class CurrencyDto {
    private String currency;
    private List<EmployeeDto> employeeDtoList;

    public CurrencyDto() {
    }

    public CurrencyDto(String currency, List<EmployeeDto> employeeDtoList) {
        this.currency = currency;
        this.employeeDtoList = employeeDtoList;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<EmployeeDto> getEmployeeDtoList() {
        return employeeDtoList;
    }

    public void setEmployeeDtoList(List<EmployeeDto> employeeDtoList) {
        this.employeeDtoList = employeeDtoList;
    }
}
