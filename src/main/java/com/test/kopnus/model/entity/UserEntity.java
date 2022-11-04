package com.test.kopnus.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Component
@Entity
@Data
@Table(schema = "public", catalog = "kopnus", name = "userlogin")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Integer userId;

    @Basic
    @Column(name="username")
    private String userName;

    @Column(name="password")
    private String password;

    @Column(name="token")
    private String token;

    @Column(name = "lastupdate")
    private Date lastUpdate;

    @Column(name = "level")
    private Integer level;

    @Override
    public boolean equals(Object o){
        return true;
    }

    @Override
    public int hashCode(){
        return 0;
    }

    @JsonIgnore
    @JoinColumn(name = "level", referencedColumnName = "levelid", updatable = false, insertable = false)
    @OneToOne
    @Setter(AccessLevel.NONE)
    private LevelEntity userLevel;
}
