package com.assesment.spring.EmployeDepartmentService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long depId;
    private String depName;
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employeeList;

    public Department(Long depId, String depName, List<Employee> employeeList) {
        this.depId = depId;
        this.depName = depName;
        this.employeeList = employeeList;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public Department() {
    }
}
