package com.example.Lista11;

public class Municipio {
	
	private String nome;
	private String estado;
	private int populacao;
	
	public Municipio() {
		
	}
	
	public Municipio(String nome, String estado, int populacao) {
		
		this.setNome(nome);
		this.setEstado(estado);
		this.setPopulacao(populacao);
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		if(nome==null) {
			throw new IllegalArgumentException("Erro ao inserir nome da cidade");
		}
		this.nome = nome;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		if(estado==null) {
			throw new IllegalArgumentException("Erro ao inserir nome da estado");
		}
		this.estado = estado;
	}
	public int getPopulacao() {
		return populacao;
	}
	public void setPopulacao(int populacao) {
		this.populacao = populacao;
	}
	
	
	
}
