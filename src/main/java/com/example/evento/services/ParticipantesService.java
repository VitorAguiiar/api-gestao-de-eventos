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


    /**
     * Cadastra um novo participante.
     * @param dto Os dados do participante a ser cadastrado.
     * @return O DTO do participante cadastrado.
     * @throws IllegalStateException se o e-mail já estiver em uso.
     */
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

    /**
     * Realiza a inscrição de um participante em um evento.
     * Inclui validações para vagas e duplicidade de inscrição.
     * @param participanteId O ID do participante.
     * @param eventoId O ID do evento.
     * @return O DTO do participante atualizado com a nova inscrição.
     * @throws IllegalArgumentException se o participante ou evento não forem encontrados.
     * @throws IllegalStateException se o participante já estiver inscrito no evento ou não houver vagas.
     */
    public ParticipantesDTO inscreverParticipante(Long participanteId, Long eventoId) {

        Participantes participante = participantesRepository.findById(participanteId)
                .orElseThrow(() -> new IllegalArgumentException("Participante não encontrado com ID: " + participanteId));

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado com ID: " + eventoId));

        // 1. Validação: Participante já inscrito no evento
        if (participante.getEventos().contains(evento)) {
            throw new IllegalStateException("Participante '" + participante.getNome() + "' (ID: " + participante.getId() + ") já está inscrito no evento '" + evento.getNome() + "' (ID: " + evento.getId() + ").");
        }

        // 2. Validação: Vagas Disponíveis
        if (evento.getParticipantes().size() >= evento.getVagas()) {
            throw new IllegalStateException("Limite de vagas atingido para o evento: " + evento.getNome() + " (ID: " + evento.getId() + ").");
        }

        // 3. Realizar Inscrição: Adicionar o evento à coleção do participante
        participante.getEventos().add(evento);
        evento.getParticipantes().add(participante); // Manter a consistência bidirecional

        participantesRepository.save(participante);
        eventoRepository.save(evento);

        return new ParticipantesDTO(participante);
    }

    /**
     * Cancela a inscrição de um participante em um evento.
     * @param participanteId O ID do participante.
     * @param eventoId O ID do evento.
     * @throws IllegalArgumentException se o participante ou evento não forem encontrados.
     * @throws IllegalStateException se o participante não estiver inscrito no evento.
     */
    public void cancelarInscricao(Long participanteId, Long eventoId) {

        Participantes participante = participantesRepository.findById(participanteId)
                .orElseThrow(() -> new IllegalArgumentException("Participante não encontrado com ID: " + participanteId));

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado com ID: " + eventoId));

        // Validação: Verificar se o participante está realmente inscrito no evento
        if (!participante.getEventos().contains(evento)) {
            throw new IllegalStateException("Participante '" + participante.getNome() + "' (ID: " + participante.getId() + ") não está inscrito no evento '" + evento.getNome() + "' (ID: " + evento.getId() + ").");
        }

        // Remover a inscrição de ambos os lados para manter a consistência
        participante.getEventos().remove(evento);
        evento.getParticipantes().remove(participante);

        participantesRepository.save(participante);
        eventoRepository.save(evento);
    }

    /**
     * Busca um participante por ID.
     * @param id O ID do participante.
     * @return O ParticipantesDTO correspondente.
     * @throws IllegalArgumentException se o participante não for encontrado.
     */
    @Transactional(readOnly = true)
    public ParticipantesDTO buscarParticipantePorId(Long id) {
        return participantesRepository.findById(id)
                .map(ParticipantesDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Participante não encontrado com ID: " + id));
    }

    /**
     * Lista todos os participantes cadastrados.
     * @return Uma lista de ParticipantesDTOs.
     */
    @Transactional(readOnly = true)
    public List<ParticipantesDTO> listarTodosParticipantes() {
        List<Participantes> participantes = participantesRepository.findAll();
        return participantes.stream()
                .map(ParticipantesDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza os dados de um participante existente.
     * @param id O ID do participante a ser atualizado.
     * @param dto Os novos dados do participante.
     * @return O ParticipantesDTO atualizado.
     * @throws IllegalArgumentException se o participante não for encontrado.
     * @throws IllegalStateException se o e-mail já estiver em uso por outro participante.
     */
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

    /**
     * Exclui um participante pelo ID.
     * @param id O ID do participante a ser excluído.
     * @throws IllegalArgumentException se o participante não for encontrado.
     */
    public void excluirParticipante(Long id) {
        if (!participantesRepository.existsById(id)) {
            throw new IllegalArgumentException("Participante não encontrado com ID: " + id);
        }
        participantesRepository.deleteById(id);
    }
}