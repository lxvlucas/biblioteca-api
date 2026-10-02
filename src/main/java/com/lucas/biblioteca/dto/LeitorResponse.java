package com.lucas.biblioteca.dto;

public record LeitorResponse(
		Long id,
		String cpf,
		String nome,
		String email,
		String celular
		) {

}
