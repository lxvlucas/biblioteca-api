package com.lucas.biblioteca.dto;

import com.lucas.biblioteca.entities.Livro;

import jakarta.validation.constraints.NotNull;

public record ExemplarRequest(
		@NotNull
		Long livroId
		) {

}
