package com.projeto.oficina.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.oficina.model.OrdemServico;
import com.projeto.oficina.repository.OrdemRepo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api")
public class OrdemController {
	
	
	//VARIÁVEL DE ACESSO AO BANCO DE DADOS
			@Autowired
		    OrdemRepo ordemrepo;

			
			//OPERAÇÕES
			/*
			 * ENCONTRA UMA ORDEM POR id NO BANCO
			 * */
			@Operation(summary = "Busca uma ordem(id)", description = "Retorna um JSON ordem com seu id especificado")
			@GetMapping(path="/ordens/{id}") //ENDEREÇO DE REQUISIÇÃO GET
			public ResponseEntity<OrdemServico> getOrdemById(@Parameter(description = "Código da ordem buscada") @PathVariable("id") long id) //COD_PESSOA É UMA PATHVARIABLE 
			{
				//PROCURA NO BANCO NA TABELA PESSOA
		        Optional<OrdemServico> ordem = ordemrepo.findById(id);
		 
		        if (ordem.isPresent()) {
		            return new ResponseEntity<>(ordem.get(), HttpStatus.OK); //RETORNA OBJETO PESSOA + MENSAGEM DE SUCESSO
		        } else {
		            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		        }
		    }
			
			
			/*
			 * ENCONTRA UMA ORDEM/LISTA DE ORDENS NO BANCO PELA PLACA DO VEICULO
			 * */
			@Operation(summary = "Busca uma/várias Ordens/s(por placa)", description = "Retorna uma Ordem cujo placa é especificado")
			@GetMapping(path="/ordens/veiculo/{placa}") //ENDEREÇO DE BUSCA GET
		    public ResponseEntity<Map<String, Object>> getAllVeiculoOrdens(@Parameter(description = "placa")  @PathVariable("placa") String placa, @RequestParam(defaultValue = "0") int page,
		            @RequestParam(defaultValue = "3") int size) 
			{
		        try {
		        	//CRIA A LISTA
		            List<OrdemServico> ordensList = new ArrayList<OrdemServico>();
		            Pageable paging = PageRequest.of(page, size);
		            Page<OrdemServico> paginaOrdens;
		            

		            paginaOrdens = ordemrepo.findByPlaca(placa, paging);
		 
		            ordensList = paginaOrdens.getContent();
		            
		            Map<String, Object> response = new HashMap<>();
		            response.put("ordens", ordensList);
		            response.put("currentPage", paginaOrdens.getNumber());
		            response.put("totalItems", paginaOrdens.getTotalElements());
		            response.put("totalPages", paginaOrdens.getTotalPages());
		            //SE NÃO HOUVEREM PESSOAS COM O NOME ESPECIFICADO
		            if (ordensList.isEmpty()) {
		                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //LISTA VAZIA
		            }
		            //RETORNA A LISTA DE PESSOAS
		            return new ResponseEntity<>(response, HttpStatus.OK);
		 
		 
		        } catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
			
			
			/*
			 * ENCONTRA UMA ORDEM/LISTA DE ORDENS NO BANCO PELO FUNCIONARIO
			 * */
			@Operation(summary = "Busca uma/várias Ordens/s(por func)", description = "Retorna uma Ordem cujo func é especificado")
			@GetMapping(path="/ordens/funcionario/{codFuncionario}") //ENDEREÇO DE BUSCA GET
		    public ResponseEntity<Map<String, Object>> getAllFuncionarioOrdens(@Parameter(description = "codFuncionario")  @PathVariable("codFuncionario") long codFuncionario, @RequestParam(defaultValue = "0") int page,
		            @RequestParam(defaultValue = "3") int size) 
			{
		        try {
		        	//CRIA A LISTA
		            List<OrdemServico> ordensList = new ArrayList<OrdemServico>();
		            Pageable paging = PageRequest.of(page, size);
		            Page<OrdemServico> paginaOrdens;
		            

		            paginaOrdens = ordemrepo.findByCodFuncionario(codFuncionario, paging);
		 
		            ordensList = paginaOrdens.getContent();
		            
		            Map<String, Object> response = new HashMap<>();
		            response.put("ordens", ordensList);
		            response.put("currentPage", paginaOrdens.getNumber());
		            response.put("totalItems", paginaOrdens.getTotalElements());
		            response.put("totalPages", paginaOrdens.getTotalPages());
		            //SE NÃO HOUVEREM PESSOAS COM O NOME ESPECIFICADO
		            if (ordensList.isEmpty()) {
		                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //LISTA VAZIA
		            }
		            //RETORNA A LISTA DE PESSOAS
		            return new ResponseEntity<>(response, HttpStatus.OK);
		 
		 
		        } catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
			
			
			
			/*
			 * CRIA UMA NOVA ORDEM
			 * */
			@Operation(summary = "Cria nova ordem", description = "Cria uma nova ordem com os dados da Json recebida no caminho")
			@PostMapping(path="/ordens/{codFuncionario}/{placa}") //ENDEREÇO DE REQUISIÇÃO POST
		    public ResponseEntity<OrdemServico> createPessoa(@Parameter(description = "Código do funcionario") @PathVariable("codFuncionario") long codFuncionario, 
		    		@Parameter(description = "Placa do veiculo") @PathVariable("placa") String placa) 
			{	
				try {
					//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					Date date = new Date();
		//CRIA OBJETO ORDEM COM A JSON E SALVA NO BANCO
					OrdemServico _ordem = ordemrepo.save( new OrdemServico(codFuncionario, placa,date) );
		            //RETORNA MENSAGEM DE SUCESSO
		            return new ResponseEntity<>(_ordem, HttpStatus.CREATED);
		        } catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
			
			
			@Transactional
			@Operation(summary = "Deleta Ordem", description = "Deleta Ordem por id")
			 @DeleteMapping(path="/ordem/{id}")//ENDEREÇO REQUISIÇÃO DELETE
			    public ResponseEntity<HttpStatus> deleteOrdem(@Parameter(description = "id") @PathVariable("id") long id) {
				//PROCURA A PESSOA NO BANCO
				Optional<OrdemServico> ordemData = ordemrepo.findById(id);
				//SE EXISTIR, DELETA
				if(ordemData.isPresent())
				{
				try {
			        ordemrepo.deleteById(id);
			        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			        } catch (Exception e) {
			            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			        }
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			    }
}
