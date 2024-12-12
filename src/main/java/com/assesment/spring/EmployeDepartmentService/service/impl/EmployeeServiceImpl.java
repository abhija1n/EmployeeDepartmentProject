package com.assesment.spring.EmployeDepartmentService.service.impl;


import com.assesment.spring.EmployeDepartmentService.dto.ApiResponseDto;
import com.assesment.spring.EmployeDepartmentService.dto.CurrencyDto;
import com.assesment.spring.EmployeDepartmentService.dto.EmployeeDto;
import com.assesment.spring.EmployeDepartmentService.exception.EmployeeServiceException;
import com.assesment.spring.EmployeDepartmentService.model.Department;
import com.assesment.spring.EmployeDepartmentService.model.Employee;
import com.assesment.spring.EmployeDepartmentService.repository.DepartmentRepository;
import com.assesment.spring.EmployeDepartmentService.repository.EmployeeRepository;
import com.assesment.spring.EmployeDepartmentService.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Override
    public ApiResponseDto<String> saveEmployees(Map<String, List<Map<String, Object>>> data) {
        List<Map<String, Object>> empData = data.get("employees");

        if (empData == null || empData.isEmpty()) {
            throw new EmployeeServiceException("Employee data is missing or empty", "DATA_VALIDATION_ERROR");
        }

        for (Map<String, Object> employeeMap : empData) {
            // Fetch the department name
            String department = (String) employeeMap.get("department");
            if (department == null || department.isEmpty()) {
                throw new EmployeeServiceException("Department name is missing", "DATA_VALIDATION_ERROR");
            }

            // Check and save department
            Department dep = departmentRepository.findByDepName(department)
                    .orElseGet(() -> {
                        Department newDepartment = new Department();
                        newDepartment.setDepName(department);
                        return departmentRepository.save(newDepartment);
                    });

            try {
                // Save the employee in DB
                Employee employee = new Employee();
                employee.setEmpName((String) employeeMap.get("empName"));
                employee.setAmount(Double.parseDouble(employeeMap.get("amount").toString()));
                employee.setCurrency((String) employeeMap.get("currency"));
                employee.setJoiningDate((String) employeeMap.get("joiningDate"));
                employee.setLeavingDate((String) employeeMap.get("leavingDate"));
                employee.setDepartment(dep);
                employeeRepository.save(employee);
            } catch (Exception e) {
                throw new EmployeeServiceException("Failed to save employee data", e);
            }
        }

        ApiResponseDto<String> responseDto = new ApiResponseDto<>();
        responseDto.setErrorMessage("");
        responseDto.setData("Employee Data Saved Successfully");
        return responseDto;
    }

    @Override
    public ApiResponseDto<List<CurrencyDto>> getEligibleEmployees(String date) {
        if (date == null || date.isEmpty()) {
            throw new EmployeeServiceException("Date parameter is missing", "DATA_VALIDATION_ERROR");
        }

        List<Employee> eligibleEmployees;
        try {
            eligibleEmployees = employeeRepository.findByJoiningDateBeforeAndExitDateAfter(date, date);
        } catch (Exception e) {
            throw new EmployeeServiceException("Failed to fetch eligible employees", e);
        }

        if (eligibleEmployees.isEmpty()) {
            throw new EmployeeServiceException("No eligible employees found for the given date", "NO_DATA_FOUND");
        }

        List<CurrencyDto> currencyGroups = eligibleEmployees.stream()
                .collect(Collectors.groupingBy(Employee::getCurrency))
                .entrySet()
                .stream()
                .map(entry -> {
                    CurrencyDto group = new CurrencyDto();
                    group.setCurrency(entry.getKey());
                    group.setEmployeeDtoList(entry.getValue().stream().map(emp -> {
                        EmployeeDto dto = new EmployeeDto();
                        dto.setEmpName(emp.getEmpName());
                        dto.setAmount(emp.getAmount());
                        return dto;
                    }).sorted(Comparator.comparing(EmployeeDto::getEmpName)).collect(Collectors.toList()));
                    return group;
                }).collect(Collectors.toList());

        ApiResponseDto<List<CurrencyDto>> response = new ApiResponseDto<>();
        response.setErrorMessage("");
        response.setData(currencyGroups);
        return response;
    }

}
