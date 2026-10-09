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

import com.lucas.biblioteca.dto.AutorRequest;
import com.lucas.biblioteca.dto.AutorResponse;
import com.lucas.biblioteca.entities.Autor;
import com.lucas.biblioteca.services.AutorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/autores")
public class AutorController {
	
	private final AutorService autorService;

	
	public AutorController(AutorService autorService) {
		this.autorService = autorService;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AutorResponse registrarAutor(@Valid @RequestBody AutorRequest autorRequest) {
		Autor autor = new Autor(autorRequest.nome());
		Autor autorSalvo = autorService.registrarAutor(autor);
		
		return new AutorResponse(autorSalvo.getId(), autorSalvo.getNome());
	}
	
	@GetMapping("/{id}")
	public AutorResponse findById(@PathVariable Long id) {
		Autor autor = autorService.findById(id);
		
		return new AutorResponse(autor.getId(), autor.getNome());
	}
	
	@GetMapping
	public List<AutorResponse> findAll() {
		List<AutorResponse> autoresResponse = new ArrayList<>();
		List<Autor> autores = autorService.findAll();
		for ( Autor a : autores) {
			autoresResponse.add(new AutorResponse(a.getId(), a.getNome()));
		}
		return autoresResponse;
	}
}
