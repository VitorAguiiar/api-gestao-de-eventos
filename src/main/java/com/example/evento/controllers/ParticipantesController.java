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

    /**
     * POST /participantes
     * Cadastra um novo participante.
     * @param dto Os dados do participante a ser cadastrado.
     * @return ResponseEntity com o ParticipantesDTO do participante cadastrado e status 201 Created.
     * Lança IllegalStateException se o e-mail já estiver em uso.
     */
    @PostMapping
    public ResponseEntity<ParticipantesDTO> cadastrarParticipante(@RequestBody ParticipantesDTO dto) {
        ParticipantesDTO novoParticipante = participantesService.cadastrarParticipante(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoParticipante);
    }

    /**
     * GET /participantes
     * Lista todos os participantes.
     * @return ResponseEntity com uma lista de ParticipantesDTOs e status 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<ParticipantesDTO>> listarTodosParticipantes() {
        List<ParticipantesDTO> participantes = participantesService.listarTodosParticipantes();
        return ResponseEntity.ok(participantes);
    }

    /**
     * GET /participantes/{id}
     * Busca um participante por ID.
     * @param id O ID do participante.
     * @return ResponseEntity com o ParticipantesDTO e status 200 OK.
     * Lança IllegalArgumentException se o participante não for encontrado.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ParticipantesDTO> buscarParticipantePorId(@PathVariable Long id) {
        ParticipantesDTO participante = participantesService.buscarParticipantePorId(id);
        return ResponseEntity.ok(participante);
    }

    /**
     * PUT /participantes/{id}
     * Atualiza os dados de um participante existente.
     * @param id O ID do participante a ser atualizado.
     * @param dto Os novos dados do participante.
     * @return ResponseEntity com o ParticipantesDTO atualizado e status 200 OK.
     * Lança IllegalArgumentException se o participante não for encontrado.
     * Lança IllegalStateException se o e-mail já estiver em uso por outro participante.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<ParticipantesDTO> atualizarParticipante(@PathVariable Long id, @RequestBody ParticipantesDTO dto) {
        ParticipantesDTO participanteAtualizado = participantesService.atualizarParticipante(id, dto);
        return ResponseEntity.ok(participanteAtualizado);
    }

    /**
     * DELETE /participantes/{id}
     * Exclui um participante.
     * @param id O ID do participante a ser excluído.
     * @return ResponseEntity com status 204 No Content.
     * Lança IllegalArgumentException se o participante não for encontrado.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluirParticipante(@PathVariable Long id) {
        participantesService.excluirParticipante(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * POST /eventos/{eventoId}/inscrever/{participanteId}
     * Rota para inscrever um participante em um evento.
     * Nota: Embora a rota comece com /eventos, a operação principal é a inscrição de um participante.
     * @param eventoId O ID do evento.
     * @param participanteId O ID do participante.
     * @return ResponseEntity com o ParticipantesDTO atualizado e status 200 OK.
     * Lança IllegalArgumentException se o evento ou participante não forem encontrados.
     * Lança IllegalStateException se já inscrito ou vagas esgotadas.
     */
    @PostMapping(value = "/eventos/{eventoId}/inscrever/{participanteId}") // Rota customizada
    public ResponseEntity<ParticipantesDTO> inscreverParticipanteEmEvento(
            @PathVariable Long eventoId,
            @PathVariable Long participanteId) {
        ParticipantesDTO participanteAtualizado = participantesService.inscreverParticipante(participanteId, eventoId);
        return ResponseEntity.ok(participanteAtualizado);
    }

    /**
     * POST /eventos/{eventoId}/cancelar/{participanteId}
     * Rota para cancelar a inscrição de um participante em um evento.
     * @param eventoId O ID do evento.
     * @param participanteId O ID do participante.
     * @return ResponseEntity com status 204 No Content.
     * Lança IllegalArgumentException se o evento ou participante não forem encontrados.
     * Lança IllegalStateException se não estiver inscrito.
     */
    @PostMapping(value = "/eventos/{eventoId}/cancelar/{participanteId}") // Rota customizada
    public ResponseEntity<Void> cancelarInscricao(
            @PathVariable Long eventoId,
            @PathVariable Long participanteId) {
        participantesService.cancelarInscricao(participanteId, eventoId);
        return ResponseEntity.noContent().build();
    }
}