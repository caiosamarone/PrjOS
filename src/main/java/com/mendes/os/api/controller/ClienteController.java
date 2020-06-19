package com.mendes.os.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mendes.os.domain.model.Cliente;
import com.mendes.os.domain.repository.ClienteRepository;
import com.mendes.os.domain.service.CadastroClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	CadastroClienteService clienteService;
	
	//LISTA CLIENTES
	@GetMapping
	public List<Cliente> list() {
		return clienteRepository.findAll();
		
	}
	
	//BUSCA CLIENTE POR ID
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		
		if(cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	//ADICIONA CLIENTE
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	//O requestBody transforma o JSON que está vindo do cliente em um objeto java
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return clienteService.salvar(cliente);
		
	}
	
	//ATUALIZA UM CLIENTE
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId, @RequestBody Cliente cliente){
		if(!clienteRepository.existsById(clienteId)) {
			ResponseEntity.notFound().build();
		}
		cliente.setId(clienteId);
		clienteService.salvar(cliente);
		
		return ResponseEntity.ok(cliente);
	}
	
	
	//DELETA UM CLIENTE
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Cliente> deletar(@PathVariable Long clienteId){
		if(!clienteRepository.existsById(clienteId)) {
			ResponseEntity.notFound().build();
		}
		clienteService.excluir(clienteId);
		return ResponseEntity.noContent().build();
	}
	
}
