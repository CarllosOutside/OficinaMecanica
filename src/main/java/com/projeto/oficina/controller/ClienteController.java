package com.projeto.oficina.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.projeto.oficina.model.Cliente;
import com.projeto.oficina.model.Estado;
import com.projeto.oficina.model.Fisica;
import com.projeto.oficina.model.Funcionario;
import com.projeto.oficina.model.Juridica;
import com.projeto.oficina.model.Pessoa;
import com.projeto.oficina.repository.ClienteRepo;
import com.projeto.oficina.repository.FisicaRepo;
import com.projeto.oficina.repository.JuridicaRepo;
import com.projeto.oficina.repository.PessoaRepo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

//RECEBE E RETORNA REQUISICOES HTTP ATRAVES DO ENDEREÇO /API
@CrossOrigin
@RestController
@RequestMapping("/api")
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
			 * ENCONTRA CLIENTE POR COD PESSOA
			 * */
			@Operation(summary = "Busca cliente(cod_pessoa)", description = "Retorna um JSON Cliente")
			@GetMapping(path="/clientes/pessoa/{cod_pessoa}") //ENDEREÇO DE REQUISIÇÃO GET
			public ResponseEntity<Cliente> getClienteByCod_Pessoa(@Parameter(description = "Código da pessoa buscada") @PathVariable("cod_pessoa") long cod_pessoa)  
			{
				//PROCURA NO BANCO NA TABELA PESSOA
		        Optional<Pessoa> pessoa = prepo.findById(cod_pessoa);
		 
		        if (pessoa.isPresent()) {
		        	//PROCURA CLIENTE VINCULADO Á PESSOA
		        	Optional<Cliente> cliente = crepo.findByPessoa(pessoa.get());
		        	if (cliente.isPresent()) {
			            return new ResponseEntity<>(cliente.get(), HttpStatus.OK); 
			        } else {
			            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			        }
		        } else {
		            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		        }
		    }
			
			/*
			 * ENCONTRA UMA CLIENTE/LISTA DE CLIENTES NO BANCO PELO SEU NOME 
			 * */
			@Operation(summary = "Busca uma/várias Cliente/s(por nomes que ela contém)", description = "Retorna uma Cliente cujo nome é especificado, se nenhum for especificado, retorna uma lista de clientes")
			@GetMapping(path="/clientes") //ENDEREÇO DE BUSCA GET
		    public ResponseEntity<Map<String, Object>> getAllClientes(@Parameter(description = "Nome da cliente que está sendo buscada") @RequestParam(required = false) String nome, @RequestParam(defaultValue = "0") int page,
		            @RequestParam(defaultValue = "3") int size) 
			{
		        try {
		        	//CRIA A LISTA
		            List<Cliente> clientesList = new ArrayList<Cliente>();
		            Pageable paging = PageRequest.of(page, size);
		            Page<Cliente> paginaClientes;
		            
		            //SE NENHUM NOME FOI ESPECIFICADO
		            if (nome == null) {
		            	paginaClientes = crepo.findAll(paging);
		            } else { //SE HÁ NOME
		            	paginaClientes = crepo.findAllByPessoaNomeContaining(nome, paging);
		            }
		 
		            clientesList = paginaClientes.getContent();
		            
		            Map<String, Object> response = new HashMap<>();
		            response.put("clientes", clientesList);
		            response.put("currentPage", paginaClientes.getNumber());
		            response.put("totalItems", paginaClientes.getTotalElements());
		            response.put("totalPages", paginaClientes.getTotalPages());
		            
		            //SE NÃO HOUVEREM PESSOAS COM O NOME ESPECIFICADO
		            if (clientesList.isEmpty()) {
		                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //LISTA VAZIA
		            }
		            //RETORNA A LISTA DE CLIENTES
		            return new ResponseEntity<>(response, HttpStatus.OK);
		 
		 
		        } catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
			
			/*
			 * ENCONTRA CLIENTE POR COD_CLIENTE
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
		            Cliente _cliente = crepo.save( new Cliente(novoCliente.getPessoa(),true));
		            //RETORNA MENSAGEM DE SUCESSO
		            return new ResponseEntity<>(_cliente, HttpStatus.CREATED);
		        } catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
			
			/*
			 * OPERACAO DE "DELETE" ATIVA OU DESATIVA CLIENTE
			 * */
			@Operation(summary = "Deleta Cliente", description = "Deleta Cliente por cod_cliente")
			 @DeleteMapping(path="/clientes/{cod_cliente}")//ENDEREÇO REQUISIÇÃO DELETE
			    public ResponseEntity<Cliente> deleteCliente(@Parameter(description = "Cod_cliente") @PathVariable("cod_cliente") long cod_cliente) {
				Optional<Cliente> clientedata = crepo.findById(cod_cliente);
				if(clientedata.isPresent())
				{
					Cliente _cliente = clientedata.get();
					_cliente.setcAtivo(!_cliente.iscAtivo());
					return new ResponseEntity<>(crepo.save(_cliente), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			    }
			
			/*
			 * LISTA COM CLIENTES
			 * */
			@Operation(summary = "Busca uma/várias Clientes/s", description = " retorna uma lista de cli")
			@GetMapping(path="/clientes/lista") //ENDEREÇO DE BUSCA GET
		    public ResponseEntity<List<Cliente>> getAllClientes() 
			{
		        try {
		        	//CRIA A LISTA
		            List<Cliente> clienteList = crepo.findAll();
		            //SE NÃO HOUVEREM PESSOAS COM O NOME ESPECIFICADO
		            if (clienteList.isEmpty()) {
		                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //LISTA VAZIA
		            }
		            //RETORNA A LISTA DE CLIENTES
		            return new ResponseEntity<>(clienteList, HttpStatus.OK);
		 
		 
		        } catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
}
