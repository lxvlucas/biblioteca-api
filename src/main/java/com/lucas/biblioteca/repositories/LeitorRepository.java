package com.lucas.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucas.biblioteca.entities.Leitor;

@Repository
public interface LeitorRepository extends JpaRepository<Leitor, Long>{

}
