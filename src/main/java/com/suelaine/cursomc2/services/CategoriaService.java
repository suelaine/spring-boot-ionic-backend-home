package com.suelaine.cursomc2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.suelaine.cursomc2.domain.Categoria;
import com.suelaine.cursomc2.repositories.CategoriaRepository;
import com.suelaine.cursomc2.services.exceptions.DataIntegrityException2;
import com.suelaine.cursomc2.services.exceptions.ObjectNotFoundException;


//******************************
//CAMADA DE SERVIÇOS
//******************************

//classe responsável por fazer a  consulta no meu repositório
@Service
public class CategoriaService {

	// serve para instanciar autoaticamente pelo Spring
	@Autowired
	private CategoriaRepository repo;// declarando dependência do repositorio -camada de acesos a dados

	/*
	 * public Categoria find(Integer id) throws ObjectNotFoundException {
	 * Optional<Categoria> obj = repo.findById(id); if(obj == null) { throw new
	 * ObjectNotFoundException("Objeto não encontrado! Id: "+id
	 * +"Tipo: "+Categoria.class.getName()); }
	 * 
	 * return obj.orElse(null); }
	 */

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);// faz a busca no banco de dados com base no id e retorna o objeto
													// rpontinho
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);

	}

	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);

	}
	
	public void delete(Integer id)  {
		find(id);
		try {
		    repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException2("Não é possível excluir uma categoria que possui produtos");
			
		}

	}
	
	public List< Categoria> findAll() {		
													// rpontinho
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage,Direction.valueOf(direction),orderBy);
		return repo.findAll(pageRequest);
		
	} 

}
