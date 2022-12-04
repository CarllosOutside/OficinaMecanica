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
@Table(name = "cities")
public class Cidade {
	
		//COLUNAS
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
		private long id;
		
		@Column
	    private long code;
		
		@Column
	    private String name;
		
		@Column(name = "state_id")
		private long estado;
			//CONSTRAINT
		@ManyToOne //foreign key
	    @JoinColumn(name = "state_id", insertable = false, updatable = false) //nome da coluna
		private Estado state_id; //state_id é foreign key que referencia a tabela Estado(id)

		//CONSTRUCTORS
		public Cidade() {}
		public Cidade(long code, String name, Estado state_id) {
			this.code = code;
			this.name = name;
			this.state_id = state_id;
		}

		//GETTERS E SETTERS
		public long getCode() {
			return code;
		}

		public void setCode(long code) {
			this.code = code;
		}
		

		public Cidade(String name, Estado state_id) {
			super();
			this.name = name;
			this.state_id = state_id;
		}
		public Cidade(long code, String name, long estado, Estado state_id) {
			this.code = code;
			this.name = name;
			this.estado = estado;
			this.state_id = state_id;
		}
		public long getEstado() {
			return estado;
		}
		public void setEstado(long estado) {
			this.estado = estado;
		}
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Estado getState_id() {
			return state_id;
		}

		public void setState_id(Estado state_id) {
			this.state_id = state_id;
		}

		public long getId() {
			return id;
		}
		
		
		
		
		

}
