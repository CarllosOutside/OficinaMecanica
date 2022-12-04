package com.projeto.oficina.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.projeto.oficina.model.UserModel;
import com.projeto.oficina.repository.UserRepo;


//RECEBE E RETORNA REQUISICOES HTTP ATRAVES DO ENDEREÇO /API
@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {
	
	//VARIÁVEL DE ACESSO AO BANCO DE DADOS
	@Autowired
    UserRepo userrepo;
	
	//OPERAÇÕES
	/*
	 * GET USER
	 * */
	@GetMapping(path="/user/cred") //ENDEREÇO DE REQUISIÇÃO GET
	public ResponseEntity<UserModel> getUser(@RequestBody UserModel user) 
	{
		//PROCURA NO BANCO NA TABELA ESTADO
        Optional<UserModel> usuario = userrepo.findByNomeAndSenha(user.getNome(), user.getSenha());
 
        if (usuario.isPresent()) {
            return new ResponseEntity<>(usuario.get(), HttpStatus.OK); //RETORNA OBJETO ESTADO + MENSAGEM DE SUCESSO
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}