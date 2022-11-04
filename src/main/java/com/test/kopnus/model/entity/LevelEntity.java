package com.test.kopnus.model.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Entity
@Component
@Table(schema = "userlevel", catalog = "public")
public class LevelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "levelid")
    private Integer levelId;

    @Column(name = "levelname")
    private String levelName;
}
