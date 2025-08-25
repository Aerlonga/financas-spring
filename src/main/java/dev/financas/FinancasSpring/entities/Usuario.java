package dev.financas.FinancasSpring.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Getter
@Setter
@Entity
@Table(name = "usuarios")
@NoArgsConstructor

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomeCompleto", nullable = false, length = 255)
    private String nome;

    @Column(name = "email", nullable = false, length = 255)


}
