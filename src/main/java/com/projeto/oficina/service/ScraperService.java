package com.projeto.oficina.service;

import  com.projeto.oficina.model.Veiculo;


//Serviço veículo
public interface ScraperService {

	//retorna um veículo pela placa
    Veiculo getVehicleByPlaca(String placa);
}