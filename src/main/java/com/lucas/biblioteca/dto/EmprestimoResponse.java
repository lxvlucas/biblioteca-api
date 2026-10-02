package com.lucas.biblioteca.dto;

import java.time.LocalDate;

import com.lucas.biblioteca.entities.Exemplar;
import com.lucas.biblioteca.entities.Leitor;

public record EmprestimoResponse(
		Long id,
		LocalDate dataEmprestimo,
		LocalDate dataDevolucaoPrevista,
		LocalDate dataDevolucao,
		Long leitorId,
		Long exemplarId
		) {

}
