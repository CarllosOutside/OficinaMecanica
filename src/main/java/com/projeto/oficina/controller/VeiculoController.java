package com.projeto.oficina.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.oficina.model.Veiculo;
import com.projeto.oficina.service.ScraperService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

//RECEBE E RETORNA REQUISICOES HTTP ATRAVES DO ENDEREÇO /API
@RestController
@RequestMapping("/api")
public class VeiculoController {
	@Autowired
    ScraperService scraperService;

    @GetMapping(path = "/api/veiculos/{placa}")
    public Veiculo getVehicleByModel(@PathVariable String placa) {
        return  scraperService.getVehicleByPlaca(placa);
    }
}
