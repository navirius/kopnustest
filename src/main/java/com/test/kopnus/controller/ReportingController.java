package com.test.kopnus.controller;

import com.test.kopnus.model.entity.PekerjaanEntity;
import com.test.kopnus.repository.PekerjaanRepository;
import com.test.kopnus.response.ErrorResponse;
import com.test.kopnus.response.SuccessResponse;
import com.test.kopnus.service.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/v1/reporting/")
public class ReportingController {

    @Autowired
    ValidationService validationService;

    @Autowired
    PekerjaanRepository pekerjaanRepository;


    @Operation(description = "mendapatkan laporan semua pekerjaan")
    @GetMapping("all-work")
    public ResponseEntity getAllPekerjaan(@RequestHeader("Authorization") String authToken){
        if(!validationService.authorizationHeaderValidation(authToken)){
            return ErrorResponse.generateResponseWithStatus("Authorization header can not validate", HttpStatus.UNAUTHORIZED.value(), "");
        }
        List<PekerjaanEntity> listPekerjaan = pekerjaanRepository.findAll();
        return SuccessResponse.generateSuccessResponse(listPekerjaan);
    }
}
