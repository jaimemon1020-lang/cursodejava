package com.curso.catalogo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    List<Cliente> findByNombreContaining(String fragmento);

    @Query("SELECT c FROM Cliente c WHERE c.direccionEnvio LIKE %:ciudad%")
    List<Cliente> buscarPorCiudad(@Param("ciudad") String ciudad);
}
