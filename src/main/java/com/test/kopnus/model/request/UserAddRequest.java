package com.test.kopnus.model.request;

import lombok.Data;

@Data
public class UserAddRequest {
    String userName;
    String password;
    Integer userLevel;
}
