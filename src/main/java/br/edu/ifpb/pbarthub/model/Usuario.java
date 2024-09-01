package br.edu.ifpb.pbarthub.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O campo email é obrigatório.")
    @Email(message = "Por favor, insira um email válido.")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "O campo senha é obrigatório.")
    private String senha;
}
