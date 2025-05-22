package com.example.evento.dtos;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.evento.entities.Evento;
import com.example.evento.entities.Participantes;

public class EventoDTO {

	private Long id;
	private String nome;
	private String descricao;
	private Instant data;
	private String local;
	private int vagas;

	private List<ParticipantesDTO> participantes = new ArrayList<>();

	public EventoDTO() {
	}

	public EventoDTO(Evento entity) {
		id = entity.getId();
		nome = entity.getNome();
		descricao = entity.getDescricao();
		data = entity.getData();
		local = entity.getLocal();
		vagas = entity.getVagas();

		if (entity.getParticipantes() != null) {
			for (Participantes participante : entity.getParticipantes()) {

				this.participantes.add(new ParticipantesDTO(participante.getId(), participante.getNome(),
						participante.getEmail(), participante.getTelefone()));
			}
		}
	}


	public EventoDTO(Long id, String nome, String descricao, Instant data, String local, int vagas) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.data = data;
		this.local = local;
		this.vagas = vagas;
	}


	public EventoDTO(Long id, String nome, Instant data, String local) {
		this.id = id;
		this.nome = nome;
		this.data = data;
		this.local = local;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Instant getData() {
		return data;
	}

	public void setData(Instant data) {
		this.data = data;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public int getVagas() {
		return vagas;
	}

	public void setVagas(int vagas) {
		this.vagas = vagas;
	}


	public List<ParticipantesDTO> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<ParticipantesDTO> participantes) {
		this.participantes = participantes;
	}
}