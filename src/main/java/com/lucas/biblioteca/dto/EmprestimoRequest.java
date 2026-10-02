package com.lucas.biblioteca.dto;

import java.time.LocalDate;

import com.lucas.biblioteca.entities.Exemplar;
import com.lucas.biblioteca.entities.Leitor;

import jakarta.validation.constraints.NotNull;

public record EmprestimoRequest(
		@NotNull
		Long leitorId,
		@NotNull
		Long exemplarId
		) {

}
