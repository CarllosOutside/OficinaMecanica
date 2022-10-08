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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.oficina.model.Pessoa;
import com.projeto.oficina.repository.JuridicaRepo;
import com.projeto.oficina.model.Fisica;
import com.projeto.oficina.model.Juridica;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

//RECEBE E RETORNA REQUISICOES HTTP ATRAVES DO ENDEREÇO /API
@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api")
public class JuridicaController {
	//VARIÁVEL DE ACESSO AO BANCO DE DADOS
			@Autowired
		    JuridicaRepo jrepo;
			//OPERAÇÕES
			/*
			 * CRIA UMA PESSOA J
			 * */
			@Operation(summary = "Cria nova pessoa j", description = "Cria uma nova Pessoa com os dados da Json recebida no caminho")
			@PostMapping(path="/juridicas") //ENDEREÇO DE REQUISIÇÃO POST
		    public ResponseEntity<Juridica> createPessoaJ(@RequestBody Pessoa novaPessoa, @Parameter(description = "CNPJ") @RequestParam(required = true) String cnpj) 
			{	
				try {
					//CRIA OBJETO PESSOA COM A JSON E SALVA NO BANCO
		            Juridica _juridica = jrepo.save( new Juridica(novaPessoa.getCod_pessoa(), cnpj) );
		            //RETORNA MENSAGEM DE SUCESSO
		            return new ResponseEntity<>(_juridica, HttpStatus.CREATED);
		        } catch (Exception e) {
		        	System.out.println(e);
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
			
			/*
			 * DELETA UMA PESSOA J
			 * */
			@Transactional
			@Operation(summary = "Deleta uma pessoa Juridica pelo seu cnpj", description = "Recebe um cnpj e deleta pessoa")
			@DeleteMapping(path = "/juridicas/{cnpj}")
			public ResponseEntity<HttpStatus> deleteJuridica (@Parameter(description = "cnpj") @PathVariable("cnpj") String cnpj){
				Optional<Juridica> _juridica = jrepo.findByCnpj(cnpj);
				if(_juridica.isPresent()) {
					try {
						jrepo.deleteByCnpj(cnpj);
				        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				        } catch (Exception e) {
				        	//System.out.println(e);
				            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				        }
					
				}else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			}
			@Operation(summary = "Busca pj por cnpj", description = "Retorna um JSON")
			@GetMapping(path="/juridicas/{cnpj}") //ENDEREÇO DE REQUISIÇÃO GET
			public ResponseEntity<Juridica> getJuridicaByCnpj(@Parameter(description = "cnpj") @PathVariable("cnpj") String cnpj) 
			{
				//PROCURA NO BANCO NA TABELA 
		        Optional<Juridica> juridica = jrepo.findByCnpj(cnpj);
		 
		        if (juridica.isPresent()) {
		            return new ResponseEntity<>(juridica.get(), HttpStatus.OK); //RETORNA OBJETO
		        } else {
		            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		        }
		    }
}
