package com.lucas.biblioteca.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin extends Usuario{
	public Admin() {
		
	}
	
	public Admin(String nome, String cpf, String email, String celular) {
		super(nome, cpf, email, celular);
	}
	
}
