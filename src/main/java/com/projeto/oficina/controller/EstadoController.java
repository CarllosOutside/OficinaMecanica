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

import com.projeto.oficina.model.Estado;
import com.projeto.oficina.repository.EstadoRepo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

//RECEBE E RETORNA REQUISICOES HTTP ATRAVES DO ENDEREÇO /API
@RestController
@RequestMapping("/api")
public class EstadoController {
	
	//VARIÁVEL DE ACESSO AO BANCO DE DADOS
	@Autowired
    EstadoRepo estadorepo;
	
	//OPERAÇÕES
	/*
	 * ENCONTRA UM ESTADO NO BANCO PELO SEU CODIGO UF
	 * */
	@Operation(summary = "Busca um Estado(uf)", description = "Retorna um JSON Estado com seu código uf especificado")
	@GetMapping(path="/estados/{uf}") //ENDEREÇO DE REQUISIÇÃO GET
	public ResponseEntity<Estado> getEstadoByUf(@Parameter(description = "Uf do Estado que está sendo buscado") @PathVariable("uf") long uf) //UF É UMA PATHVARIABLE 
	{
		//PROCURA NO BANCO NA TABELA ESTADO
        Optional<Estado> estado = estadorepo.findById(uf);
 
        if (estado.isPresent()) {
            return new ResponseEntity<>(estado.get(), HttpStatus.OK); //RETORNA OBJETO ESTADO + MENSAGEM DE SUCESSO
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	/*
	 * ENCONTRA UM ESTADO NO BANCO PELO SEU NOME 
	 * */
	@Operation(summary = "Busca um/vários Estado/s(por nomes)", description = "Retorna um Estado cujo nome é especificado, se nenhum for especificado, retorna uma lista de estados")
	@GetMapping(path="/estados") //ENDEREÇO DE BUSCA GET
    public ResponseEntity<List<Estado>> getAllEstados(@Parameter(description = "Nome do Estadoque está sendo buscado") @RequestParam(required = false) String nome) 
	{
        try {
        	//CRIA A LISTA
            List<Estado> estadosList = new ArrayList<Estado>();
            
            //SE NENHUM NOME FOI ESPECIFICADO
            if (nome == null) {
                estadorepo.findAll().forEach(estadosList::add); //ADICIONA TODOS OS ESTADOS DO BANCO NA LISTA
            } else { //SE HÁ NOME
                estadorepo.findByNomeContaining(nome).forEach(estadosList::add);
            }
 
            //SE NÃO HOUVEREM ESTADOS COM O NOME ESPECIFICADO
            if (estadosList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //LISTA VAZIA
            }
            //RETORNA A LISTA DE ESTADOS
            return new ResponseEntity<>(estadosList, HttpStatus.OK);
 
 
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	/*
	 * CRIA UM ESTADO
	 * */
	@Operation(summary = "Cria novo Estado", description = "Cria um novo Estado com os dados da Json recebida no caminho")
	@PostMapping(path="/estados") //ENDEREÇO DE REQUISIÇÃO POST
    public ResponseEntity<Estado> createEstado(@RequestBody Estado novoestado) 
	{
		try {
			//CRIA OBJETO ESTADO COM A JSON E SALVA NO BANCO
            Estado _estado = estadorepo.save( new Estado(novoestado.getNome(),novoestado.getUf()) );
            //RETORNA MENSAGEM DE SUCESSO
            return new ResponseEntity<>(_estado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	/*
	 * ATUALIZA OS DADOS DE UM ESTADO
	 * */
	@Operation(summary = "Atualiza Estado", description = "Atualiza informações de um Estado dado seu codigo uf")
	@PutMapping(path="/estados/{uf}") //ENDEREÇO REQUISIÇÃO PUT
    public ResponseEntity<Estado> updateEstado(@Parameter(description = "Uf do estado a ser alterado") @PathVariable("uf") long uf, @RequestBody Estado estadoNovo)
	{
        Optional<Estado> estadoAntigo = estadorepo.findById(uf);
 
        if (estadoAntigo.isPresent()) {
            Estado _estado = estadoAntigo.get(); //SALVA ESTADO ANTIGO COM SEU ID EM _ESTADO
            //ALTERA OS DADOS
            _estado.setNome(estadoNovo.getNome());
            //SOBRESCREVE O ESTADO ANTIGO NO BANCO DE DADOS(AO SALVAR COM O MESMO ID)
            return new ResponseEntity<>(estadorepo.save(_estado), HttpStatus.OK); //RETORNA MENSAGEM DE SUCESSO
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@Operation(summary = "Deleta Estado", description = "Deleta Estado por uf")
	 @DeleteMapping(path="/estados/{uf}")//ENDEREÇO REQUISIÇÃO DELETE
	    public ResponseEntity<HttpStatus> deleteEstado(@Parameter(description = "Uf do estado que será deletado") @PathVariable("uf") long uf) {
		//PROCURA O ESTADO NO BANCO
		Optional<Estado> estadodata = estadorepo.findById(uf);
		
		if(estadodata.isPresent())
		{
		try {
	        estadorepo.deleteById(uf);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	    }
}
