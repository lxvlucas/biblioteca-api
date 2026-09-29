package com.lucas.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucas.biblioteca.entities.Exemplar;

@Repository
public interface ExemplarRepository extends JpaRepository<Exemplar, Long>{

}
