package com.mendes.os.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mendes.os.api.model.Comentario;
import com.mendes.os.domain.exception.EntidadeNaoEncontradaException;
import com.mendes.os.domain.exception.NegocioException;
import com.mendes.os.domain.model.OrdemServico;
import com.mendes.os.domain.model.StatusOrdemServico;
import com.mendes.os.domain.repository.ClienteRepository;
import com.mendes.os.domain.repository.ComentarioRepository;
import com.mendes.os.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	OrdemServicoRepository ordemRepository;
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	ComentarioRepository comentarioRepository;
	
	public OrdemServico criar (OrdemServico os) {
		
		os.setStatus(StatusOrdemServico.ABERTA);
		os.setDataAbertura(OffsetDateTime.now());
		os.setCliente(clienteRepository.findById(os.getCliente().getId()).orElseThrow(()-> new NegocioException("Cliente nao encontrado")));
		return ordemRepository.save(os);
	}
	
	public void excluir(Long idOrdem) {
		ordemRepository.deleteById(idOrdem);
	}
	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemRepository.findById(ordemServicoId).orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço nao encontrada"))); 
		return comentarioRepository.save(comentario);
	}
	
	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
//		if(ordemServico.getStatus() == StatusOrdemServico.ABERTA) {
//			ordemServico.setDataFinalizacao();
//			ordemServico.setStatus(status);
		ordemServico.finalizar();
		
		ordemRepository.save(ordemServico);
		
	}
	
	private OrdemServico buscar(Long ordemServicoId) {
		return ordemRepository.findById(ordemServicoId).orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço nao encontrada"));
	}
}
