package com.projeto.oficina;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.projeto.oficina.model.OrdemServico;
import com.projeto.oficina.repository.OrdemRepo;

@DataJpaTest
public class QueryDataTest {

    @Autowired
    private OrdemRepo repository;

    @Test
    public void whenFindByCreationDate() {
    	OrdemServico result;
    	try {
        result = repository.findByDataAbertura(
          new SimpleDateFormat("yyyy-MM-dd").parse("2022-10-31"));
        
        System.out.print(result.getId());
    	}catch(Exception e) {System.out.print(e);}
        
    }
}    