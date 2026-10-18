package com.lucas.biblioteca.exceptions;

public class ConflitoNegocioException extends RuntimeException{
	public ConflitoNegocioException(String mensagem) {
		super(mensagem);
	}
}
