package com.mendes.os.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mendes.os.api.model.Comentario;
import com.mendes.os.api.model.ComentarioInput;
import com.mendes.os.api.model.ComentarioModel;
import com.mendes.os.domain.exception.EntidadeNaoEncontradaException;
import com.mendes.os.domain.model.OrdemServico;
import com.mendes.os.domain.repository.OrdemServicoRepository;
import com.mendes.os.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {

	@Autowired
	private GestaoOrdemServicoService ordemService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private OrdemServicoRepository ordemRepository;
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioModel adicionar(@PathVariable Long ordemServicoId, 
								@Valid @RequestBody	ComentarioInput comentarioInput) {
		Comentario comentario = ordemService.adicionarComentario(ordemServicoId, comentarioInput.getDescricao());
		
		return toModel(comentario);
	}


	private ComentarioModel toModel(Comentario comentario) {
		
		return modelMapper.map(comentario, ComentarioModel.class);
	}
	@GetMapping
	private List<ComentarioModel> listar(@PathVariable Long ordemServicoId){
		OrdemServico ordemServico = ordemRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada!"));
		
		return toCollectionModel(ordemServico.getComentarios());
	}


	private List<ComentarioModel> toCollectionModel(List<Comentario> comentarios) {
				return comentarios.stream().map(comentario -> toModel(comentario))
								   .collect(Collectors.toList());
	}
}
