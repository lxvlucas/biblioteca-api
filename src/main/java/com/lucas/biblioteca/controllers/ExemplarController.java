package com.lucas.biblioteca.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.biblioteca.dto.ExemplarRequest;
import com.lucas.biblioteca.dto.ExemplarResponse;
import com.lucas.biblioteca.entities.Exemplar;
import com.lucas.biblioteca.entities.Livro;
import com.lucas.biblioteca.services.ExemplarService;
import com.lucas.biblioteca.services.LivroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/exemplares")
public class ExemplarController {
	private final ExemplarService exemplarService;
	private final LivroService livroService;
	
	public ExemplarController(ExemplarService exemplarService, LivroService livroService) {
		this.exemplarService = exemplarService;
		this.livroService = livroService;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ExemplarResponse registrarExemplar(@Valid @RequestBody ExemplarRequest exemplarRequest) {
		Livro livro = livroService.findById(exemplarRequest.livroId());
		
		Exemplar exemplar = new Exemplar(livro);
		
		Exemplar exemplarSalvo = exemplarService.registrarExemplar(exemplar);
		
		return new ExemplarResponse(exemplarSalvo.getId(), livro.getId());
	}
	
	@GetMapping("/{id}")
	public ExemplarResponse findById(@PathVariable Long id) {
		Exemplar exemplar = exemplarService.findById(id);
		return new ExemplarResponse(exemplar.getId(), exemplar.getLivro().getId());
	}
	
	@GetMapping
	public List<ExemplarResponse> findAll() {
		List<ExemplarResponse> exemplaresResponse = new ArrayList<>();
		List<Exemplar> exemplares = exemplarService.findAll();
		for ( Exemplar e : exemplares) {
			exemplaresResponse.add(new ExemplarResponse(e.getId(), e.getLivro().getId()));
		}
		return exemplaresResponse;
	}
}
