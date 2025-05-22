package com.example.evento.repositories;

import com.example.evento.entities.Evento;
import com.example.evento.entities.Participantes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; 

@Repository
public interface ParticipantesRepository extends JpaRepository<Participantes, Long> {

    
    Optional<Participantes> findByEmail(String email);

   
    List<Participantes> findByEventosContaining(Evento evento);

    
}