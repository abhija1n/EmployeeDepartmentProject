package com.assesment.spring.EmployeDepartmentService.service.impl;

import com.assesment.spring.EmployeDepartmentService.dto.ApiResponseDto;
import com.assesment.spring.EmployeDepartmentService.model.Department;
import com.assesment.spring.EmployeDepartmentService.model.Employee;
import com.assesment.spring.EmployeDepartmentService.repository.DepartmentRepository;
import com.assesment.spring.EmployeDepartmentService.repository.EmployeeRepository;
import com.assesment.spring.EmployeDepartmentService.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveEmployees() {
        // Mock input data
        Map<String, List<Map<String, Object>>> inputData = new HashMap<>();
        List<Map<String, Object>> employees = new ArrayList<>();

        Map<String, Object> employee1 = new HashMap<>();
        employee1.put("empName", "Abhinav Jain");
        employee1.put("amount", 10000.0);
        employee1.put("currency", "USD");
        employee1.put("joiningDate", "2024-12-07");
        employee1.put("leavingDate", "2024-12-31");
        employee1.put("department", "IT");
        employees.add(employee1);

        inputData.put("employees", employees);

        // Mock department behavior
        Department existingDepartment = new Department();
        existingDepartment.setDepName("IT");
        when(departmentRepository.findByDepName("IT")).thenReturn(Optional.of(existingDepartment));

        // Mock employee repository save
        Employee mockEmployee = new Employee();
        mockEmployee.setEmpName("Abhinav Jain");
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);

        // Call the method under test
        ApiResponseDto<String> response = employeeService.saveEmployees(inputData);

        // Assertions
        assertNotNull(response);
        assertEquals("Employee Data Saved Successfully", response.getData());
        assertEquals("", response.getErrorMessage());

        // Verify interactions with mocks
        verify(departmentRepository, times(1)).findByDepName("IT");
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void getEligibleEmployees() {
    }
}