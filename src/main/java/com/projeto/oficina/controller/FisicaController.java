package com.projeto.oficina.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		    public ResponseEntity<Fisica> createPessoaF(@RequestBody Pessoa novaPessoa, @Parameter(description = "CPF") @RequestParam(required = true) String cpf) 
			{	
				try {
					//CRIA OBJETO PESSOA COM A JSON E SALVA NO BANCO
		            Fisica _pessoa = frepo.save( new Fisica(novaPessoa.getCod_pessoa(), cpf) );
		            //RETORNA MENSAGEM DE SUCESSO
		            return new ResponseEntity<>(_pessoa, HttpStatus.CREATED);
		        } catch (Exception e) {
		        	System.out.println(e);
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
			
			/*
			 * DELETA UMA PESSOA FISICA
			 * */
			@Transactional
			@Operation(summary = "Deleta uma pessoa Fisica pelo seu cpf", description = "Recebe um cpf e deleta pessoa")
			@DeleteMapping(path = "/fisicas/{cpf}")
			public ResponseEntity<HttpStatus> deleteFisica (@Parameter(description = "cpf") @PathVariable("cpf") String cpf){
				Optional<Fisica> _fisica = frepo.findByCpf(cpf);
				if(_fisica.isPresent()) {
					try {
						frepo.deleteByCpf(cpf);
				        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				        } catch (Exception e) {
				        	System.out.println(e);
				            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				        }
					
				}else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			}
			
			@Operation(summary = "lista de fisica", description ="retorna lista de P fisica")
			@GetMapping(path = "/fisicas")
			public ResponseEntity<List<Fisica>> getAllFisica(){	
				try 
				{
				List<Fisica> listFisicas = new ArrayList<Fisica>();
				frepo.findAll().forEach(listFisicas::add);
				if (listFisicas.isEmpty()) {
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //LISTA VAZIA
	            }
	            return new ResponseEntity<>(listFisicas, HttpStatus.OK);
				}
				catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
				
				
			}
}
