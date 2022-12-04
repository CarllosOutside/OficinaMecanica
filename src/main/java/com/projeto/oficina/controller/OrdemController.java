package com.projeto.oficina.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.oficina.model.OrdemServico;
import com.projeto.oficina.model.Pessoa;
import com.projeto.oficina.model.Veiculo;
import com.projeto.oficina.repository.OrdemRepo;
import com.projeto.oficina.repository.VeiculoRepo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class OrdemController {
	
	
	//VARIÁVEL DE ACESSO AO BANCO DE DADOS
			@Autowired
		    OrdemRepo ordemrepo;
			
			@Autowired
			VeiculoRepo veicrepo;
			
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
			 * ENCONTRA TODAS ORDENS POR FUNCIONARIO
			 * */
			@Operation(summary = "Busca uma ordem(id)", description = "Retorna um JSON ordem com seu id especificado")
			@GetMapping(path="/ordens/lista/f/{codFuncionario}") //ENDEREÇO DE REQUISIÇÃO GET
			public ResponseEntity<List<OrdemServico>> getOrdemByf(@Parameter(description = "codFuncionario")  @PathVariable("codFuncionario") long codFuncionario)
			{
				try{
					List<OrdemServico> ordensList = new ArrayList<OrdemServico>();

					ordemrepo.findAllByCodFuncionario(codFuncionario).
					forEach(ordensList::add);
					
			        if (!ordensList.isEmpty()) {
			            return new ResponseEntity<>(ordensList, HttpStatus.OK); //RETORNA OBJETO PESSOA + MENSAGEM DE SUCESSO
			        } else {
			            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			        }
				}
			        catch(Exception e) {
			        	System.out.println(e);
			        	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
		            } System.out.println(ordensList);
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
		    		@Parameter(description = "Placa do veiculo") @PathVariable("placa") String placa,
		    		@Parameter(description = "Data de abertura") @RequestParam(required = true) String dataAbertura) 
			{	
				try {
					Optional<Veiculo> veiculo = veicrepo.findById(placa);
					if (!veiculo.isPresent()) {
			           System.out.println("Veiculo nao achado");
			        } 
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date date = dateFormat.parse(dataAbertura);
		//CRIA OBJETO ORDEM COM A JSON E SALVA NO BANCO
					OrdemServico _ordem = ordemrepo.save( new OrdemServico(codFuncionario, placa,date, date, veiculo.get().getCodCliente()) );
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
			
			
			
			
			/*
			 * ENCONTRA UMA ORDEM POR MES E ANO
			 * */
			@SuppressWarnings("deprecation")
			@Operation(summary = "Busca ordens(data)", description = "Retorna  JSONs ordens com seu mes especificado")
			@GetMapping(path="/ordens/{ano}/{mes}") //ENDEREÇO DE REQUISIÇÃO GET
			public ResponseEntity<List<OrdemServico>> getOrdensByMes(@Parameter(description = "Mes") @PathVariable("mes") int mes,
					@Parameter(description = "ano") @PathVariable("ano") int ano) //COD_PESSOA É UMA PATHVARIABLE 
			{
			try{
				List<OrdemServico> ordensList = new ArrayList<OrdemServico>();
				
				Calendar lastDayOfPastMonth= Calendar.getInstance(); //ultimo dia do mes passado
				if(mes>1) //se nao for janeiro
					lastDayOfPastMonth.set(ano, mes-1, 0); //0 é o ultimo dia do mes especificado
				else
					lastDayOfPastMonth.set(ano-1, 12, 0);//se for janeiro, pega o ultimo dia de dezembro do ano passado
				
				Calendar firstDayOfNextMonth = Calendar.getInstance();
				if(mes<12)
					firstDayOfNextMonth.set(ano,mes,1);
				else
					firstDayOfNextMonth.set(ano+1,0,1); //mês 0 = janeiro
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				
				ordemrepo.findAllByDataAberturaBetween(df.parse(df.format(lastDayOfPastMonth.getTime()).toString()),
						df.parse(df.format(firstDayOfNextMonth.getTime()).toString())).
				forEach(ordensList::add);
				
		        if (!ordensList.isEmpty()) {
		            return new ResponseEntity<>(ordensList, HttpStatus.OK); //RETORNA OBJETO PESSOA + MENSAGEM DE SUCESSO
		        } else {
		            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		        }
			}
		        catch(Exception e) {
		        	System.out.println(e);
		        	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		    }
			
			
			/*
			 * ATUALIZA ORDEM
			 * */
			@Operation(summary = "Atualiza Ordem", description = "Atualiza informações de uma ordem")
			@PutMapping(path="/ordens/{id}") //ENDEREÇO REQUISIÇÃO PUT
		    public ResponseEntity<OrdemServico> updateOrdem(@Parameter(description = "Cod da ordem") @PathVariable("id") long id, @RequestBody OrdemServico ordemNova)
			{
				try {
		        Optional<OrdemServico> ordemAntiga = ordemrepo.findById(id);
		
		        if (ordemAntiga.isPresent()) {
		            OrdemServico _ordem = ordemAntiga.get(); 
		            //ALTERA OS DADOS
		            _ordem.setCodFuncionario(ordemNova.getCodFuncionario());
		            _ordem.setDataAbertura(ordemNova.getDataAbertura());
		            _ordem.setPlaca(ordemNova.getPlaca());
		            _ordem.setValorTotalPecas(ordemNova.getValorTotalPecas());
		            _ordem.setValorTotalServicos(ordemNova.getValorTotalServicos());
		            _ordem.setAberto(ordemNova.isAberto());
		            _ordem.setDevolvido(ordemNova.isDevolvido());
		            //SOBRESCREVE
		            return new ResponseEntity<>(ordemrepo.save(_ordem), HttpStatus.OK); //RETORNA MENSAGEM DE SUCESSO
		        } else {
		            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		        }}catch(Exception e) {System.out.println(e); return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}
		    }
}
