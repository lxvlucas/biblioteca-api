package com.lucas.biblioteca.dto;

import com.lucas.biblioteca.entities.enums.Categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivroRequest(
		@NotBlank
		String titulo,
		Long autorId,
		String nomeAutor,
		Integer edicao,
		Integer ano,
		@NotNull
		Categoria categoria,
		@NotBlank
		String isbn
		) {

}
