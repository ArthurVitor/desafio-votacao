package com.dbserver.votacao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity()
@Table(name = "tb_pauta")
@AllArgsConstructor()
@NoArgsConstructor()
@Getter()
@Setter()
public class Pauta {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column()
    private String description;

    @Column()
    private LocalDateTime creationDate = LocalDateTime.now();
}
