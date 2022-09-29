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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.oficina.model.Veiculo;
import com.projeto.oficina.repository.VeiculoRepo;
import com.projeto.oficina.service.ScraperService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

//RECEBE E RETORNA REQUISICOES HTTP ATRAVES DO ENDEREÇO /API
@RestController
@RequestMapping("/api")
public class VeiculoController {
	@Autowired
    ScraperService scraperService;

	@Autowired
	VeiculoRepo vrepo;
	

	//OPERAÇÕES
	/*
	 * ENCONTRA UM VEICULO NO BANCO PELA PLACA
	 * */
	@Operation(summary = "Busca um veiculo(placa)", description = "Retorna um JSON Veiculo com a placa dada")
	@GetMapping(path="/veiculos/{placa}") //ENDEREÇO DE REQUISIÇÃO GET
	public ResponseEntity<Veiculo> getVeiculoByPlaca(@Parameter(description = "Placa do veiculo") @PathVariable("placa") String placa) 
	{
		//PROCURA NO BANCO NA TABELA VEICULO
        Optional<Veiculo> veiculo = vrepo.findById(placa);
 
        if (veiculo.isPresent()) {
            return new ResponseEntity<>(veiculo.get(), HttpStatus.OK); //RETORNA OBJETO
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	/*
	 * ENCONTRA LISTA DE VEICULOS NO BANCO POR MARCA
	 * */
	@Operation(summary = "Busca veiculo por marca ou retorna lista de veiculos", description = "Litsa de veiculos")
	@GetMapping(path="/veiculos") //ENDEREÇO DE BUSCA GET
    public ResponseEntity<List<Veiculo>> getAllVeiculos(@Parameter(description = "Nome da marca") @RequestParam(required = false) String marca) 
	{
        try {
        	//CRIA A LISTA
            List<Veiculo> veiculoLista = new ArrayList<Veiculo>();
            
            //SE NENHUM NOME FOI ESPECIFICADO
            if (marca == null) {
                vrepo.findAll().forEach(veiculoLista::add); 
            } else { //SE HÁ NOME
            	//System.out.print("O nome não é nulo");
                vrepo.findByMarcaContaining(marca).forEach(veiculoLista::add);
            }
            
            if (veiculoLista.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //LISTA VAZIA
            }
            return new ResponseEntity<>(veiculoLista, HttpStatus.OK);
 
 
        } catch (Exception e) {
        	System.out.print(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	/*
	 * CRIA UM NOVO VEICULO
	 * */
	@Operation(summary = "Cria novo veiculo", description = "Cria um novo veiculo pela sua placa fornecida no caminho")
	@PostMapping(path="/veiculos/{placa}") //ENDEREÇO DE REQUISIÇÃO POST
    public ResponseEntity<Veiculo> createVeiculo(@Parameter(description = "Placa do veiculo") @PathVariable("placa") String placa)
	{	
		//BUSCA NA TABELA FIPE USANDO WEBSCRAPER
		Veiculo veiculo = scraperService.getVehicleByPlaca(placa);
		try {
			//SALVA NO BANCO
            vrepo.save(veiculo);
            //RETORNA MENSAGEM DE SUCESSO
            return new ResponseEntity<>(veiculo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@Operation(summary = "Deleta Veiculo do banco", description = "Deleta pela placa")
	 @DeleteMapping(path="/veiculos/{placa}")//ENDEREÇO REQUISIÇÃO DELETE
	    public ResponseEntity<HttpStatus> deleteVeiculo(@Parameter(description = "placa do veiculo") @PathVariable("placa") String placa) {
		//PROCURA A CIDADE NO BANCO
		Optional<Veiculo> veiculodata = vrepo.findById(placa);
		
		if(veiculodata.isPresent())
		{
		try {
	        vrepo.deleteById(placa);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	    }
}
