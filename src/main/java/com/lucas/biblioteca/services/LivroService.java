package com.lucas.biblioteca.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucas.biblioteca.entities.Autor;
import com.lucas.biblioteca.entities.Livro;
import com.lucas.biblioteca.exceptions.RecursoNaoEncontradoException;
import com.lucas.biblioteca.repositories.AutorRepository;
import com.lucas.biblioteca.repositories.LivroRepository;

import jakarta.transaction.Transactional;

@Service
public class LivroService {
	//inserir os que eu preciso
	private final LivroRepository livroRepository;
	private final AutorRepository autorRepository;
	
	//fazer os cronstuctors
	 public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
		 this.livroRepository = livroRepository;
		 this.autorRepository = autorRepository;
	 }
	
	@Transactional
	public Livro registrarLivro (Livro livro) {
		if (livro.getId() != null) {
			throw new RecursoNaoEncontradoException("Um livro novo não deve ter ID.");
		}
		// verificador de ISBN/ verifica se está em branco
		if (livro.getIsbn() == null || livro.getIsbn().isBlank()) {
			throw new RecursoNaoEncontradoException("Informe o ISBN do livro.");
		}
		//verifica se o ISBN ja existe
		if (livroRepository.existsByIsbn(livro.getIsbn())) {
			throw new RecursoNaoEncontradoException("ISBN já existente");
		}
		
		//verifcador de autor
		// pega o autor informado no cadastro do livro
		Autor autorInformado = livro.getAutor();
		// se autor nulo, pede para digitar um
		if (autorInformado == null) {
			throw new RecursoNaoEncontradoException("Informe um autor.");
		}
		
		Autor autor;
		
		//se for diferente de nulo, vai buscar
		if (autorInformado.getId() != null) {
			autor = autorRepository.findById(autorInformado.getId()).orElseThrow(() -> new RecursoNaoEncontradoException("Autor não encontrado."));
		} else {
			String nome = autorInformado.getNome();
			//se nome for nulo ou branco, pede para informar um nome
			if (nome == null || nome.isBlank()) {
				throw new RecursoNaoEncontradoException("Informe o nome do autor.");
			}
			String nomeNormalizado = nome.strip();
			
			autor = autorRepository.findByNomeIgnoreCase(nomeNormalizado).orElseGet(() -> autorRepository.save(new Autor(nomeNormalizado)));
		}
		livro.setAutor(autor);
		//autor verificado
		
		return livroRepository.save(livro);
	}
	
	public Livro findById(Long id) {
		return livroRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado."));
	}
	
	public List<Livro> findAll() {
		return livroRepository.findAll();
	}
	

}
