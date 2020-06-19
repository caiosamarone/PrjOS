package com.mendes.os.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mendes.os.domain.model.OrdemServico;
import com.mendes.os.domain.repository.OrdemServicoRepository;
import com.mendes.os.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

	@Autowired
	GestaoOrdemServicoService ordemService;
	
	@Autowired
	OrdemServicoRepository ordemRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServico adicionar(@Valid @RequestBody OrdemServico ordemServico) {
		return ordemService.criar(ordemServico);
	}
	
	@GetMapping
	public List<OrdemServico> listar(){
		return ordemRepository.findAll();
	}
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<OrdemServico> buscar(@PathVariable Long ordemServicoId){
		Optional<OrdemServico> ordemServico = ordemRepository.findById(ordemServicoId);
		
		if(ordemServico.isPresent()) {
			return ResponseEntity.ok(ordemServico.get());
		}
		return ResponseEntity.notFound().build();		
				
	}
}
