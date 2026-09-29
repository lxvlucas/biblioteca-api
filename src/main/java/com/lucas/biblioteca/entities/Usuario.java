package com.lucas.biblioteca.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@MappedSuperclass
public abstract class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	@Column(nullable = false)
	private String nome;
	@NotBlank
	@Column(nullable = false, unique = true)
	@Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF deve seguir o formato 000.000.000-00")
	private String cpf;
	@NotBlank
	@Email
	@Size(max = 255)
	@Column(nullable = false, unique = true)
	private String email;
	@NotBlank
	@Column(nullable = false)
	private String celular;
	
	protected Usuario() {
		
	}

	public Usuario(@NotBlank String nome, @NotBlank @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF deve seguir o formato 000.000.000-00") String cpf, @NotBlank @Email @Size(max = 255) String email, @NotBlank String celular) {
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.celular = celular;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	
}
