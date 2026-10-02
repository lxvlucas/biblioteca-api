package com.lucas.biblioteca.dto;

public record AdminResponse(
		Long id,
		String cpf,
		String nome,
		String email,
		String celular
		) {

}
