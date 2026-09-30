package com.lucas.biblioteca.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucas.biblioteca.entities.Autor;
import com.lucas.biblioteca.repositories.AutorRepository;

import jakarta.transaction.Transactional;

@Service
public class AutorService {
	private final AutorRepository autorRepository;
	
	public AutorService(AutorRepository autorRepository) {
		this.autorRepository = autorRepository;
	}
	
	@Transactional
	public Autor registrarAutor(Autor autor) {
		if (autor.getId() != null) {
			throw new IllegalArgumentException("Autor novo deve ter id nulo.");
		}
		
		if (autor.getNome() == null || autor.getNome().isBlank()) {
			throw new IllegalArgumentException("Informe nome do autor.");
		}
		
		if (autorRepository.existsByNome(autor.getNome())) {
			throw new IllegalArgumentException("Este autor já existe.");
		}
		return autorRepository.save(autor);
	}
	
	public Autor findById(Long id) {
		return autorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Autor não encontrado."));
	}
	
	public List<Autor> findAll() {
		return autorRepository.findAll();
	}
}
