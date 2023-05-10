package com.mazayaku.ujiteknis.controller;


import com.mazayaku.ujiteknis.constant.ServicePath;
import com.mazayaku.ujiteknis.dto.request.CountSalaryRequest;
import com.mazayaku.ujiteknis.dto.response.CountSalaryResponse;
import com.mazayaku.ujiteknis.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SalaryController {

    @Autowired
    SalaryService salaryService;


    @PostMapping(value = ServicePath.COUNT_SALARY, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CountSalaryResponse> countSalary(@RequestBody CountSalaryRequest request){


        CountSalaryResponse result = salaryService.countSalary(request);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
