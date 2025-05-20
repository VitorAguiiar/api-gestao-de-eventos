package com.evento.evento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evento.evento.DTO.AtividadeDTO;
import com.evento.evento.entidade.Atividade;
import com.evento.evento.repository.AtividadeRepository;

@Service
public class AtividadeService {

	@Autowired
	private AtividadeRepository repo;
	
	
	public List<AtividadeDTO> buscarTodos(){
		List<Atividade> list = repo.findAll();
		return list.stream().map(AtividadeDTO::new).toList();
	}

    

}
