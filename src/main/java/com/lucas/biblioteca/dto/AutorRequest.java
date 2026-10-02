package com.lucas.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;

public record AutorRequest(
		@NotBlank
		String nome
		) {

}
