package com.projeto.oficina.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "ordem")
//@IdClass(OrdemServicoId.class)
public class OrdemServico implements Serializable{
	 private static final long serialVersionUID = -909206262878526790L; 

	 @Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private long id; 
	 
	 	//@Id //PK e FK
		@Column(name = "cod_funcionario")
		private long codFuncionario;
		
		@ManyToOne
		@JoinColumn(name = "cod_funcionario", insertable = false, updatable = false) //junta à tabela acima
		private Funcionario funcionario; // é preciso haver uma pessoa
		
		
		//@Id //PK e FK
		@Column(name = "placa")
		private String placa;
		
		@ManyToOne
		@JoinColumn(name = "placa", insertable = false, updatable = false) //junta à tabela acima
		private Veiculo veiculo; // é preciso haver uma pessoa
		
		@Temporal(TemporalType.DATE)
		@JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT-3")
		private Date dataAbertura;
		
		private float valorTotalServicos;
		
		private float valorTotalPecas;
		
		@JsonFormat(pattern = "EEE", timezone="GMT-3")
		private Date diaSemana;
		
		@Column(columnDefinition="boolean default 'false'")
		private boolean atrasado;
		
		private boolean aberto = true;
		
		@Column(columnDefinition="boolean default 'false'")
		private boolean devolvido;
		
		

		public boolean isDevolvido() {
			return devolvido;
		}

		public void setDevolvido(boolean devolvido) {
			this.devolvido = devolvido;
		}

		public boolean isAtrasado() {
			return atrasado;
		}

		public void setAtrasado(boolean atrasado) {
			this.atrasado = atrasado;
		}

		public boolean isAberto() {
			return aberto;
		}

		public void setAberto(boolean aberto) {
			this.aberto = aberto;
		}

		public long getCodFuncionario() {
			return codFuncionario;
		}

		public void setCodFuncionario(long codFuncionario) {
			this.codFuncionario = codFuncionario;
		}

		public Date getDiaSemana() {
			return diaSemana;
		}

		public void setDiaSemana(Date diaSemana) {
			this.diaSemana = diaSemana;
		}

		public Funcionario getFuncionario() {
			return funcionario;
		}

		public void setFuncionario(Funcionario funcionario) {
			this.funcionario = funcionario;
		}

		public String getPlaca() {
			return placa;
		}

		public void setPlaca(String placa) {
			this.placa = placa;
		}

		public Veiculo getVeiculo() {
			return veiculo;
		}

		public void setVeiculo(Veiculo veiculo) {
			this.veiculo = veiculo;
		}

		public Date getDataAbertura() {
			return dataAbertura;
		}

		public void setDataAbertura(Date dataAbertura) {
			this.dataAbertura = dataAbertura;
		}

		public float getValorTotalServicos() {
			return valorTotalServicos;
		}

		public void setValorTotalServicos(float valorTotalServicos) {
			this.valorTotalServicos = valorTotalServicos;
		}

		public float getValorTotalPecas() {
			return valorTotalPecas;
		}

		public void setValorTotalPecas(float valorTotalPecas) {
			this.valorTotalPecas = valorTotalPecas;
		}

		public long getId() {
			return id;
		}
		
		public OrdemServico() {}

		//Apenas é necessario fornecer a placa do veiculo, a data de abertura da ordem e o funcionario responsavel
		public OrdemServico(long codFuncionario, String placa, Date dataAbertura) {
			super();
			this.codFuncionario = codFuncionario;
			this.placa = placa;
			this.dataAbertura = dataAbertura;
		}

		public OrdemServico(long codFuncionario, String placa, Date dataAbertura, Date diaSemana) {
			super();
			this.codFuncionario = codFuncionario;
			this.placa = placa;
			this.dataAbertura = dataAbertura;
			this.diaSemana = diaSemana;
		}

		public OrdemServico(long codFuncionario, String placa, Date dataAbertura, float valorTotalServicos,
				float valorTotalPecas, boolean atrasado, boolean aberto) {
			super();
			this.codFuncionario = codFuncionario;
			this.placa = placa;
			this.dataAbertura = dataAbertura;
			this.valorTotalServicos = valorTotalServicos;
			this.valorTotalPecas = valorTotalPecas;
			this.atrasado = atrasado;
			this.aberto = aberto;
		}

		public OrdemServico(long codFuncionario, String placa, Date dataAbertura, float valorTotalServicos,
				float valorTotalPecas, Date diaSemana, boolean atrasado, boolean aberto, boolean devolvido) {
			super();
			this.codFuncionario = codFuncionario;
			this.placa = placa;
			this.dataAbertura = dataAbertura;
			this.valorTotalServicos = valorTotalServicos;
			this.valorTotalPecas = valorTotalPecas;
			this.diaSemana = diaSemana;
			this.atrasado = atrasado;
			this.aberto = aberto;
			this.devolvido = devolvido;
		}
		
		
		
		
		
}
