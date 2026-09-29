package com.lucas.biblioteca.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "emprestimos")
public class Emprestimo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Column(nullable = false)
	private LocalDate dataEmprestimo;
	@NotNull
	@Column(nullable = false)
	private LocalDate dataDevolucaoPrevista;
	
	private LocalDate dataDevolucao;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "leitor_id", nullable = false)
	private Leitor leitor;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "exemplar_id", nullable = false)
	private Exemplar exemplar;
	
	public Emprestimo() {
		
	}

	public Emprestimo(@NotNull LocalDate dataEmprestimo, @NotNull LocalDate dataDevolucaoPrevista, LocalDate dataDevolucao, Leitor leitor, Exemplar exemplar) {
		this.dataEmprestimo = dataEmprestimo;
		this.dataDevolucaoPrevista = dataDevolucaoPrevista;
		this.dataDevolucao = dataDevolucao;
		this.leitor = leitor;
		this.exemplar = exemplar;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(LocalDate dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public LocalDate getDataDevolucaoPrevista() {
		return dataDevolucaoPrevista;
	}

	public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
		this.dataDevolucaoPrevista = dataDevolucaoPrevista;
	}

	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Leitor getLeitor() {
		return leitor;
	}

	public void setLeitor(Leitor leitor) {
		this.leitor = leitor;
	}

	public Exemplar getExemplar() {
		return exemplar;
	}

	public void setExemplar(Exemplar exemplar) {
		this.exemplar = exemplar;
	}
	
	
}
