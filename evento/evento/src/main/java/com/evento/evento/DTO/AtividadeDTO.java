package com.evento.evento.DTO;

import com.evento.evento.entidade.Atividade;

public class AtividadeDTO {

	private Integer id;
	private String nome;
	private String descricao;
	private Double preco;
	
	
	
	public AtividadeDTO() {
	
	}



	public AtividadeDTO(Integer id, String nome, String descricao, Double preco) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
	}

	public AtividadeDTO(Atividade p) {
		id = p.getId();
		nome = p.getNome();
		descricao = p.getDescricao();
		preco = p.getPreco();
	}

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
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



	public Double getPreco() {
		return preco;
	}



	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	
	
	
}
