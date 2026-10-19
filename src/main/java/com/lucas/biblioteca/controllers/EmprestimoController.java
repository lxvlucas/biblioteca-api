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

import com.lucas.biblioteca.dto.EmprestimoRequest;
import com.lucas.biblioteca.dto.EmprestimoResponse;
import com.lucas.biblioteca.entities.Emprestimo;
import com.lucas.biblioteca.entities.Exemplar;
import com.lucas.biblioteca.entities.Leitor;
import com.lucas.biblioteca.services.EmprestimoService;
import com.lucas.biblioteca.services.ExemplarService;
import com.lucas.biblioteca.services.LeitorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {
	private final EmprestimoService emprestimoService;
	private final LeitorService leitorService;
	private final ExemplarService exemplarService;
	
	public EmprestimoController(EmprestimoService emprestimoService, LeitorService leitorService, ExemplarService exemplarService) {
		this.emprestimoService = emprestimoService;
		this.leitorService = leitorService;
		this.exemplarService = exemplarService;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EmprestimoResponse registrarEmprestimo(@Valid @RequestBody EmprestimoRequest emprestimoRequest) {
		Leitor leitor = leitorService.findById(emprestimoRequest.leitorId());
		Exemplar exemplar = exemplarService.findById(emprestimoRequest.exemplarId());
		
		Emprestimo emprestimo = new Emprestimo(leitor, exemplar);
		
		Emprestimo emprestimoSalvo = emprestimoService.registrarEmprestimo(emprestimo);
		
		return new EmprestimoResponse(emprestimoSalvo.getId(), emprestimoSalvo.getDataEmprestimo(), emprestimoSalvo.getDataDevolucaoPrevista(), emprestimoSalvo.getDataDevolucao(), leitor.getId(), exemplar.getId());
	}
	
	@GetMapping("/{id}")
	public EmprestimoResponse findById(@PathVariable Long id) {
		Emprestimo emprestimo = emprestimoService.findById(id);
		
		return new EmprestimoResponse(emprestimo.getId(), emprestimo.getDataEmprestimo(), emprestimo.getDataDevolucaoPrevista(), emprestimo.getDataDevolucao(), emprestimo.getLeitor().getId(), emprestimo.getExemplar().getId());
	}
	
	@GetMapping
	public List<EmprestimoResponse> findAll() {
		List<EmprestimoResponse> emprestimosResponse = new ArrayList<>();
		List<Emprestimo> emprestimo = emprestimoService.findAll();
		for ( Emprestimo e : emprestimo ) {
			emprestimosResponse.add(new EmprestimoResponse(e.getId(), e.getDataEmprestimo(), e.getDataDevolucaoPrevista(), e.getDataDevolucao(), e.getLeitor().getId(), e.getExemplar().getId()));
		}
		return emprestimosResponse;
	}
	
	@PostMapping("/{id}/devolucao")
	public EmprestimoResponse registrarDevolucao(@PathVariable Long id) {
		Emprestimo devolucao = emprestimoService.resgistrarDevolucao(id);
		
		return new EmprestimoResponse(devolucao.getId(), devolucao.getDataEmprestimo(), devolucao.getDataDevolucaoPrevista(), devolucao.getDataDevolucao(), devolucao.getLeitor().getId(), devolucao.getExemplar().getId());
	}
}
