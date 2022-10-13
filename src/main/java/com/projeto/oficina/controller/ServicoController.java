package com.projeto.oficina.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.oficina.model.OrdemServico;
import com.projeto.oficina.model.Servico;
import com.projeto.oficina.repository.ServicoRepo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api")
public class ServicoController {

	@Autowired
	ServicoRepo srepo;
	
	/*
	 * CRIA Servico
	 * */
	@Operation(summary = "Cria um novo servico", description = "cria um servico para ordem de servico especificada")
	@PostMapping(path="/servicos/{codOrdem}") //ENDEREÇO DE REQUISIÇÃO POST
    public ResponseEntity<Servico> createServico(@RequestBody Servico novoServico,
    		@Parameter(description = "Código da ordem") @PathVariable("codOrdem") long codOrdem) 
	{	
		try {
            Servico _servico = srepo.save( new Servico(codOrdem, novoServico.getValorPecas(), 
            		novoServico.getValorServico(), novoServico.getDescricao()));
            //RETORNA MENSAGEM DE SUCESSO
            return new ResponseEntity<>(_servico, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	//OPERAÇÕES
			/*
			 * ENCONTRA SERVICO POR COD
			 * */
			@Operation(summary = "Busca servico", description = "Retorna um JSON por id")
			@GetMapping(path="/servicos/{id}") //ENDEREÇO DE REQUISIÇÃO GET
			public ResponseEntity<Servico> getServicoByCode(@Parameter(description = "Código do servico") @PathVariable("id") long id) 
			{
				//PROCURA NO BANCO NA TABELA PESSOA
		        Optional<Servico> servico = srepo.findById(id);
		 
		        if (servico.isPresent()) {
		            return new ResponseEntity<>(servico.get(), HttpStatus.OK); 
		        } else {
		            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		        }
		    }
			
			
			/*
			 * ENCONTRA UMA PAGINA DE SERVICOS DE DETERMINADA ORDEM
			 * */
			@Operation(summary = "Busca pág de servicos)", description = "Busca servicos pela ordem de servico")
			@GetMapping(path="/servicos/ordem/{codOrdem}") //ENDEREÇO DE BUSCA GET
		    public ResponseEntity<Map<String, Object>> getAllOrdemServicos(@Parameter(description = "codOrdem")  @PathVariable("codOrdem") long codOrdem, @RequestParam(defaultValue = "0") int page,
		            @RequestParam(defaultValue = "3") int size) 
			{
		        try {
		        	//CRIA A LISTA
		            List<Servico> servList = new ArrayList<Servico>();
		            Pageable paging = PageRequest.of(page, size);
		            Page<Servico> pagServicos;
		            

		            pagServicos = srepo.findByCodOrdem(codOrdem, paging);
		 
		            servList = pagServicos.getContent();
		            
		            Map<String, Object> response = new HashMap<>();
		            response.put("servicos", servList);
		            response.put("currentPage", pagServicos.getNumber());
		            response.put("totalItems", pagServicos.getTotalElements());
		            response.put("totalPages", pagServicos.getTotalPages());
		            //SE NÃO HOUVEREM PESSOAS COM O NOME ESPECIFICADO
		            if (servList.isEmpty()) {
		                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //LISTA VAZIA
		            } //System.out.println(ordensList);
		            
		            //RETORNA A LISTA DE SERVICOS DENTRO DA RESPOSTA
		            return new ResponseEntity<>(response, HttpStatus.OK);
		 
		 
		        } catch (Exception e) {
		            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
			
			/*
			 * Deletar servico por id
			 * */
			@Transactional
			@Operation(summary = "Deleta Servico", description = "Deleta Servico por id")
			 @DeleteMapping(path="/servico/{id}")//ENDEREÇO REQUISIÇÃO DELETE
			    public ResponseEntity<HttpStatus> deleteServico(@Parameter(description = "id") @PathVariable("id") long id) {
				//PROCURA NO BANCO
				Optional<Servico> servData = srepo.findById(id);
				//SE EXISTIR, DELETA
				if(servData.isPresent())
				{
				try {
			        srepo.deleteById(id);
			        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			        } catch (Exception e) {
			            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			        }
				} else {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			    }
}
