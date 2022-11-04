package com.test.kopnus.controller;

import com.test.kopnus.model.entity.UserEntity;
import com.test.kopnus.model.request.UserLoginRequest;
import com.test.kopnus.model.response.UserLoginResponse;
import com.test.kopnus.repository.UserRepository;
import com.test.kopnus.response.ErrorResponse;
import com.test.kopnus.response.SuccessResponse;
import com.test.kopnus.service.ValidationService;
import com.test.kopnus.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/v1/user/")
public class UserController {

    @Autowired
    ValidationService validationService;
    @Autowired
    UserRepository userRepository;

    @Operation(description = "Digunakan untuk melakukan login")
    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity userLogin(@RequestBody UserLoginRequest userLogin){
        if(userLogin == null){
            return ErrorResponse.generateResponseWithStatus("Request invalid", 400, null);
        }

        UserEntity userEntity = userRepository.getUserEntityByUserNameAndPassword(userLogin.getUserName(), userLogin.getPassword());
        if(userEntity==null){
            return ErrorResponse.generateResponseWithStatus("Username and password not found", HttpStatus.UNAUTHORIZED.value(), "");
        }


        Long currentMilis = System.currentTimeMillis();

        String jwtToken = JwtUtil.createJWT(userLogin.getUserName(), userLogin.getUserName(), "UserLogin", currentMilis, userEntity);
        UserLoginResponse userLoginResponse = new UserLoginResponse(userLogin.getUserName(), jwtToken);

        userEntity.setToken(jwtToken);
        userEntity.setLastUpdate(new Date(currentMilis));
        userRepository.save(userEntity);

        return SuccessResponse.generateSuccessResponse(userLoginResponse);
    }

    @Operation(description = "digunakan untuk mendapatkan semua user, hanya untuk admin")
    @GetMapping("all-user")
    public ResponseEntity getAllUser(@RequestHeader("Authorization") String requestHeader){
        if(!validationService.authorizationHeaderValidation(requestHeader)) {
            return ErrorResponse.generateResponseWithStatus("Token expired", HttpStatus.UNAUTHORIZED.value(), "");
        }

        if(validationService.isAdmin(requestHeader)){
            List<UserEntity> allUser =  userRepository.findAll();
            return SuccessResponse.generateSuccessResponse(allUser);
        }
        return ErrorResponse.generateResponseWithStatus("Only admin allowed", HttpStatus.UNAUTHORIZED.value(), "");
    }
}
