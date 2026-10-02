package com.lucas.biblioteca.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucas.biblioteca.entities.Exemplar;
import com.lucas.biblioteca.entities.Livro;
import com.lucas.biblioteca.repositories.ExemplarRepository;
import com.lucas.biblioteca.repositories.LivroRepository;

import jakarta.transaction.Transactional;

@Service
public class ExemplarService {
	private final ExemplarRepository exemplarRepository;
	private final LivroRepository livroRepository;
	
	public ExemplarService(ExemplarRepository exemplarRepository, LivroRepository livroRepository) {
		this.exemplarRepository = exemplarRepository;
		this.livroRepository = livroRepository;
	}
	
	@Transactional
	public Exemplar registrarExemplar(Exemplar exemplar) {
		if (exemplar.getId() != null) {
			throw new IllegalArgumentException("Exemplar novo não deve ter ID.");
		}
		if (exemplar.getLivro() == null) {
			throw new IllegalArgumentException("Exemplar novo precisa de um livro.");
		}
		if (exemplar.getLivro().getId() == null) {
			throw new IllegalArgumentException("Livro inválido.");
		}
		if (livroRepository.existsById(exemplar.getLivro().getId())) {
			Livro livroInformado = livroRepository.findById(exemplar.getLivro().getId()).orElseThrow(() -> new IllegalArgumentException("Livro não encontrado."));
			exemplar.setLivro(livroInformado);
		} else {
			throw new IllegalArgumentException("Livro não encontrado.");
		}
		return exemplarRepository.save(exemplar);
	}
	
	public Exemplar findById(Long id) {
		return exemplarRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Exemplar não encontrado."));
	}
	
	public List<Exemplar> findAll() {
		return exemplarRepository.findAll();
	}
}
