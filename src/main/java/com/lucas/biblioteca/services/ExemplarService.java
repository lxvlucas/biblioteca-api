package com.lucas.biblioteca.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucas.biblioteca.entities.Exemplar;
import com.lucas.biblioteca.repositories.ExemplarRepository;

import jakarta.transaction.Transactional;

@Service
public class ExemplarService {
	private final ExemplarRepository exemplarRepository;
	
	public ExemplarService(ExemplarRepository exemplarRepository) {
		this.exemplarRepository = exemplarRepository;
	}
	
	@Transactional
	public Exemplar registrarExemplar(Exemplar exemplar) {
		if (exemplar.getId() != null) {
			throw new IllegalArgumentException("Exemplar novo não deve ter ID.");
		}
		if (exemplar.getLivro() == null) {
			throw new IllegalArgumentException("Exemplar novo precisa de um livro.");
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
