package com.assesment.spring.EmployeDepartmentService.service.impl;


import com.assesment.spring.EmployeDepartmentService.dto.ApiResponseDto;
import com.assesment.spring.EmployeDepartmentService.dto.CurrencyDto;
import com.assesment.spring.EmployeDepartmentService.dto.EmployeeDto;
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
        List<Map<String, Object>> empData= data.get("employees");

        for (Map<String,Object> employeMap: empData) {
            //fetch the department Name
            String department= (String) employeMap.get("department");

            Department dep= departmentRepository.findByDepName(department)
                    .orElseGet(()->{
                       Department newDepartment= new Department();
                       newDepartment.setDepName(department);
                       return departmentRepository.save(newDepartment);
                    });

            //Save the employees in Db
            Employee employee= new Employee();
            employee.setEmpName((String) employeMap.get("empName"));
            employee.setAmount(Double.parseDouble(employeMap.get("amount").toString()));
            employee.setCurrency((String) employeMap.get("currency"));
            employee.setJoiningDate((String) employeMap.get("joiningDate"));
            employee.setLeavingDate((String) employeMap.get("leavingDate"));
            employee.setDepartment(dep);
            employeeRepository.save(employee);
        }
        ApiResponseDto<String> responseDto= new ApiResponseDto<>();
        responseDto.setErrorMessage("");
        responseDto.setData("Employee Data Saved Successfully");
        return responseDto;
    }

    @Override
    public ApiResponseDto<List<CurrencyDto>> getEligibleEmployees(String date) {
        List<Employee> eligibleEmployees = employeeRepository.findByJoiningDateBeforeAndExitDateAfter(date, date);

        // Group employees by currency
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
