package com.assesment.spring.EmployeDepartmentService.controller;

import com.assesment.spring.EmployeDepartmentService.dto.ApiResponseDto;
import com.assesment.spring.EmployeDepartmentService.dto.CurrencyDto;
import com.assesment.spring.EmployeDepartmentService.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tci")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @PostMapping("/empBonus")
    public ResponseEntity<ApiResponseDto<String>> save(@RequestBody Map<String, List<Map<String, Object>>> data){
        ApiResponseDto<String> responseDto = employeeService.saveEmployees(data);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/bonus")
    public ResponseEntity<ApiResponseDto<List<CurrencyDto>>> getEmployee(@RequestParam("date") String date){
        ApiResponseDto<List<CurrencyDto>> eligibleEmployees = employeeService.getEligibleEmployees(date);
        return new ResponseEntity<>(eligibleEmployees,HttpStatus.OK);
    }
}
