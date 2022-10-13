package com.projeto.oficina.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "servico")
public class Servico {
	
	 	@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private long id; 
	 	
	 	//FK
	 	@Column(name = "codOrdem")
		private long codOrdem; //não é necessario notnull, pois estara atrelado ao objeto OrdemServico abaixo, que deve existir
		
		@ManyToOne
		@JoinColumn(name = "codOrdem", insertable = false, updatable = false) 
		private OrdemServico ordemServico;
		
		private float valorPecas;
		private float valorServico;
		
		private String descricao;

		public long getCodOrdem() {
			return codOrdem;
		}

		public void setCodOrdem(long codOrdem) {
			this.codOrdem = codOrdem;
		}

		public OrdemServico getOrdemServico() {
			return ordemServico;
		}

		public void setOrdemServico(OrdemServico ordemServico) {
			this.ordemServico = ordemServico;
		}

		public float getValorPecas() {
			return valorPecas;
		}

		public void setValorPecas(float valorPecas) {
			this.valorPecas = valorPecas;
		}

		public float getValorServico() {
			return valorServico;
		}

		public void setValorServico(float valorServico) {
			this.valorServico = valorServico;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public long getId() {
			return id;
		}
		public Servico() {}
		public Servico(long codOrdem, float valorPecas, float valorServico, String descricao) {
			this.codOrdem = codOrdem;
			this.valorPecas = valorPecas;
			this.valorServico = valorServico;
			this.descricao = descricao;
		}

		
		
}
