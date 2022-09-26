package com.projeto.oficina.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.oficina.model.Regiao;


//ACESSA NA TABELA REGIAO, COM ID DO TIPO LONG
public interface RegiaoRepo extends JpaRepository<Regiao, Long> {

}