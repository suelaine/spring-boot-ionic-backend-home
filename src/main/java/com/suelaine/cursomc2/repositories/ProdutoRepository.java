package com.suelaine.cursomc2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suelaine.cursomc2.domain.Categoria;
import com.suelaine.cursomc2.domain.Produto;

//******************************
//CAMADA DE ACESSO A DADOS - DAOS
//******************************

//classe capaz de acessar o banco de dados e fazer consultas
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
  	
}
