package com.lucas.biblioteca.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lucas.biblioteca.entities.Emprestimo;
import com.lucas.biblioteca.entities.Exemplar;
import com.lucas.biblioteca.entities.Leitor;
import com.lucas.biblioteca.exceptions.ConflitoNegocioException;
import com.lucas.biblioteca.exceptions.RecursoNaoEncontradoException;
import com.lucas.biblioteca.repositories.EmprestimoRepository;
import com.lucas.biblioteca.repositories.ExemplarRepository;
import com.lucas.biblioteca.repositories.LeitorRepository;

@ExtendWith(MockitoExtension.class)
public class EmprestimoServiceTest {
	@Mock
	private EmprestimoRepository emprestimoRepository;
	@Mock
	private LeitorRepository leitorRepository;
	@Mock
	private ExemplarRepository exemplarRepository;
	
	private EmprestimoService emprestimoService;
	
	@BeforeEach
	void prepararService() {
		emprestimoService = new EmprestimoService(emprestimoRepository, leitorRepository, exemplarRepository);
	}
	
	@Test
	void rejeitarSegundaDevolucaoSemAlterarDataOriginal() {
		//preparar
		Leitor leitor = mock(Leitor.class);
		Exemplar exemplar = mock(Exemplar.class);
		when(leitor.getId()).thenReturn(1L);
		when(exemplar.getId()).thenReturn(2L);
		
		Emprestimo emprestimo = new Emprestimo(leitor, exemplar);
		LocalDate dataOriginal = LocalDate.of(2026, 10, 1);
		emprestimo.setDataDevolucao(dataOriginal);
		
		when(emprestimoRepository.findById(10L)).thenReturn(Optional.of(emprestimo));
		
		//executar e verificar a excecao
		assertThrows(ConflitoNegocioException.class,() -> emprestimoService.resgistrarDevolucao(10L));
		
		//verificar que a data se manteve e nao houve tentativa de salvar
		assertEquals(dataOriginal, emprestimo.getDataDevolucao());
		verify(emprestimoRepository, never()).save(any(Emprestimo.class));
	}
	
	@Test
	void emprestimoInexistente() {
		when(emprestimoRepository.findById(1L)).thenReturn(Optional.empty());
		
		assertThrows(RecursoNaoEncontradoException.class,() -> emprestimoService.resgistrarDevolucao(1L));
		verify(emprestimoRepository, never()).save(any(Emprestimo.class));
	}
	
	@Test
	void primeiraDevolucaoValidaSegundaInvalida() {
		Leitor leitor = mock(Leitor.class);
		Exemplar exemplar = mock(Exemplar.class);
		when(leitor.getId()).thenReturn(1L);
		when(exemplar.getId()).thenReturn(2L);
		
		Emprestimo emprestimo = new Emprestimo(leitor, exemplar);
		LocalDate dataEmprestimo = LocalDate.of(2026, 10, 1);
		LocalDate prazo = dataEmprestimo.plusDays(14);
		emprestimo.setDataEmprestimo(dataEmprestimo);
		emprestimo.setDataDevolucaoPrevista(prazo);
		when(emprestimoRepository.findById(10L)).thenReturn(Optional.of(emprestimo));
		when(emprestimoRepository.save(emprestimo)).thenReturn(emprestimo);
		
		Emprestimo resultado = emprestimoService.resgistrarDevolucao(10L);
		
		
		assertNotNull(resultado.getDataDevolucao());
		assertEquals(dataEmprestimo, resultado.getDataEmprestimo());
		assertEquals(prazo, resultado.getDataDevolucaoPrevista());
		
		verify(emprestimoRepository).save(emprestimo);
	}
	
	@Test
	void exemplarOcupado() {
		Leitor leitor = mock(Leitor.class);
		Exemplar exemplar = mock(Exemplar.class);
		when(leitor.getId()).thenReturn(1L);
		when(leitorRepository.existsById(1L)).thenReturn(true);
		when(leitorRepository.findById(1L)).thenReturn(Optional.of(leitor));
		when(exemplar.getId()).thenReturn(2L);
		when(exemplarRepository.existsById(2L)).thenReturn(true);
		when(exemplarRepository.findById(2L)).thenReturn(Optional.of(exemplar));
		Emprestimo emprestimo = new Emprestimo(leitor, exemplar);
		when(emprestimoRepository.existsByExemplar_IdAndDataDevolucaoIsNull(2L)).thenReturn(true);
		when(emprestimoRepository.countByLeitor_IdAndDataDevolucaoIsNull(1L)).thenReturn(2L);
		
		assertThrows(ConflitoNegocioException.class,() -> emprestimoService.registrarEmprestimo(emprestimo));
		verify(emprestimoRepository, never()).save(any(Emprestimo.class ));
	}
	
	@Test
	void quartoEmprestimo() {
		Leitor leitor = mock(Leitor.class);
		when(leitor.getId()).thenReturn(1L);
		when(leitorRepository.existsById(1L)).thenReturn(true);
		when(leitorRepository.findById(1L)).thenReturn(Optional.of(leitor));
		
		Exemplar exemplar = mock(Exemplar.class);
		when(exemplar.getId()).thenReturn(2L);
		
		Emprestimo emprestimo = new Emprestimo(leitor, exemplar);
		when(emprestimoRepository.countByLeitor_IdAndDataDevolucaoIsNull(1L)).thenReturn(3L);
		
		assertThrows(ConflitoNegocioException.class,() -> emprestimoService.registrarEmprestimo(emprestimo));
		verify(emprestimoRepository, never()).save(any(Emprestimo.class));
	}
	
	@Test
	void cadastroEmprestimoValidoELimiteDe3() {
		Leitor leitor = mock(Leitor.class);
		when(leitor.getId()).thenReturn(1L);
		when(leitorRepository.existsById(1L)).thenReturn(true);
		when(leitorRepository.findById(1L)).thenReturn(Optional.of(leitor));
		
		Exemplar exemplar = mock(Exemplar.class);
		when(exemplar.getId()).thenReturn(2L);
		when(exemplarRepository.existsById(2L)).thenReturn(true);
		when(exemplarRepository.findById(2L)).thenReturn(Optional.of(exemplar));
		
		Emprestimo emprestimo = new Emprestimo(leitor, exemplar);
		when(emprestimoRepository.existsByExemplar_IdAndDataDevolucaoIsNull(2L)).thenReturn(false);
		when(emprestimoRepository.save(emprestimo)).thenReturn(emprestimo);
		when(emprestimoRepository.countByLeitor_IdAndDataDevolucaoIsNull(1L)).thenReturn(2L);
		
		Emprestimo resultado = emprestimoService.registrarEmprestimo(emprestimo);
		verify(emprestimoRepository).save(resultado);
	}
}