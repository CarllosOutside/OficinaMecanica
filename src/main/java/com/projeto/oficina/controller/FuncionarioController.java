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

import com.projeto.oficina.model.Funcionario;
import com.projeto.oficina.model.Juridica;
import com.projeto.oficina.model.Pessoa;

import com.projeto.oficina.repository.FuncionarioRepo;
import com.projeto.oficina.repository.PessoaRepo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

//RECEBE E RETORNA REQUISICOES HTTP ATRAVES DO ENDEREÇO /API
@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api")
public class FuncionarioController {
	//VARIÁVEL DE ACESSO AO BANCO DE DADOS
			@Autowired
		    FuncionarioRepo frepo;
			
			@Autowired
			PessoaRepo prepo;
			
			
			//OPERAÇÕES
			/*
			 * ENCONTRA FUNCIONARIO POR COD PESSOA
			 * */
			@Operation(summary = "Busca funcionario(cod_pessoa)", description = "Retorna um JSON func")
			@GetMapping(path="/funcionario/pessoa/{cod_pessoa}") //ENDEREÇO DE REQUISIÇÃO GET
			public ResponseEntity<Funcionario> getFuncionarioByCod_Pessoa(@Parameter(description = "Código da pessoa buscada") @PathVariable("cod_pessoa") long cod_pessoa)  
			{
				//PROCURA NO BANCO NA TABELA PESSOA
		        Optional<Pessoa> pessoa = prepo.findById(cod_pessoa);
		 
		        if (pessoa.isPresent()) {
		        	//PROCURA CLIENTE VINCULADO Á PESSOA
		        	Optional<Funcionario> funcionario = frepo.findByPessoa(pessoa.get());
		        	if (funcionario.isPresent()) {
			            return new ResponseEntity<>(funcionario.get(), HttpStatus.OK); 
			        } else {
			            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			        }
		        } else {
		            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		        }
		    }
			
			/*
			 * ENCONTRA FUNCIONARIO POR NOME
			 * */
			@Operation(summary = "Busca uma/várias Funcionario/s(por nomes que ela contém)", description = "Retorna uma Funcionario cujo nome é especificado, se nenhum for especificado, retorna uma lista de func")
			@GetMapping(path="/funcionarios") //ENDEREÇO DE BUSCA GET
		    public ResponseEntity<Map<String, Object>> getAllFuncionarios(@Parameter(description = "Nome do func que está sendo buscada") @RequestParam(required = false) String nome, @RequestParam(defaultValue = "0") int page,
		            @RequestParam(defaultValue = "3") int size) 
			{
		        try {
		        	//CRIA A LISTA
		            List<Funcionario> funcionariosList = new ArrayList<Funcionario>();
		            Pageable paging = PageRequest.of(page, size);
		            Page<Funcionario> paginaFuncionarios;
		            
		            //SE NENHUM NOME FOI ESPECIFICADO
		            if (nome == null) {
		            	paginaFuncionarios = frepo.findAll(paging);
		            } else { //SE HÁ NOME
		            	paginaFuncionarios = frepo.findAllByPessoaNomeContaining(nome, paging);
		            }
		 
		            funcionariosList = paginaFuncionarios.getContent();
		            
		            Map<String, Object> response = new HashMap<>();
		            response.put("funcionarios", funcionariosList);
		            response.put("currentPage", paginaFuncionarios.getNumber());
		            response.put("totalItems", paginaFuncionarios.getTotalElements());
		            response.put("totalPages", paginaFuncionarios.getTotalPages());
		            
		            //SE NÃO HOUVEREM PESSOAS COM O NOME ESPECIFICADO
		            if (funcionariosList.isEmpty()) {
		                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //LISTA VAZIA
		            }
		            //RETORNA A LISTA DE CLIENTES
		            return new ResponseEntity<>(response, HttpStatus.OK);
		 
		 
		        } catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
			
			/*
			 * ENCONTRA FUNCIONARIO PELO SEU CODIGO
			 * */
			@Operation(summary = "Busca cliente(cod_cliente)", description = "Retorna um JSON Cliente")
			@GetMapping(path="/funcionario/{cod_funcionario}") //ENDEREÇO DE REQUISIÇÃO GET
			public ResponseEntity<Funcionario> getFuncionarioByCod_Funcionario(@Parameter(description = "Código da func buscada") @PathVariable("cod_funcionario") long cod_funcionario)  
			{
				//PROCURA NO BANCO 
		        Optional<Funcionario> funcionario = frepo.findById(cod_funcionario);
		        if (funcionario.isPresent()) {
		            return new ResponseEntity<>(funcionario.get(), HttpStatus.OK); 
		        } else {
		            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		        }
				
		    }
			
			
			/*
			 * CRIA FUNCIONARIO
			 * */
			@Operation(summary = "Cria nova func", description = "Cria uma nova funcionario")
			@PostMapping(path="/funcionario") //ENDEREÇO DE REQUISIÇÃO POST
		    public ResponseEntity<Funcionario> createCliente(@RequestBody Funcionario novoFuncionario) 
			{	
				try {
		            Funcionario _funcionario = frepo.save( new Funcionario(novoFuncionario.getPessoa()));
		            //RETORNA MENSAGEM DE SUCESSO
		            return new ResponseEntity<>(_funcionario, HttpStatus.CREATED);
		        } catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
			
			
			@Operation(summary = "Deleta Funcionario", description = "Deleta Funcionairo por cod_funcionario")
			 @DeleteMapping(path="/funcionario/{cod_funcionario}")//ENDEREÇO REQUISIÇÃO DELETE
			    public ResponseEntity<HttpStatus> deleteFuncionario(@Parameter(description = "cod_funcionario") @PathVariable("cod_funcionario") long cod_funcionario) {
				Optional<Funcionario> funcionariodata = frepo.findById(cod_funcionario);
				//SE EXISTIR, DELETA
				if(funcionariodata.isPresent())
				{
				try {
			        frepo.deleteById(cod_funcionario);
			        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			        } catch (Exception e) {
			            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			        }
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			    }
}
