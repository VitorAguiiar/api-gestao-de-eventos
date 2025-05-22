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

    /**
     * POST /eventos
     * Cria um novo evento.
     * @param dto Os dados do evento a ser criado.
     * @return ResponseEntity com o EventoDTO do evento criado e status 201 Created.
     */
    @PostMapping
    public ResponseEntity<EventoDTO> criarEvento(@RequestBody EventoDTO dto) {
        EventoDTO novoEvento = eventoService.criarEvento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEvento);
    }

    /**
     * GET /eventos
     * Lista todos os eventos.
     * @return ResponseEntity com uma lista de EventoDTOs e status 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<EventoDTO>> listarEventos() {
        List<EventoDTO> eventos = eventoService.listarEventos();
        return ResponseEntity.ok(eventos);
    }

    /**
     * PUT /eventos/{id}
     * Atualiza os dados de um evento existente.
     * @param id O ID do evento a ser atualizado.
     * @param dto Os novos dados do evento.
     * @return ResponseEntity com o EventoDTO atualizado e status 200 OK.
     * Lança IllegalArgumentException se o evento não for encontrado.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<EventoDTO> atualizarEvento(@PathVariable Long id, @RequestBody EventoDTO dto) {
        EventoDTO eventoAtualizado = eventoService.atualizarEvento(id, dto);
        return ResponseEntity.ok(eventoAtualizado);
    }

    /**
     * DELETE /eventos/{id}
     * Remove um evento.
     * @param id O ID do evento a ser removido.
     * @return ResponseEntity com status 204 No Content.
     * Lança IllegalArgumentException se o evento não for encontrado.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluirEvento(@PathVariable Long id) {
        eventoService.excluirEvento(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /eventos/{eventoId}/participantes
     * Retorna todos os participantes de um evento específico.
     * @param eventoId O ID do evento.
     * @return ResponseEntity com uma lista de ParticipantesDTOs e status 200 OK.
     * Lança IllegalArgumentException se o evento não for encontrado.
     */
    @GetMapping(value = "/{eventoId}/participantes")
    public ResponseEntity<List<ParticipantesDTO>> verParticipantesDoEvento(@PathVariable Long eventoId) {
        List<ParticipantesDTO> participantes = eventoService.listarParticipantesPorEvento(eventoId);
        return ResponseEntity.ok(participantes);
    }

    // Nota: As rotas de inscrição e cancelamento foram movidas para ParticipantesController,
    // pois a lógica principal reside na manipulação do participante e sua relação com o evento.
}