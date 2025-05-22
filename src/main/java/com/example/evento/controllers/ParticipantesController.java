package com.example.evento.controllers;

import com.example.evento.dtos.ParticipantesDTO;
import com.example.evento.services.ParticipantesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/participantes")
public class ParticipantesController {

    @Autowired
    private ParticipantesService participantesService;

   
    @PostMapping
    public ResponseEntity<ParticipantesDTO> cadastrarParticipante(@RequestBody ParticipantesDTO dto) {
        ParticipantesDTO novoParticipante = participantesService.cadastrarParticipante(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoParticipante);
    }

  
    @GetMapping
    public ResponseEntity<List<ParticipantesDTO>> listarTodosParticipantes() {
        List<ParticipantesDTO> participantes = participantesService.listarTodosParticipantes();
        return ResponseEntity.ok(participantes);
    }

  
    @GetMapping(value = "/{id}")
    public ResponseEntity<ParticipantesDTO> buscarParticipantePorId(@PathVariable Long id) {
        ParticipantesDTO participante = participantesService.buscarParticipantePorId(id);
        return ResponseEntity.ok(participante);
    }

    
    @PutMapping(value = "/{id}")
    public ResponseEntity<ParticipantesDTO> atualizarParticipante(@PathVariable Long id, @RequestBody ParticipantesDTO dto) {
        ParticipantesDTO participanteAtualizado = participantesService.atualizarParticipante(id, dto);
        return ResponseEntity.ok(participanteAtualizado);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluirParticipante(@PathVariable Long id) {
        participantesService.excluirParticipante(id);
        return ResponseEntity.noContent().build();
    }

 
    @PostMapping(value = "/eventos/{eventoId}/inscrever/{participanteId}") 
    public ResponseEntity<ParticipantesDTO> inscreverParticipanteEmEvento(
            @PathVariable Long eventoId,
            @PathVariable Long participanteId) {
        ParticipantesDTO participanteAtualizado = participantesService.inscreverParticipante(participanteId, eventoId);
        return ResponseEntity.ok(participanteAtualizado);
    }

    
    @PostMapping(value = "/eventos/{eventoId}/cancelar/{participanteId}")
    public ResponseEntity<Void> cancelarInscricao(
            @PathVariable Long eventoId,
            @PathVariable Long participanteId) {
        participantesService.cancelarInscricao(participanteId, eventoId);
        return ResponseEntity.noContent().build();
    }
}