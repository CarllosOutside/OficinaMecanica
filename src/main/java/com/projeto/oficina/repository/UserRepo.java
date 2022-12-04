package com.projeto.oficina.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.oficina.model.UserModel;


public interface UserRepo extends JpaRepository<UserModel,Long> {
	Optional<UserModel> findByNomeAndSenha(String nome, String senha);

}

