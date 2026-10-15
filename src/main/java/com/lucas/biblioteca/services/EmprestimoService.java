package com.lucas.biblioteca.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lucas.biblioteca.entities.Emprestimo;
import com.lucas.biblioteca.entities.Exemplar;
import com.lucas.biblioteca.entities.Leitor;
import com.lucas.biblioteca.exceptions.RecursoNaoEncontradoException;
import com.lucas.biblioteca.repositories.EmprestimoRepository;
import com.lucas.biblioteca.repositories.ExemplarRepository;
import com.lucas.biblioteca.repositories.LeitorRepository;

import jakarta.transaction.Transactional;

@Service
public class EmprestimoService {
	private final EmprestimoRepository emprestimoRepository;
	private final LeitorRepository leitorRepository;
	private final ExemplarRepository exemplarRepository;
	
	public EmprestimoService(EmprestimoRepository emprestimoRepository, LeitorRepository leitorRepository, ExemplarRepository exemplarRepository) {
		this.emprestimoRepository = emprestimoRepository;
		this.leitorRepository = leitorRepository;
		this.exemplarRepository = exemplarRepository;
	}
	
	@Transactional
	public Emprestimo registrarEmprestimo(Emprestimo emprestimo) {
		if (emprestimo.getId() != null) {
			throw new RecursoNaoEncontradoException("Emprestimo novo não deve ter ID preenchido.");
		}
		
		if (emprestimo.getDataEmprestimo() != null) {
			throw new RecursoNaoEncontradoException("Data de emprestimo precisa estar vazia.");
		}
		
		if (emprestimo.getDataDevolucaoPrevista() != null) {
			throw new RecursoNaoEncontradoException("Data de devolução prevista precisa estar vazia.");
		}
		
		if (emprestimo.getDataDevolucao() != null) {
			throw new RecursoNaoEncontradoException("Data de devolução precisa estar vazia.");
		}
		
		LocalDate dataEmprestimo = LocalDate.now();
		LocalDate dataDevolucaoPrevista = dataEmprestimo.plusDays(14);
		
		emprestimo.setDataEmprestimo(dataEmprestimo);
		emprestimo.setDataDevolucaoPrevista(dataDevolucaoPrevista);
		
		if (emprestimo.getLeitor() == null) {
			throw new RecursoNaoEncontradoException("Emprestimo precisa estar vinculado a um leitor.");
		}
		
		if (emprestimo.getLeitor().getId() == null) {
			throw new RecursoNaoEncontradoException("Leitor inválido.");
		}
		
		if (emprestimo.getExemplar() == null) {
			throw new RecursoNaoEncontradoException("Emprestimo precisa estar vinculado a um exemplar de um livro.");
		}
		
		if (emprestimo.getExemplar().getId() == null) {
			throw new RecursoNaoEncontradoException("Exemplar inválido.");
		}
		
		if (leitorRepository.existsById(emprestimo.getLeitor().getId())) {
			Leitor leitorInformado = leitorRepository.findById(emprestimo.getLeitor().getId()).orElseThrow(() -> new RecursoNaoEncontradoException("Leitor não encontrado."));
			emprestimo.setLeitor(leitorInformado);
		} else {
			throw new RecursoNaoEncontradoException("Leitor não encontrado.");
		}
		if (emprestimoRepository.countByLeitor_IdAndDataDevolucaoIsNull(emprestimo.getLeitor().getId()) >= 3) {
			throw new RecursoNaoEncontradoException("Leitor já está com 3 emprestimos ativos.");
		}
		if (exemplarRepository.existsById(emprestimo.getExemplar().getId())) {
			Exemplar exemplarInformado = exemplarRepository.findById(emprestimo.getExemplar().getId()).orElseThrow(() -> new RecursoNaoEncontradoException("Exemplar não encontrado."));
			emprestimo.setExemplar(exemplarInformado);
		} else {
			throw new RecursoNaoEncontradoException("Exemplar não encontrado.");
		}
		
		if (emprestimoRepository.existsByExemplar_IdAndDataDevolucaoIsNull(emprestimo.getExemplar().getId())) {
			throw new RecursoNaoEncontradoException("Exemplar já está alocado.");
		} 
		return emprestimoRepository.save(emprestimo);
	}
	@Transactional
	public Emprestimo resgistrarDevolução(Long emprestimoId) {
		if (emprestimoId == null) {
			throw new RecursoNaoEncontradoException("Informe um ID.");
		}
		Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId).orElseThrow(() -> new RecursoNaoEncontradoException("Emprestimo não encontrado."));
		if (emprestimo.getLeitor().getId() == null) {
			throw new RecursoNaoEncontradoException("Leitor inválido.");
		}
		if (emprestimo.getExemplar().getId() == null) {
			throw new RecursoNaoEncontradoException("Exemplar inválido.");
		}
		if (emprestimo.getDataDevolucao() != null) {
			throw new RecursoNaoEncontradoException("Exemplar já devolvido.");
		}
		emprestimo.setDataDevolucao(LocalDate.now());
		return emprestimoRepository.save(emprestimo);
	}
	
	public Emprestimo findById(Long id) {
		return emprestimoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Emprestimo não encontrado."));
	}
	
	public List<Emprestimo> findAll() {
		return emprestimoRepository.findAll();
	}
}
