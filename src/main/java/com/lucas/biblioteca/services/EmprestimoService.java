package com.lucas.biblioteca.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lucas.biblioteca.entities.Emprestimo;
import com.lucas.biblioteca.repositories.EmprestimoRepository;

import jakarta.transaction.Transactional;

@Service
public class EmprestimoService {
	private final EmprestimoRepository emprestimoRepository;
	
	public EmprestimoService(EmprestimoRepository emprestimoRepository) {
		this.emprestimoRepository = emprestimoRepository;
	}
	
	@Transactional
	public Emprestimo registrarEmprestimo(Emprestimo emprestimo) {
		if (emprestimo.getId() != null) {
			throw new IllegalArgumentException("Emprestimo novo não deve ter ID preenchido.");
		}
		
		if (emprestimo.getDataEmprestimo() != null) {
			throw new IllegalArgumentException("Data de emprestimo precisa estar vazia.");
		}
		
		if (emprestimo.getDataDevolucaoPrevista() != null) {
			throw new IllegalArgumentException("Data de devolução prevista precisa estar vazia.");
		}
		
		if (emprestimo.getDataDevolucao() != null) {
			throw new IllegalArgumentException("Data de devolução precisa estar vazia.");
		}
		
		LocalDate dataEmprestimo = LocalDate.now();
		LocalDate dataDevolucaoPrevista = dataEmprestimo.plusDays(14);
		
		emprestimo.setDataEmprestimo(dataEmprestimo);
		emprestimo.setDataDevolucaoPrevista(dataDevolucaoPrevista);
		
		if (emprestimo.getLeitor() == null) {
			throw new IllegalArgumentException("Emprestimo precisa estar vinculado a um leitor.");
		}
		
		if (emprestimo.getExemplar() == null) {
			throw new IllegalArgumentException("Emprestimo precisa estar vinculado a um exemplar de um livro.");
		}
		return emprestimoRepository.save(emprestimo);
	}
	
	public Emprestimo findById(Long id) {
		return emprestimoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Emprestimo não encontrado."));
	}
	
	public List<Emprestimo> findAll() {
		return emprestimoRepository.findAll();
	}
}
