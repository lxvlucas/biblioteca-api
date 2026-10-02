package com.lucas.biblioteca.dto;

import com.lucas.biblioteca.entities.Livro;

public record ExemplarResponse(
		Long id,
		Long livroId
		) {

}
