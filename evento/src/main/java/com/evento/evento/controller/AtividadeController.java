package com.evento.evento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evento.evento.DTO.AtividadeDTO;
import com.evento.evento.entidade.Categoria;
import com.evento.evento.repository.CategoriaRepository;
import com.evento.evento.service.AtividadeService;

@RestController
@RequestMapping(value = "/atividade")
public class AtividadeController {

	@Autowired 
    private AtividadeService service;
	
	@GetMapping
    public ResponseEntity<List<AtividadeDTO>> buscar() {
        return ResponseEntity.ok(service.buscarTodos());
	}
       
	@Autowired
    private CategoriaRepository categoriaRepository;

    @PostMapping
    public ResponseEntity<Categoria> createCategoria(@RequestBody Categoria categoria) {
      
        Categoria savedCategoria = categoriaRepository.save(categoria); 

        return ResponseEntity.status(201).body(savedCategoria);
    }
}