package com.example.evento.repositories;

import com.example.evento.entities.Evento;
import com.example.evento.entities.Participantes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantesRepository extends JpaRepository<Participantes, Long> {

    
    long countByEvento(Evento evento);

    
    List<Participantes> findByEvento(Evento evento);
}
