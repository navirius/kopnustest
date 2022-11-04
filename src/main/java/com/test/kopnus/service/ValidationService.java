package com.test.kopnus.service;

import com.google.gson.Gson;
import com.test.kopnus.model.entity.LevelEntity;
import com.test.kopnus.model.entity.UserEntity;
import com.test.kopnus.repository.LevelRepository;
import com.test.kopnus.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class ValidationService {
    public UserEntity getUserEntity(String authHeader){
        try {
            String jwtToken  = authHeader.replace("Bearer ", "");
            Claims claims = JwtUtil.decodeJWT(jwtToken);
            String jsonObject = claims.get(JwtUtil.DEFAULT_USERNAME_KEY).toString();
            UserEntity userEntity = new Gson().fromJson(jsonObject, UserEntity.class);
            return userEntity;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public boolean authorizationHeaderValidation(String authHeader){
        try {
            if(authHeader == null || StringUtils.isEmpty(authHeader))
                return false;
            UserEntity userEntity = getUserEntity(authHeader);
            if(userEntity!=null){
                Date nextDate = DateUtils.addDays(new Date(), 2);
                if(userEntity.getLastUpdate().after(nextDate))
                    return false;
                else
                    return true;
            }
            return false;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

    }

    @Autowired
    LevelRepository levelRepository;
    public boolean isAdmin(String authHeader){
        UserEntity userEntity = getUserEntity(authHeader);
        Optional<LevelEntity> level = levelRepository.findById(userEntity.getLevel());
        if(level.isEmpty())
            return false;
        else{
            if(level.get().getLevelName().equalsIgnoreCase("admin"))
                return true;
            return false;
        }
    }
}
