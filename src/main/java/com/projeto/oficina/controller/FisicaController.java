package com.projeto.oficina.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.oficina.model.Fisica;
import com.projeto.oficina.model.Pessoa;
import com.projeto.oficina.repository.FisicaRepo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

//RECEBE E RETORNA REQUISICOES HTTP ATRAVES DO ENDEREÇO /API
@RestController
@RequestMapping("/api")
public class FisicaController {
	//VARIÁVEL DE ACESSO AO BANCO DE DADOS
			@Autowired
		    FisicaRepo frepo;
			//OPERAÇÕES
			/*
			 * CRIA UMA PESSOA FISICA
			 * */
			@Operation(summary = "Cria nova pessoa f", description = "Cria uma nova Pessoa com os dados da Json recebida no caminho")
			@PostMapping(path="/fisicas") //ENDEREÇO DE REQUISIÇÃO POST
		    public ResponseEntity<Fisica> createPessoaF(@RequestBody Pessoa novaPessoa, @Parameter(description = "Nome da pessoa que está sendo buscada") @RequestParam(required = true) String cpf) 
			{	
				try {
					//CRIA OBJETO PESSOA COM A JSON E SALVA NO BANCO
		            Fisica _pessoa = frepo.save( new Fisica(novaPessoa, cpf) );
		            //RETORNA MENSAGEM DE SUCESSO
		            return new ResponseEntity<>(_pessoa, HttpStatus.CREATED);
		        } catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
}
