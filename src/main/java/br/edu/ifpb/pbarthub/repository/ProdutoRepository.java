package br.edu.ifpb.pbarthub.repository;

import br.edu.ifpb.pbarthub.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
