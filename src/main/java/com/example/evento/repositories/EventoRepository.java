package com.example.evento.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.evento.entities.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {

}
