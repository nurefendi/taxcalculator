package com.mazayaku.ujiteknis.service;

import com.mazayaku.ujiteknis.dto.request.CountSalaryRequest;
import com.mazayaku.ujiteknis.dto.response.CountSalaryResponse;

public interface SalaryService {

    CountSalaryResponse countSalary(CountSalaryRequest request);
}
