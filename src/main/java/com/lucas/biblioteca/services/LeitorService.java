package com.lucas.biblioteca.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucas.biblioteca.entities.Leitor;
import com.lucas.biblioteca.repositories.LeitorRepository;

import jakarta.transaction.Transactional;

@Service
public class LeitorService {
	private final LeitorRepository leitorRepository;
	
	public LeitorService(LeitorRepository leitorRepository) {
		this.leitorRepository = leitorRepository;
	}
	
	@Transactional
	public Leitor registrarLeitor(Leitor leitor) {
		if (leitor.getId() != null) {
			throw new IllegalArgumentException("Leitor novo não deve ter ID.");
		}
		//nome
		if (leitor.getNome() == null || leitor.getNome().isBlank()) {
		    throw new IllegalArgumentException("Informe o nome do leitor.");
		}
		//verificador de CPF
		if (leitor.getCpf() == null || leitor.getCpf().isBlank()) {
			throw new IllegalArgumentException("Leitor precisa de um CPF.");
		}
		if (leitorRepository.existsByCpf(leitor.getCpf())) {
			throw new IllegalArgumentException("Já existe um leitor com este CPF");
		}
		
		//verificador de email
		if (leitor.getEmail() == null || leitor.getEmail().isBlank()) {
			throw new IllegalArgumentException("Leitor precisa de um email.");
		}
		if (leitorRepository.existsByEmail(leitor.getEmail())) {
			throw new IllegalArgumentException("Já existe um leitor com este email");
		}
		//celular 
		if (leitor.getCelular() == null || leitor.getCelular().isBlank()) {
			throw new IllegalArgumentException("Leitor precisa de um Celular.");
		}
		
		return leitorRepository.save(leitor);
	}
	
	public Leitor findById(Long id) {
		return leitorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Nenhum leitor encontrado."));
	}
	
	public List<Leitor> findAll(){
		return leitorRepository.findAll();
	}
}
