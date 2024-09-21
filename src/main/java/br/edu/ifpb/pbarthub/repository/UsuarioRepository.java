package br.edu.ifpb.pbarthub.repository;

import br.edu.ifpb.pbarthub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);


    @Query("SELECT COALESCE(MAX(u.id), 0) FROM Usuario u")
    Integer findMaxId();

}
