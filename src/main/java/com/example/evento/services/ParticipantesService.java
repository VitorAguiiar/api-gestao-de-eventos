package com.example.evento.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.evento.dtos.ParticipantesDTO;
import com.example.evento.entities.Evento;
import com.example.evento.entities.Participantes;
import com.example.evento.repositories.EventoRepository;
import com.example.evento.repositories.ParticipantesRepository;


@Service
@Transactional
public class ParticipantesService {

    @Autowired
    private ParticipantesRepository participantesRepository;

    @Autowired
    private EventoRepository eventoRepository;

    public ParticipantesDTO cadastrarParticipante(ParticipantesDTO dto) {
        if (participantesRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalStateException("Já existe um participante cadastrado com este e-mail: " + dto.getEmail());
        }

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
                .orElseThrow(() -> new IllegalArgumentException("Participante não encontrado com ID: " + participanteId));

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado com ID: " + eventoId));

       
        if (participante.getEventos().contains(evento)) {
            throw new IllegalStateException("Participante '" + participante.getNome() + "' (ID: " + participante.getId() + ") já está inscrito no evento '" + evento.getNome() + "' (ID: " + evento.getId() + ").");
        }

       
        if (evento.getParticipantes().size() >= evento.getVagas()) {
            throw new IllegalStateException("Limite de vagas atingido para o evento: " + evento.getNome() + " (ID: " + evento.getId() + ").");
        }

       
        participante.getEventos().add(evento);
        evento.getParticipantes().add(participante);

        participantesRepository.save(participante);
        eventoRepository.save(evento);

        return new ParticipantesDTO(participante);
    }

   
    public void cancelarInscricao(Long participanteId, Long eventoId) {

        Participantes participante = participantesRepository.findById(participanteId)
                .orElseThrow(() -> new IllegalArgumentException("Participante não encontrado com ID: " + participanteId));

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado com ID: " + eventoId));

   
        if (!participante.getEventos().contains(evento)) {
            throw new IllegalStateException("Participante '" + participante.getNome() + "' (ID: " + participante.getId() + ") não está inscrito no evento '" + evento.getNome() + "' (ID: " + evento.getId() + ").");
        }

      
        participante.getEventos().remove(evento);
        evento.getParticipantes().remove(participante);

        participantesRepository.save(participante);
        eventoRepository.save(evento);
    }

    
    @Transactional(readOnly = true)
    public ParticipantesDTO buscarParticipantePorId(Long id) {
        return participantesRepository.findById(id)
                .map(ParticipantesDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Participante não encontrado com ID: " + id));
    }

   
    @Transactional(readOnly = true)
    public List<ParticipantesDTO> listarTodosParticipantes() {
        List<Participantes> participantes = participantesRepository.findAll();
        return participantes.stream()
                .map(ParticipantesDTO::new)
                .collect(Collectors.toList());
    }

    
    public ParticipantesDTO atualizarParticipante(Long id, ParticipantesDTO dto) {
        Participantes participanteExistente = participantesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Participante não encontrado com ID: " + id));

        if (!participanteExistente.getEmail().equals(dto.getEmail()) &&
            participantesRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalStateException("Já existe outro participante com este e-mail: " + dto.getEmail());
        }

        participanteExistente.setNome(dto.getNome());
        participanteExistente.setEmail(dto.getEmail());
        participanteExistente.setTelefone(dto.getTelefone());

        participanteExistente = participantesRepository.save(participanteExistente);
        return new ParticipantesDTO(participanteExistente);
    }

    
    public void excluirParticipante(Long id) {
        if (!participantesRepository.existsById(id)) {
            throw new IllegalArgumentException("Participante não encontrado com ID: " + id);
        }
        participantesRepository.deleteById(id);
    }
}