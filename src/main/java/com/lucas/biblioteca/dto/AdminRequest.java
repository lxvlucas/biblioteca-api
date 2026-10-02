package com.lucas.biblioteca.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AdminRequest(
		@NotBlank
		String nome,
		@NotBlank
		@Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF deve seguir o formato 000.000.000-00")
		String cpf,
		@NotBlank
		@Email
		@Size(max = 255)
		String email,
		@NotBlank
		String celular
		) {

}
