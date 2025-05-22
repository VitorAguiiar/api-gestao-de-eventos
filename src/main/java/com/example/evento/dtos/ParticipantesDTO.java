package com.example.evento.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Set; 

import com.example.evento.entities.Evento; 
import com.example.evento.entities.Participantes;

public class ParticipantesDTO {

	private Long id;
	private String nome;
	private String email;
	private String telefone;

	private List<EventoDTO> eventos = new ArrayList<>();

	public ParticipantesDTO() {
	}

	public ParticipantesDTO(Long id, String nome, String email, String telefone) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
	}

	public ParticipantesDTO(Participantes entity) {
		id = entity.getId();
		nome = entity.getNome();
		email = entity.getEmail();
		telefone = entity.getTelefone();

		if (entity.getEventos() != null) {
			for (Evento evento : entity.getEventos()) {

				this.eventos.add(new EventoDTO(evento.getId(), evento.getNome(), evento.getData(), evento.getLocal()));
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public List<EventoDTO> getEventos() {
		return eventos;
	}

	public void setEventos(List<EventoDTO> eventos) {
		this.eventos = eventos;
	}
}