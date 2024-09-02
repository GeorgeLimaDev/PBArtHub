package br.edu.ifpb.pbarthub.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O campo descrição é obrigatório.")
    private String descricao;

    @Positive(message = "O preço deve ser maior que zero.")
    private Double preco;

    @NotBlank(message = "O campo URL da imagem é obrigatório.")
    private String imagemUrl;
}
