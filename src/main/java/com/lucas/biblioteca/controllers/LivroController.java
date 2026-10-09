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

import com.lucas.biblioteca.dto.AutorResponse;
import com.lucas.biblioteca.dto.LivroRequest;
import com.lucas.biblioteca.dto.LivroResponse;
import com.lucas.biblioteca.entities.Autor;
import com.lucas.biblioteca.entities.Livro;
import com.lucas.biblioteca.services.AutorService;
import com.lucas.biblioteca.services.LivroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros")
public class LivroController {
	//setando livroService e autorService
	private final LivroService livroService;
	private final AutorService autorService;
	//constructor
	public LivroController(LivroService livroService, AutorService autorService) {
		this.livroService = livroService;
		this.autorService = autorService;
	}
	//post do cadastro
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public LivroResponse registrarLivro(@Valid @RequestBody LivroRequest livroRequest) {
		//criando um autor
		Autor autor = null;
		// caso tenha id do autor
		if (livroRequest.autorId() != null) {
			autor = autorService.findById(livroRequest.autorId());
		//caso nao tenha id mas tenha nome
		} else if (livroRequest.nomeAutor() != null) {
			autor = new Autor(livroRequest.nomeAutor());
		}
		//instanciando um livro
		Livro livro = new Livro(livroRequest.titulo(), autor, livroRequest.categoria(), livroRequest.edicao(), livroRequest.ano(), livroRequest.isbn());
		//registrando livro
		Livro livroSalvo = livroService.registrarLivro(livro);
		//transformando o autor do livro em autorResponse para retornar um LivroResponse
		AutorResponse autorResponse = new AutorResponse(livro.getAutor().getId(), livro.getAutor().getNome());
		//retornando livroResponse
		return new LivroResponse(livroSalvo.getId(), livroSalvo.getTitulo(), autorResponse, livroSalvo.getEdicao(), livroSalvo.getAno(), livroSalvo.getCategoria(), livroSalvo.getIsbn());
	}
	//consulta de um livro
	@GetMapping("/{id}")
	public LivroResponse findById(@PathVariable Long id) {
		//acha livro pelo Id
		Livro livro = livroService.findById(id);
		//fazendo o autorResponse pra retornar o livroResponse
		AutorResponse autor = new AutorResponse(livro.getAutor().getId(), livro.getAutor().getNome());
		//retornando o livro
		return new LivroResponse(livro.getId(), livro.getTitulo(), autor, livro.getEdicao(), livro.getAno(),livro.getCategoria(), livro.getIsbn());
	}
	//consultando todos os livros
	@GetMapping
	public List<LivroResponse> findAll() {
		//cria arraylist pros livrosResponses
		List<LivroResponse> livrosResponse = new ArrayList<>();
		//Pega todos os livros
		List<Livro> livros = livroService.findAll();
		//transforma cada livro em um livroResponse e adiciona na arrayList (tambem faz o autorResponse pra cada livroResponse)
		for ( Livro l : livros ) {
			AutorResponse autorTemp = new AutorResponse(l.getAutor().getId(), l.getAutor().getNome());
			livrosResponse.add(new LivroResponse(l.getId(), l.getTitulo(), autorTemp, l.getEdicao(), l.getAno(), l.getCategoria(), l.getIsbn()));
		}
		//retorna a ArrayList.
		return livrosResponse;
	}
}
