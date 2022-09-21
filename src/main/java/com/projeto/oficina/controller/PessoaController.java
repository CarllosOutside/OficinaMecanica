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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.oficina.model.Pessoa;
import com.projeto.oficina.repository.PessoaRepo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

//RECEBE E RETORNA REQUISICOES HTTP ATRAVES DO ENDEREÇO /API
@RestController
@RequestMapping("/api")
public class PessoaController {

	//VARIÁVEL DE ACESSO AO BANCO DE DADOS
		@Autowired
	    PessoaRepo pessoarepo;
		
		//OPERAÇÕES
		/*
		 * ENCONTRA UMA PESSOA POR COD_PESSOA NO BANCO
		 * */
		@Operation(summary = "Busca uma pessoa(cod_pessoa)", description = "Retorna um JSON Pessoa com seu cod_pessoa especificado")
		@GetMapping(path="/pessoas/{cod_pessoa}") //ENDEREÇO DE REQUISIÇÃO GET
		public ResponseEntity<Pessoa> getPessoaByCod_pessoa(@Parameter(description = "Código da pessoa buscada") @PathVariable("cod_pessoa") long cod_pessoa) //COD_PESSOA É UMA PATHVARIABLE 
		{
			//PROCURA NO BANCO NA TABELA PESSOA
	        Optional<Pessoa> pessoa = pessoarepo.findById(cod_pessoa);
	 
	        if (pessoa.isPresent()) {
	            return new ResponseEntity<>(pessoa.get(), HttpStatus.OK); //RETORNA OBJETO PESSOA + MENSAGEM DE SUCESSO
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
		
		/*
		 * ENCONTRA UMA PESSOA/LISTA DE PESSOAS NO BANCO PELO SEU NOME 
		 * */
		@Operation(summary = "Busca uma/várias Pessoa/s(por nomes que ela contém)", description = "Retorna uma Pessoa cujo nome é especificado, se nenhum for especificado, retorna uma lista de pessoas")
		@GetMapping(path="/pessoas") //ENDEREÇO DE BUSCA GET
	    public ResponseEntity<List<Pessoa>> getAllPessoas(@Parameter(description = "Nome da pessoa que está sendo buscada") @RequestParam(required = false) String nome) 
		{
	        try {
	        	//CRIA A LISTA
	            List<Pessoa> pessoasList = new ArrayList<Pessoa>();
	            
	            //SE NENHUM NOME FOI ESPECIFICADO
	            if (nome == null) {
	                pessoarepo.findAll().forEach(pessoasList::add); //ADICIONA TODOS AS PESSOAS DO BANCO NA LISTA
	            } else { //SE HÁ NOME
	                pessoarepo.findByNomeContaining(nome).forEach(pessoasList::add);
	            }
	 
	            //SE NÃO HOUVEREM PESSOAS COM O NOME ESPECIFICADO
	            if (pessoasList.isEmpty()) {
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //LISTA VAZIA
	            }
	            //RETORNA A LISTA DE PESSOAS
	            return new ResponseEntity<>(pessoasList, HttpStatus.OK);
	 
	 
	        } catch (Exception e) {
	            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		
		/*
		 * CRIA UMA NOVA PESSOA
		 * */
		@Operation(summary = "Cria nova pessoa", description = "Cria uma nova Pessoa com os dados da Json recebida no caminho")
		@PostMapping(path="/pessoas") //ENDEREÇO DE REQUISIÇÃO POST
	    public ResponseEntity<Pessoa> createPessoa(@RequestBody Pessoa novaPessoa) 
		{	
			try {
				//CRIA OBJETO PESSOA COM A JSON E SALVA NO BANCO
	            Pessoa _pessoa = pessoarepo.save( new Pessoa(novaPessoa.getNome(),novaPessoa.getEndereco(),novaPessoa.getTelefone(),
	            		 novaPessoa.getCidade()) );
	            //RETORNA MENSAGEM DE SUCESSO
	            return new ResponseEntity<>(_pessoa, HttpStatus.CREATED);
	        } catch (Exception e) {
	            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		
		/*
		 * ATUALIZA OS DADOS DE UMA PESSOA
		 * */
		@Operation(summary = "Atualiza Pessoa", description = "Atualiza informações de uma Pessoa com cod_pessoa")
		@PutMapping(path="/pessoas/{cod_pessoa}") //ENDEREÇO REQUISIÇÃO PUT
	    public ResponseEntity<Pessoa> updatePessoa(@Parameter(description = "Cod_pessoa da pessoa alterada") @PathVariable("cod_pessoa") long cod_pessoa, @RequestBody Pessoa pessoaNova)
		{
	        Optional<Pessoa> pessoaAntiga = pessoarepo.findById(cod_pessoa);
	 
	        if (pessoaAntiga.isPresent()) {
	            Pessoa _pessoa = pessoaAntiga.get(); //SALVA PESSOA ANTIGA COM SEU ID EM _PESSOA (COM ID)
	            //ALTERA OS DADOS
	            _pessoa.setNome(pessoaNova.getNome());
	            _pessoa.setCidade(pessoaNova.getCidade());
	            _pessoa.setEndereco(pessoaNova.getEndereco());
	            _pessoa.setTelefone(pessoaNova.getTelefone());
	            //SOBRESCREVE A PESSOA ANTIGA NO BANCO DE DADOS(AO SALVAR COM O MESMO ID)
	            return new ResponseEntity<>(pessoarepo.save(_pessoa), HttpStatus.OK); //RETORNA MENSAGEM DE SUCESSO
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
		
		@Operation(summary = "Deleta Pessoa", description = "Deleta Pessoa por cod_pessoa")
		 @DeleteMapping(path="/pessoas/{cod_pessoa}")//ENDEREÇO REQUISIÇÃO DELETE
		    public ResponseEntity<HttpStatus> deletePessoa(@Parameter(description = "Cod_pessoa da pessoa que será deletada") @PathVariable("cod_pessoa") long cod_pessoa) {
			//PROCURA A PESSOA NO BANCO
			Optional<Pessoa> pessoadata = pessoarepo.findById(cod_pessoa);
			//SE EXISTIR, DELETA
			if(pessoadata.isPresent())
			{
			try {
		        pessoarepo.deleteById(cod_pessoa);
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		        } catch (Exception e) {
		            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		        }
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		    }
}
