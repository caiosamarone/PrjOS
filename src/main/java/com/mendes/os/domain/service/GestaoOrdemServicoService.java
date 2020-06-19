package com.mendes.os.domain.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendes.os.domain.exception.NegocioException;
import com.mendes.os.domain.model.Cliente;
import com.mendes.os.domain.model.OrdemServico;
import com.mendes.os.domain.model.StatusOrdemServico;
import com.mendes.os.domain.repository.ClienteRepository;
import com.mendes.os.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	OrdemServicoRepository ordemRepository;
	@Autowired
	ClienteRepository clienteRepository;
	
	public OrdemServico criar (OrdemServico os) {
		
		os.setStatus(StatusOrdemServico.ABERTA);
		os.setDataAbertura(OffsetDateTime.now());
		os.setCliente(clienteRepository.findById(os.getCliente().getId()).orElseThrow(()-> new NegocioException("Cliente nao encontrado")));
		return ordemRepository.save(os);
	}
	
	public void excluir(Long idOrdem) {
		ordemRepository.deleteById(idOrdem);
	}
}
