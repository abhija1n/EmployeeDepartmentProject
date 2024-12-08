package com.assesment.spring.EmployeDepartmentService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class EmployeeDto {
    private String empName;
    private Double amount;

    public EmployeeDto() {
    }

    public EmployeeDto(String empName, Double amount) {
        this.empName = empName;
        this.amount = amount;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
