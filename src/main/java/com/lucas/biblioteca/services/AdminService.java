package com.lucas.biblioteca.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lucas.biblioteca.entities.Admin;
import com.lucas.biblioteca.repositories.AdminRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminService {
	private final AdminRepository adminRepository;
	
	public AdminService(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}
	@Transactional
	public Admin registrarAdmin(Admin admin) {
		if (admin.getId() != null) {
			throw new IllegalArgumentException("Admin novo não deve ter ID.");
		}
		//nome
		if (admin.getNome() == null || admin.getNome().isBlank()) {
		    throw new IllegalArgumentException("Informe o nome do admin.");
		}
		//verificador de CPF
		if (admin.getCpf() == null || admin.getCpf().isBlank()) {
			throw new IllegalArgumentException("admin precisa de um CPF.");
		}
		if (adminRepository.existsByCpf(admin.getCpf())) {
			throw new IllegalArgumentException("Já existe um admin com este CPF");
		}
		
		//verificador de email
		if (admin.getEmail() == null || admin.getEmail().isBlank()) {
			throw new IllegalArgumentException("admin precisa de um email.");
		}
		if (adminRepository.existsByEmail(admin.getEmail())) {
			throw new IllegalArgumentException("Já existe um admin com este email");
		}
		//celular 
		if (admin.getCelular() == null || admin.getCelular().isBlank()) {
			throw new IllegalArgumentException("admin precisa de um Celular.");
		}
		return adminRepository.save(admin);
	}
	
	public Admin findById(Long id) {
		return adminRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Admin não encontrado."));
	}
	
	public List<Admin> findAll() {
		return adminRepository.findAll();
	}
}
