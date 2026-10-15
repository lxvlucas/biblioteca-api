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

import com.lucas.biblioteca.dto.AdminRequest;
import com.lucas.biblioteca.dto.AdminResponse;
import com.lucas.biblioteca.entities.Admin;
import com.lucas.biblioteca.services.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admins")
public class AdminController {
	private final AdminService adminService;
	
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AdminResponse registrarAdmin(@Valid @RequestBody AdminRequest adminRequest) {
		Admin admin = new Admin(adminRequest.nome(), adminRequest.cpf(), adminRequest.email(), adminRequest.celular());
		Admin adminSalvo = adminService.registrarAdmin(admin);
		return new AdminResponse(adminSalvo.getId(), adminSalvo.getCpf(), adminSalvo.getNome(), adminSalvo.getEmail(), adminSalvo.getCelular());
	}
	
	@GetMapping("/{id}")
	public AdminResponse findById(@PathVariable Long id) {
		Admin admin = adminService.findById(id);
		return new AdminResponse(admin.getId(), admin.getCpf(), admin.getNome(), admin.getEmail(), admin.getCelular());
	}
	
	@GetMapping
	public List<AdminResponse> findAll() {
		List<AdminResponse> adminsResponse = new ArrayList<>();
		List<Admin> admins = adminService.findAll();
		for ( Admin a : admins ) {
			adminsResponse.add(new AdminResponse(a.getId(), a.getCpf(), a.getNome(), a.getEmail(),a.getCelular()));
		}
		return adminsResponse;
	}
}