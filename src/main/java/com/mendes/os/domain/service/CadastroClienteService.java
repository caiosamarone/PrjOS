package com.mendes.os.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendes.os.domain.exception.NegocioException;
import com.mendes.os.domain.model.Cliente;
import com.mendes.os.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService {
	@Autowired
	ClienteRepository clienteRepository;
	
	
	public Cliente salvar(Cliente cliente) {
		Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
		
		if(clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new NegocioException("Ja existe um cliente cadastrado com esse email");
		}
		return clienteRepository.save(cliente);
	}
	
	public void excluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}
	
	
}
