package com.lucas.biblioteca.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<ProblemDetail> tratarRecursoNaoEncontrado(RecursoNaoEncontradoException exception) {
		ProblemDetail erro = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	@ExceptionHandler(DadoInvalidoException.class)
	public ResponseEntity<ProblemDetail> tratarDadoInvalido(DadoInvalidoException exception) {
		ProblemDetail erro = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	@ExceptionHandler(ConflitoNegocioException.class)
	public ResponseEntity<ProblemDetail> tratarConflitoNegocio(ConflitoNegocioException exception) {
		ProblemDetail erro = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}
}

