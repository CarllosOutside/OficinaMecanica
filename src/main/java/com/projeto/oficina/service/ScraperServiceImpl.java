package com.projeto.oficina.service;

import com.projeto.oficina.model.Veiculo;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class ScraperServiceImpl implements ScraperService {
    //Reading data from property file to a list
    @Value("#{'${website.urls}'.split(',')}")
    List<String> urls;

    @Override
    public Veiculo getVehicleByPlaca(String placa) {
    	Veiculo veiculo = new Veiculo();
        //Traversing through the urls
        for (String url: urls) {

            if (url.contains("placafipe")) {
                //method to extract data from placafipe
                extractDataFromPlacaFipe(veiculo, url + placa);
            } 
        }
        return veiculo;
    }


    private void extractDataFromPlacaFipe(Veiculo veiculo, String url) {
        try {
            //loading the HTML to a Document Object
            Document document = Jsoup.connect(url).get();
//Selecting the element which contains the fipe table
            Element element = document.getElementsByClass("fipeTablePriceDetail").first();
            //getting all the <tr> elements inside the table
            Elements elements = element.getElementsByTag("tr");

            for (Element linhas: elements) {
            	//pega o td com nome do campo
            	String campo = linhas.child(0).text();
            	//pega o td contendo o dado
            	String dado = linhas.child(1).text();
            	
                if (StringUtils.isNotEmpty(campo) && StringUtils.isNotEmpty(dado)) {
                   //mapping data to our model class
                    if(campo.contains("Marca")) {
                    	veiculo.setMarca(dado);
                    }
                    if(campo.contentEquals("Modelo:")) {
                    	veiculo.setModelo(dado);
                    }
                    if(campo.contentEquals("Ano:")) {
                    	veiculo.setAno(dado);
                    }
                    if(campo.contains("Cor")) {
                    	veiculo.setCor(dado);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
