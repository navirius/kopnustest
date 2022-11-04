package com.test.kopnus.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Component
@Table(schema = "public", catalog = "kopnus", name = "userlevel")
public class LevelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "levelid")
    private Integer levelId;

    @Column(name = "levelname")
    private String levelName;

}
