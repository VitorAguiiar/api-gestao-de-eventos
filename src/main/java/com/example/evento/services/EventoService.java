package com.example.evento.services;

import com.example.evento.dtos.EventoDTO;
import com.example.evento.dtos.ParticipantesDTO;
import com.example.evento.entities.Evento;
import com.example.evento.repositories.EventoRepository;
import com.example.evento.repositories.ParticipantesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ParticipantesRepository participantesRepository;

    /**
     * Cria um novo evento.
     * @param dto Os dados do evento a ser criado.
     * @return O DTO do evento criado.
     */
    public EventoDTO criarEvento(EventoDTO dto) {
        Evento novoEvento = new Evento(
                null,
                dto.getNome(),
                dto.getDescricao(),
                dto.getData(),
                dto.getLocal(),
                dto.getVagas()
        );
        novoEvento = eventoRepository.save(novoEvento);
        return new EventoDTO(novoEvento);
    }

    /**
     * Lista todos os eventos disponíveis.
     * @return Uma lista de EventoDTOs.
     */
    @Transactional(readOnly = true)
    public List<EventoDTO> listarEventos() {
        List<Evento> eventos = eventoRepository.findAll();
        return eventos.stream()
                .map(EventoDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca um evento por ID.
     * @param id O ID do evento.
     * @return O EventoDTO correspondente.
     * @throws IllegalArgumentException se o evento não for encontrado.
     */
    @Transactional(readOnly = true)
    public EventoDTO buscarEventoPorId(Long id) {
        Optional<Evento> evento = eventoRepository.findById(id);
        return evento.map(EventoDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado com ID: " + id));
    }

    /**
     * Atualiza os dados de um evento existente.
     * @param id O ID do evento a ser atualizado.
     * @param dto Os novos dados do evento.
     * @return O EventoDTO atualizado.
     * @throws IllegalArgumentException se o evento não for encontrado.
     */
    public EventoDTO atualizarEvento(Long id, EventoDTO dto) {
        Evento eventoExistente = eventoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado com ID: " + id));

        eventoExistente.setNome(dto.getNome());
        eventoExistente.setDescricao(dto.getDescricao());
        eventoExistente.setData(dto.getData());
        eventoExistente.setLocal(dto.getLocal());
        eventoExistente.setVagas(dto.getVagas());

        eventoExistente = eventoRepository.save(eventoExistente);
        return new EventoDTO(eventoExistente);
    }

    /**
     * Exclui um evento pelo ID.
     * @param id O ID do evento a ser excluído.
     * @throws IllegalArgumentException se o evento não for encontrado.
     */
    public void excluirEvento(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new IllegalArgumentException("Evento não encontrado com ID: " + id);
        }
        eventoRepository.deleteById(id);
    }

    /**
     * Lista todos os participantes inscritos em um evento específico.
     * @param eventoId O ID do evento.
     * @return Uma lista de ParticipantesDTOs.
     * @throws IllegalArgumentException se o evento não for encontrado.
     */
    @Transactional(readOnly = true)
    public List<ParticipantesDTO> listarParticipantesPorEvento(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado com ID: " + eventoId));

        // Converte o Set<Participantes> para List<ParticipantesDTO>
        return evento.getParticipantes().stream()
                .map(ParticipantesDTO::new)
                .collect(Collectors.toList());
    }
}