package com.example.evento.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.evento.dtos.ParticipantesDTO;
import com.example.evento.entities.Evento;
import com.example.evento.entities.Participantes;
import com.example.evento.repositories.EventoRepository;
import com.example.evento.repositories.ParticipantesRepository;

@Service
public class ParticipantesService {

    @Autowired
    private ParticipantesRepository participantesRepository;

    @Autowired
    private EventoRepository eventoRepository;


    public ParticipantesDTO cadastrarParticipante(ParticipantesDTO dto) {
        
        Participantes novoParticipante = new Participantes(
                null,  
                dto.getNome(),
                dto.getEmail(),
                dto.getTelefone()
        );
        

        novoParticipante = participantesRepository.save(novoParticipante);
        

        return new ParticipantesDTO(novoParticipante);
    }


    public ParticipantesDTO inscreverParticipante(Long participanteId, Long eventoId) {
       
        Participantes participante = participantesRepository.findById(participanteId)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado com ID: " + participanteId));

       
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com ID: " + eventoId));

       
        long totalParticipantes = participantesRepository.countByEvento(evento); 
        if (totalParticipantes >= evento.getVagas()) {
            throw new RuntimeException("Limite de vagas atingido para este evento");
        }

    
        participante.getEventos().add(evento); 
        participantesRepository.save(participante); 

        return new ParticipantesDTO(participante);
    }

    
    public void cancelarInscricao(Long participanteId, Long eventoId) {
        
        Participantes participante = participantesRepository.findById(participanteId)
                .orElseThrow(() -> new RuntimeException("Participante não encontrado com ID: " + participanteId));

    
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com ID: " + eventoId));

            participante.getEventos().remove(evento);
        participantesRepository.save(participante); 
    }

    
    public List<ParticipantesDTO> listarParticipantesPorEvento(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com ID: " + eventoId));

        
        List<Participantes> participantes = (List<Participantes>) evento.getParticipantes();
        return participantes.stream()
                .map(ParticipantesDTO::new)
                .collect(Collectors.toList());
    }
}
