package com.test.kopnus.model.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Entity
@Component
@Table(schema = "pekerjaan", catalog = "public")
public class PekerjaanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pekerjaanid")
    private Integer pekerjaanId;

    @Column(name = "namapekerjaan")
    private String namaPekerjaan;
}
