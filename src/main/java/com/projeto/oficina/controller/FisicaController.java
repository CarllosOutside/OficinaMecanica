package com.projeto.oficina.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.oficina.model.Fisica;
import com.projeto.oficina.model.Juridica;
import com.projeto.oficina.model.Pessoa;
import com.projeto.oficina.model.Veiculo;
import com.projeto.oficina.repository.FisicaRepo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

//RECEBE E RETORNA REQUISICOES HTTP ATRAVES DO ENDEREÇO /API
//@CrossOrigin(origins = {"http://localhost:3000"})
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
				        	//System.out.println(e);
				            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				        }
					
				}else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			}
			
			@CrossOrigin
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
			
			
			@Operation(summary = "Busca pf por cpf", description = "Retorna um JSON")
			@GetMapping(path="/fisicas/{cpf}") //ENDEREÇO DE REQUISIÇÃO GET
			public ResponseEntity<Fisica> getFisicaByCpf(@Parameter(description = "Cpf") @PathVariable("cpf") String cpf) 
			{
				//PROCURA NO BANCO NA TABELA VEICULO
		        Optional<Fisica> fisica = frepo.findByCpf(cpf);
		 
		        if (fisica.isPresent()) {
		            return new ResponseEntity<>(fisica.get(), HttpStatus.OK); //RETORNA OBJETO
		        } else {
		            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		        }
		    }
			
			
			@Operation(summary = "Busca pf por pessoa", description = "Retorna um JSON")
			@GetMapping(path="/fisica/pessoa/{codPessoa}") //ENDEREÇO DE REQUISIÇÃO GET
			public ResponseEntity<Fisica> getFisicaByPessoa(@Parameter(description = "codPessoa") @PathVariable("codPessoa") long codPessoa) 
			{
				//PROCURA NO BANCO NA TABELA 
		        Optional<Fisica> fisica = frepo.findByCodPessoa(codPessoa);
		 
		        if (fisica.isPresent()) {
		            return new ResponseEntity<>(fisica.get(), HttpStatus.OK); //RETORNA OBJETO
		        } else {
		            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		        }
		    }
			
			@Operation(summary = "Atualiza cpf", description = "Retorna um JSON")
			@PutMapping(path="/fisica/pessoa/{codPessoa}") //ENDEREÇO DE REQUISIÇÃO GET
			public ResponseEntity<Fisica> updateFisicaByPessoa(@Parameter(description = "codPessoa") @PathVariable("codPessoa") long codPessoa, @Parameter(description = "Novo cpf") @RequestParam(required = true) String cpf) 
			{
				//PROCURA NO BANCO NA TABELA 
		        Optional<Fisica> fisica = frepo.findByCodPessoa(codPessoa);
		 
		        if (fisica.isPresent()) {
		        	Fisica _fisica = fisica.get();
		        	_fisica.setCpf(cpf);
		        	
		            return new ResponseEntity<>(frepo.save(_fisica), HttpStatus.OK); //RETORNA OBJETO
		        } else {
		        	try {
						//CRIA OBJETO PESSOA COM A JSON E SALVA NO BANCO
			            Fisica _fisica = frepo.save( new Fisica(codPessoa, cpf) );
			            //RETORNA MENSAGEM DE SUCESSO
			            return new ResponseEntity<>(_fisica, HttpStatus.CREATED);
			        } catch (Exception e) {
			        	System.out.println(e);
			            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			        }
		        }
		    }
			
			
			@Transactional
			@Operation(summary = "Deleta uma pessoa Fisica pela pessoa", description = "Recebe uma pessoa e deleta pf")
			@DeleteMapping(path = "/fisicas/pessoa/{codPessoa}")
			public ResponseEntity<HttpStatus> deleteFisicaBypessoa(@Parameter(description = "codPessoa") @PathVariable("codPessoa") long codPessoa){
				Optional<Fisica> _fisica = frepo.findByCodPessoa(codPessoa);
				if(_fisica.isPresent()) {
					try {
						frepo.deleteByCodPessoa(codPessoa);
				        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				        } catch (Exception e) {
				        	//System.out.println(e);
				            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				        }
					
				}else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			}
}
