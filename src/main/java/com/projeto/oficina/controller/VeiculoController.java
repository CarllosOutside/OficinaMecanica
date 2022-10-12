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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.oficina.model.Pessoa;
import com.projeto.oficina.model.Veiculo;
import com.projeto.oficina.repository.VeiculoRepo;
import com.projeto.oficina.service.ScraperService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

//RECEBE E RETORNA REQUISICOES HTTP ATRAVES DO ENDEREÇO /API
@CrossOrigin(origins = {"http://localhost:3000"})
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
    public ResponseEntity<Veiculo> createVeiculo(@Parameter(description = "Placa do veiculo") @PathVariable("placa") String placa, @Parameter(description = "Codigo do cliente dono do veiculo") @RequestParam(required = true) long cod_cliente)
	{	
		//BUSCA NA TABELA FIPE USANDO WEBSCRAPER
		Veiculo veiculo = scraperService.getVehicleByPlaca(placa);
		veiculo.setCodCliente(cod_cliente);
		//System.out.println("codCli: "+veiculo.getCodCliente() + "   placa: "+veiculo.getPlaca());
		try {
			//SALVA NO BANCO
            vrepo.save(veiculo);
            //RETORNA MENSAGEM DE SUCESSO
            return new ResponseEntity<>(veiculo, HttpStatus.CREATED);
        } catch (Exception e) {
        	System.out.println(e);
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
	
	
	/*
	 * Encontra lista de veiculos de um cliente
	 * */
	@Operation(summary = "Busca veiculos de um cliente", description = "Retorna lista de veiculos de um cliente")
	@GetMapping(path="/veiculos/cliente/{codCliente}") //ENDEREÇO DE BUSCA GET
    public ResponseEntity<Map<String, Object>> getAllVeiculosFromCliente(@Parameter(description = "codigo do cliente") @PathVariable("codCliente") long codCliente, @Parameter(description = "placa") @RequestParam(required = false) String placa, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) 
	{
        try {
        	//CRIA A LISTA
            List<Veiculo> veiculosList = new ArrayList<Veiculo>();
            //cria paginação
            Pageable paging = PageRequest.of(page, size);
            Page<Veiculo> paginaVeiculos;
  
            //Busca pagina no banco
            
            if (placa == null) {
            	paginaVeiculos = vrepo.findAllByCodCliente(codCliente, paging);
            } else { //SE HÁ NOME
            	paginaVeiculos = vrepo.findByCodClienteAndPlaca(codCliente, placa,paging);
            }
            
            //extrai conteudo da pagina
            veiculosList = paginaVeiculos.getContent();
            
            //cria uma resposta http
            Map<String, Object> response = new HashMap<>();
            response.put("veiculos", veiculosList);
            response.put("currentPage", paginaVeiculos.getNumber());
            response.put("totalItems", paginaVeiculos.getTotalElements());
            response.put("totalPages", paginaVeiculos.getTotalPages());
            
            //SE NÃO HOUVEREM PESSOAS COM O NOME ESPECIFICADO
            if (veiculosList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //LISTA VAZIA
            }
            //RETORNA A resposta http
            return new ResponseEntity<>(response, HttpStatus.OK);
 
 
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	
	/*
	 * ATUALIZA OS DADOS DE UM veiculo
	 * */
	@Operation(summary = "Atualiza veiculo", description = "Atualiza informações de um veiculo")
	@PutMapping(path="/veiculos/{placa}") //ENDEREÇO REQUISIÇÃO PUT
    public ResponseEntity<Veiculo> updateVeiculo(@Parameter(description = "placa da entidade alterada") @PathVariable("placa") String placa, @RequestBody Veiculo veiculoNovo)
	{
        Optional<Veiculo> veiculoAntigo = vrepo.findById(placa);
 
        if (veiculoAntigo.isPresent()) {
            Veiculo _veiculo = veiculoAntigo.get(); //SALVA PESSOA ANTIGA COM SEU ID EM _PESSOA (COM ID)
            //ALTERA OS DADOS
            _veiculo.setAno(veiculoNovo.getAno());
            _veiculo.setCliente(veiculoNovo.getCliente());
            _veiculo.setCodCliente(veiculoNovo.getCodCliente());
            _veiculo.setCor(veiculoNovo.getCor());
            _veiculo.setMarca(veiculoNovo.getMarca());
            _veiculo.setModelo(veiculoNovo.getModelo());
            //SOBRESCREVE A PESSOA ANTIGA NO BANCO DE DADOS(AO SALVAR COM O MESMO ID)
            return new ResponseEntity<>(vrepo.save(_veiculo), HttpStatus.OK); //RETORNA MENSAGEM DE SUCESSO
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
