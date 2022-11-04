package com.test.kopnus.model.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Entity
@Component
@Table(schema = "pekerjaanuser", catalog = "public")
public class PekerjaanUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="userid")
    private Integer userId;
    @Column(name = "pekerjaanid")
    private Integer pekerjaanId;
}
