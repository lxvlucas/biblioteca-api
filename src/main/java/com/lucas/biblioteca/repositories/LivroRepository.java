package com.lucas.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucas.biblioteca.entities.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>{
	boolean existsByIsbn(String isbn);
}
