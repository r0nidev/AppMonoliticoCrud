package com.nlorenzo.repository;

import com.nlorenzo.entity.Rol;
import com.nlorenzo.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    Optional<Rol> findByRolNombre(RolNombre rolNombre);

    boolean existsByRolNombre(RolNombre rolNombre);

}
