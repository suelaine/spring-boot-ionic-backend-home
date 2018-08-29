 package com.suelaine.cursomc2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.suelaine.cursomc2.domain.Categoria;
import com.suelaine.cursomc2.repositories.CategoriaRepository;

import javassist.tools.rmi.ObjectNotFoundException;

//******************************
//CAMADA DE SERVIÇOS
//******************************

//classe responsável por fazer a  consulta no meu repositório
@Service
public class CategoriaService {
	
	//serve para instanciar autoaticamente pelo Spring
	@Autowired
	private CategoriaRepository repo;//declarando dependência do repositorio -camada de acesos a dados
	
/*	public Categoria find(Integer id) throws ObjectNotFoundException {
		Optional<Categoria> obj = repo.findById(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: "+id
					+"Tipo: "+Categoria.class.getName());
		}
		
		return obj.orElse(null);
		}*/
	
	public Categoria find(Integer id) throws ObjectNotFoundException {
		Optional<Categoria> obj = repo.findById(id);//faz a busca no banco de dados com base no id e retorna o objeto rpontinho
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
		
	}
	
	
	
}
