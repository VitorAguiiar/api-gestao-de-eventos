package com.example.evento.services;

import com.example.evento.dtos.EventoDTO;
import com.example.evento.dtos.ParticipantesDTO;
import com.example.evento.entities.Evento;
import com.example.evento.entities.Participantes;
import com.example.evento.repositories.EventoRepository;
import com.example.evento.repositories.ParticipantesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ParticipantesRepository participantesRepository;


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


    public List<EventoDTO> listarEventos() {
        List<Evento> eventos = eventoRepository.findAll();
        return eventos.stream()
                .map(EventoDTO::new)
                .collect(Collectors.toList());
    }


    public EventoDTO buscarEventoPorId(Long id) {
        Optional<Evento> evento = eventoRepository.findById(id);
        return evento.map(EventoDTO::new)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado: " + id));
    }


    public EventoDTO atualizarEvento(Long id, EventoDTO dto) {
        try {
            Evento eventoExistente = eventoRepository.getReferenceById(id);
            eventoExistente.setNome(dto.getNome());
            eventoExistente.setDescricao(dto.getDescricao());
            eventoExistente.setData(dto.getData());
            eventoExistente.setLocal(dto.getLocal());
            eventoExistente.setVagas(dto.getVagas());
            eventoExistente = eventoRepository.save(eventoExistente);
            return new EventoDTO(eventoExistente);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Evento não encontrado: " + id);
        }
    }

    
    public void excluirEvento(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new RuntimeException("Evento não encontrado: " + id);
        }
        eventoRepository.deleteById(id);
    }

    
    public boolean verificarVagasDisponiveis(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado: " + eventoId));

            long totalParticipantes = participantesRepository.countByEvento(evento);
        
            return totalParticipantes < evento.getVagas();
    }

    
    public List<ParticipantesDTO> listarParticipantesPorEvento(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado: " + eventoId));

        List<Participantes> participantes = participantesRepository.findByEvento(evento);
        return participantes.stream()
                .map(ParticipantesDTO::new)
                .collect(Collectors.toList());
    }
}
