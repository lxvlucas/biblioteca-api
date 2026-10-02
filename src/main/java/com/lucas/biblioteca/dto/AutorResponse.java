package com.lucas.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;

public record AutorResponse(
		Long id,
		String nome) {

}
