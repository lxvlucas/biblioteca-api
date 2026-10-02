package com.lucas.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucas.biblioteca.entities.Emprestimo;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long>{
	boolean existsByExemplar_IdAndDataDevolucaoIsNull(Long exemplarId); //existe emprestimo desse exemplar a qual a data de devolucao é nula? (se existe algum emprestimo com o exemplar com a data de devolucao nula)
	long countByLeitor_IdAndDataDevolucaoIsNull(Long leitorId); //conta quantas data de devolucoes nulas o leitor tem.
}
