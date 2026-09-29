package com.lucas.biblioteca.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "leitores")
public class Leitor extends Usuario {
	public Leitor() {
		super();
	}

	public Leitor(String nome, String cpf, String email, String celular) {
		super(nome, cpf, email, celular);
	}
	
	
}
