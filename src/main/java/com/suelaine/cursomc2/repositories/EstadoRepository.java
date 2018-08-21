package com.suelaine.cursomc2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suelaine.cursomc2.domain.Estado;

//******************************
//CAMADA DE ACESSO A DADOS - DAOS
//******************************

//classe capaz de acessar o banco de dados e fazer consultas
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{//tioespecialdospring
 //um objeto desse tipo CategoriaRepository vai ser capaz de realizar operações de acesso a dados referentes ao objeto Categoria
	//que está mapeado com a tabela Categoria
}
