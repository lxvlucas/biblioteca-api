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

import com.lucas.biblioteca.dto.LeitorRequest;
import com.lucas.biblioteca.dto.LeitorResponse;
import com.lucas.biblioteca.entities.Leitor;
import com.lucas.biblioteca.services.LeitorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/leitores")
public class LeitorController {
	private final LeitorService leitorService;
	
	public LeitorController(LeitorService leitorService) {
		this.leitorService = leitorService;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public LeitorResponse registrarLeitor(@Valid @RequestBody LeitorRequest leitorRequest) {
		Leitor leitor = new Leitor(leitorRequest.nome(), leitorRequest.cpf(), leitorRequest.email(), leitorRequest.celular());
		
		Leitor leitorSalvo = leitorService.registrarLeitor(leitor);
		
		return new LeitorResponse(leitorSalvo.getId(), leitorSalvo.getCpf(), leitorSalvo.getNome(), leitorSalvo.getEmail(), leitorSalvo.getCelular());
	}
	
	@GetMapping("/{id}")
	public LeitorResponse findById(@PathVariable Long id) {
		Leitor leitor = leitorService.findById(id);
		return new LeitorResponse(leitor.getId(), leitor.getCpf(), leitor.getNome(), leitor.getEmail(), leitor.getCelular());
	}
	
	@GetMapping
	public List<LeitorResponse> findAll() {
		List<LeitorResponse> leitoresResponse = new ArrayList<>();
		List<Leitor> leitores = leitorService.findAll();
		for ( Leitor l : leitores ) {
			leitoresResponse.add(new LeitorResponse(l.getId(), l.getCpf(), l.getNome(), l.getEmail(), l.getCelular()));
		}
		return leitoresResponse;
	}
}
