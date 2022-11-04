package com.test.kopnus.controller;

import com.test.kopnus.model.entity.PekerjaanUserEntity;
import com.test.kopnus.repository.PekerjaanUserRepository;
import com.test.kopnus.response.ErrorResponse;
import com.test.kopnus.response.SuccessResponse;
import com.test.kopnus.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/v1/pekerjaan/")
public class PekerjaanController {

    @Autowired
    ValidationService validationService;

    @Autowired
    PekerjaanUserRepository pekerjaanUserRepository;

    @GetMapping("pekerjan-by-user/{userId}")
    public ResponseEntity getPekerjaanByUser(Integer userId, @RequestHeader("Authorization")String authHeader){
        if(!validationService.authorizationHeaderValidation(authHeader)){
            return ErrorResponse.generateResponseWithStatus("Authorization header can not validate", HttpStatus.UNAUTHORIZED.value(), "");
        }

        List<PekerjaanUserEntity> listPekerjaan = pekerjaanUserRepository.findPekerjaanUserEntitiesByUserId(userId);
        return SuccessResponse.generateSuccessResponse(listPekerjaan);
    }
}
