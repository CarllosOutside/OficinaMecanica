package com.projeto.oficina.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.projeto.oficina.model.Cliente;
import com.projeto.oficina.model.Pessoa;
import com.projeto.oficina.repository.ClienteRepo;
import com.projeto.oficina.repository.FisicaRepo;
import com.projeto.oficina.repository.JuridicaRepo;
import com.projeto.oficina.repository.PessoaRepo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

public class ClienteController {
	//VARIÁVEL DE ACESSO AO BANCO DE DADOS
			@Autowired
		    ClienteRepo crepo;
			
			@Autowired
			PessoaRepo prepo;
			
			@Autowired
			FisicaRepo frepo;
			
			@Autowired
			JuridicaRepo jrepo;
			
			//OPERAÇÕES
			/*
			 * ENCONTRA CLIENTE
			 * */
			@Operation(summary = "Busca cliente(cod_cliente)", description = "Retorna um JSON Cliente")
			@GetMapping(path="/clientes/{cod_cliente}") //ENDEREÇO DE REQUISIÇÃO GET
			public ResponseEntity<Cliente> getClienteByCod_Cliente(@Parameter(description = "Código da cliente buscada") @PathVariable("cod_cliente") long cod_cliente)  
			{
				//PROCURA NO BANCO 
		        Optional<Cliente> cliente = crepo.findById(cod_cliente);
		 
		        if (cliente.isPresent()) {
		            return new ResponseEntity<>(cliente.get(), HttpStatus.OK); 
		        } else {
		            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		        }
		    }
			
			
			/*
			 * CRIA CLIENTE
			 * */
			@Operation(summary = "Cria nova cliente", description = "Cria uma nova Cliente")
			@PostMapping(path="/clientes") //ENDEREÇO DE REQUISIÇÃO POST
		    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente novoCliente) 
			{	
				try {
		            Cliente _cliente = crepo.save( new Cliente(novoCliente.getPessoa()));
		            //RETORNA MENSAGEM DE SUCESSO
		            return new ResponseEntity<>(_cliente, HttpStatus.CREATED);
		        } catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
			
			
			@Operation(summary = "Deleta Cliente", description = "Deleta Cliente por cod_cliente")
			 @DeleteMapping(path="/clientes/{cod_cliente}")//ENDEREÇO REQUISIÇÃO DELETE
			    public ResponseEntity<HttpStatus> deleteCliente(@Parameter(description = "Cod_cliente") @PathVariable("cod_cliente") long cod_cliente) {
				Optional<Cliente> pessoadata = crepo.findById(cod_cliente);
				//SE EXISTIR, DELETA
				if(pessoadata.isPresent())
				{
				try {
			        crepo.deleteById(cod_cliente);
			        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			        } catch (Exception e) {
			            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			        }
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			    }
}
