package com.lucas.biblioteca.entities;

import com.lucas.biblioteca.entities.enums.Categoria;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "livros")
public class Livro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String titulo;
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "autor_id" ,nullable = false)
	private Autor autor;
	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(nullable = false)
	private Categoria categoria;
	private Integer edicao;
	private Integer ano;
	@NotBlank
	@Column(nullable = false, unique = true)
	private String isbn;
	
	public Livro() {
		
	}

	

	public Livro(@NotBlank String titulo, @NotNull Autor autor, @NotNull Categoria categoria, Integer edicao, Integer ano, @NotBlank String isbn) {
		this.titulo = titulo;
		this.autor = autor;
		this.categoria = categoria;
		this.edicao = edicao;
		this.ano = ano;
		this.isbn = isbn;
	}



	public Long getId() {
		return id;
	}


	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	
	public Autor getAutor() {
		return autor;
	}



	public void setAutor(Autor autor) {
		this.autor = autor;
	}



	public Integer getEdicao() {
		return edicao;
	}

	public void setEdicao(Integer edicao) {
		this.edicao = edicao;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}



	public Categoria getCategoria() {
		return categoria;
	}



	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
}
