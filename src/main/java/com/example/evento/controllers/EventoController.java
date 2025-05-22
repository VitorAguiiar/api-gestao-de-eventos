package com.example.evento.controllers;

import com.example.evento.dtos.EventoDTO;
import com.example.evento.dtos.ParticipantesDTO; // Para a rota de listar participantes
import com.example.evento.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

   
    @PostMapping
    public ResponseEntity<EventoDTO> criarEvento(@RequestBody EventoDTO dto) {
        EventoDTO novoEvento = eventoService.criarEvento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEvento);
    }


    @GetMapping
    public ResponseEntity<List<EventoDTO>> listarEventos() {
        List<EventoDTO> eventos = eventoService.listarEventos();
        return ResponseEntity.ok(eventos);
    }

    
    @PutMapping(value = "/{id}")
    public ResponseEntity<EventoDTO> atualizarEvento(@PathVariable Long id, @RequestBody EventoDTO dto) {
        EventoDTO eventoAtualizado = eventoService.atualizarEvento(id, dto);
        return ResponseEntity.ok(eventoAtualizado);
    }

    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluirEvento(@PathVariable Long id) {
        eventoService.excluirEvento(id);
        return ResponseEntity.noContent().build();
    }

    
    @GetMapping(value = "/{eventoId}/participantes")
    public ResponseEntity<List<ParticipantesDTO>> verParticipantesDoEvento(@PathVariable Long eventoId) {
        List<ParticipantesDTO> participantes = eventoService.listarParticipantesPorEvento(eventoId);
        return ResponseEntity.ok(participantes);
    }

}