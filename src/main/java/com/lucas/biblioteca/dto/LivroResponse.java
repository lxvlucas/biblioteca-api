package com.lucas.biblioteca.dto;

import com.lucas.biblioteca.entities.Autor;
import com.lucas.biblioteca.entities.enums.Categoria;

public record LivroResponse(
		Long id,
		String titulo,
		AutorResponse autor,
		Integer edicao,
		Integer ano,
		Categoria categoria,
		String isbn
		) {

}
