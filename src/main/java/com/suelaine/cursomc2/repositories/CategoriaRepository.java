package com.suelaine.cursomc2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suelaine.cursomc2.domain.Categoria;

//classe capaz de acessar o banco de dados e fazer consultas
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
  	
}
