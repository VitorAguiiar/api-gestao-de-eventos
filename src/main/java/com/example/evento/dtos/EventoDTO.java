package com.example.evento.dtos;

import java.time.Instant;

import com.example.evento.entities.Evento;

public class EventoDTO {

	private Long id;
	private String nome;
	private String descricao;
	private Instant data;
	private String local;
	private int vagas;
	
	
	public EventoDTO() {
	}


	public EventoDTO(Evento entity) {
		id = entity.getId();
		nome = entity.getNome();
		descricao = entity.getDescricao();
		data = entity.getData();
		local = entity.getLocal();
		vagas = entity.getVagas();
	}


	public EventoDTO(Long id, String nome, String descricao, Instant data, String local, int vagas) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.data = data;
		this.local = local;
		this.vagas = vagas;
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
	
	
	
}
