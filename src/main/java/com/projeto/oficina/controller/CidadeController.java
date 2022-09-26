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

import com.projeto.oficina.model.Cidade;
import com.projeto.oficina.repository.CidadeRepo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

//RECEBE E RETORNA REQUISICOES HTTP ATRAVES DO ENDEREÇO /API
@RestController
@RequestMapping("/api")
public class CidadeController {
	
	//VARIÁVEL DE ACESSO AO BANCO DE DADOS
	@Autowired
    CidadeRepo cidaderepo;
	
	//OPERAÇÕES
	/*
	 * ENCONTRA UMA CIDADE NO BANCO PELO SEU COD_CIDADE
	 * */
	@Operation(summary = "Busca uma cidade(cod_cidade)", description = "Retorna um JSON Cidade com seu cod_cidade especificado")
	@GetMapping(path="/cidades/{cod_cidade}") //ENDEREÇO DE REQUISIÇÃO GET
	public ResponseEntity<Cidade> getCidadeByCod_cidade(@Parameter(description = "Código da cidade buscada") @PathVariable("cod_cidade") long cod_cidade) //COD_CIDADE É UMA PATHVARIABLE 
	{
		//PROCURA NO BANCO NA TABELA CIDADE
        Optional<Cidade> cidade = cidaderepo.findById(cod_cidade);
 
        if (cidade.isPresent()) {
            return new ResponseEntity<>(cidade.get(), HttpStatus.OK); //RETORNA OBJETO CIDADE + MENSAGEM DE SUCESSO
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	/*
	 * ENCONTRA UMA CIDADE/LISTA DE CIDADES NO BANCO PELO SEU NOME 
	 * */
	@Operation(summary = "Busca uma/várias Cidade/s(por nomes que ela contém)", description = "Retorna uma Cidade cujo nome é especificado, se nenhum for especificado, retorna uma lista de cidades")
	@GetMapping(path="/cidades") //ENDEREÇO DE BUSCA GET
    public ResponseEntity<List<Cidade>> getAllCidades(@Parameter(description = "Nome da Cidade que está sendo buscada") @RequestParam(required = false) String nome) 
	{
        try {
        	//CRIA A LISTA
            List<Cidade> cidadesList = new ArrayList<Cidade>();
            
            //SE NENHUM NOME FOI ESPECIFICADO
            if (nome == null) {
                cidaderepo.findAll().forEach(cidadesList::add); //ADICIONA TODOS AS CIDADES DO BANCO NA LISTA
            } else { //SE HÁ NOME
                cidaderepo.findByNameContaining(nome).forEach(cidadesList::add);
            }
 
            //SE NÃO HOUVEREM CIDADES COM O NOME ESPECIFICADO
            if (cidadesList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //LISTA VAZIA
            }
            //RETORNA A LISTA DE ESTADOS
            return new ResponseEntity<>(cidadesList, HttpStatus.OK);
 
 
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	/*
	 * CRIA UMA NOVA CIDADE
	 * */
	@Operation(summary = "Cria nova cidade", description = "Cria uma nova Cidade com os dados da Json recebida no caminho")
	@PostMapping(path="/cidades") //ENDEREÇO DE REQUISIÇÃO POST
    public ResponseEntity<Cidade> createCidade(@RequestBody Cidade novaCidade) 
	{	
		try {
			//CRIA OBJETO CIDADE COM A JSON E SALVA NO BANCO
            Cidade _cidade = cidaderepo.save( new Cidade(novaCidade.getCode(),novaCidade.getName(),novaCidade.getState_id()) );
            //RETORNA MENSAGEM DE SUCESSO
            return new ResponseEntity<>(_cidade, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	/*
	 * ATUALIZA OS DADOS DE UMA CIDADE
	 * */
	@Operation(summary = "Atualiza Cidade", description = "Atualiza informações de uma Cidade com cod_cidade")
	@PutMapping(path="/cidades/{cod_cidade}") //ENDEREÇO REQUISIÇÃO PUT
    public ResponseEntity<Cidade> updateCidade(@Parameter(description = "Cod_cidade da cidade a ser alterada") @PathVariable("cod_cidade") long cod_cidade, @RequestBody Cidade cidadeNova)
	{
        Optional<Cidade> cidadeAntiga = cidaderepo.findById(cod_cidade);
 
        if (cidadeAntiga.isPresent()) {
            Cidade _cidade = cidadeAntiga.get(); //SALVA CIDADE ANTIGA COM SEU ID EM _CIDADE
            //ALTERA OS DADOS
            _cidade.setCode(cidadeNova.getCode());
            _cidade.setName(cidadeNova.getName());
            _cidade.setState_id(cidadeNova.getState_id());
            //SOBRESCREVE A CIDADE ANTIGA NO BANCO DE DADOS(AO SALVAR COM O MESMO ID)
            return new ResponseEntity<>(cidaderepo.save(_cidade), HttpStatus.OK); //RETORNA MENSAGEM DE SUCESSO
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@Operation(summary = "Deleta Cidade", description = "Deleta Cidade por cod_cidade")
	 @DeleteMapping(path="/cidades/{cod_cidade}")//ENDEREÇO REQUISIÇÃO DELETE
	    public ResponseEntity<HttpStatus> deleteCidade(@Parameter(description = "Cod_cidade da cidade que será deletada") @PathVariable("cod_cidade") long cod_cidade) {
		//PROCURA A CIDADE NO BANCO
		Optional<Cidade> cidadedata = cidaderepo.findById(cod_cidade);
		
		if(cidadedata.isPresent())
		{
		try {
	        cidaderepo.deleteById(cod_cidade);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	    }
}
