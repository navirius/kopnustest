package com.test.kopnus.controller;

import com.test.kopnus.model.entity.UserEntity;
import com.test.kopnus.model.request.UserAddRequest;
import com.test.kopnus.repository.UserRepository;
import com.test.kopnus.response.ErrorResponse;
import com.test.kopnus.response.SuccessResponse;
import com.test.kopnus.service.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/v1/useradmin/")
public class UserAdminController {
    @Autowired
    ValidationService validationService;

    @Autowired
    UserRepository userRepository;

    @Operation(description = "API to add more user, only admin")
    @PostMapping("add-user")
    public ResponseEntity addUser(@RequestBody UserAddRequest requestBody, @RequestHeader("Authorization") String authHeader){
        if(!validationService.isAdmin(authHeader)){
            return ErrorResponse.generateResponseWithStatus("Only admin allowed", HttpStatus.UNAUTHORIZED.value(), "");
        }

        UserEntity userEntity=new UserEntity();
        userEntity.setUserName(requestBody.getUserName());
        userEntity.setPassword(requestBody.getPassword());
        userEntity.setLevel(requestBody.getUserLevel());
        UserEntity saved = userRepository.save(userEntity);

        return SuccessResponse.generateSuccessResponse(saved);
    }

    @Operation(description = "setting level user/hak akses user, hanya untuk admin")
    @PostMapping("set-user-level/{userId}/{level}")
    public ResponseEntity setUserLevel(Integer userId, Integer level,  @RequestHeader("Authorization") String authHeader){
        if(!validationService.isAdmin(authHeader)){
            return ErrorResponse.generateResponseWithStatus("Only admin allowed", HttpStatus.UNAUTHORIZED.value(), "");
        }

        UserEntity userEntity = userRepository.getReferenceById(userId);
        if(userEntity!=null){
            userEntity.setLevel(level);
            UserEntity saved = userRepository.save(userEntity);
            return SuccessResponse.generateSuccessResponse(saved);
        }

        return ErrorResponse.generateResponseWithStatus("Data not found", HttpStatus.NO_CONTENT.value(), "");
    }
}
