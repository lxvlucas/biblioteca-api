package com.lucas.biblioteca.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucas.biblioteca.entities.Autor;
import com.lucas.biblioteca.exceptions.ConflitoNegocioException;
import com.lucas.biblioteca.exceptions.DadoInvalidoException;
import com.lucas.biblioteca.exceptions.RecursoNaoEncontradoException;
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
			throw new DadoInvalidoException("Autor novo deve ter id nulo.");
		}
		
		if (autor.getNome() == null || autor.getNome().isBlank()) {
			throw new DadoInvalidoException("Informe nome do autor.");
		}
		//normaliza o nome
		String nomeNormalizado = autor.getNome().strip();
		//ve se o nome existe no repository
		if (autorRepository.findByNomeIgnoreCase(nomeNormalizado).isPresent()) {
			throw new ConflitoNegocioException("Este autor já existe.");
		}
		//seta nome normalizado
		autor.setNome(nomeNormalizado);
		
		return autorRepository.save(autor);
	}
	
	public Autor findById(Long id) {
		return autorRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Autor não encontrado."));
	}
	
	public List<Autor> findAll() {
		return autorRepository.findAll();
	}
}
