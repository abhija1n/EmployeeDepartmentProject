package com.assesment.spring.EmployeDepartmentService.service;

import com.assesment.spring.EmployeDepartmentService.dto.ApiResponseDto;
import com.assesment.spring.EmployeDepartmentService.dto.CurrencyDto;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

    ApiResponseDto<String> saveEmployees(Map<String, List<Map<String, Object>>> data);
    public ApiResponseDto<List<CurrencyDto>> getEligibleEmployees(String date);
}
