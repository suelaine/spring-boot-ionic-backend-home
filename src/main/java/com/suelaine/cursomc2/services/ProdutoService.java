 package com.suelaine.cursomc2.services;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.suelaine.cursomc2.domain.Categoria;
import com.suelaine.cursomc2.domain.Produto;
import com.suelaine.cursomc2.repositories.CategoriaRepository;
import com.suelaine.cursomc2.repositories.ProdutoRepository;

import javassist.tools.rmi.ObjectNotFoundException;

//******************************
//CAMADA DE SERVIÇOS
//******************************

//classe responsável por fazer a  consulta no meu repositório
@Service
public class ProdutoService {
	
	//serve para instanciar autoaticamente pelo Spring
	@Autowired
	private ProdutoRepository repo;//declarando dependência do repositorio -camada de acesos a dados
	@Autowired
	private CategoriaRepository categoriaRepository;
	
/*	public Produto find(Integer id) throws ObjectNotFoundException {
		Optional<Produto> obj = repo.findById(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: "+id
					+"Tipo: "+Produto.class.getName());
		}
		
		return obj.orElse(null);
		}*/
	
	public Produto find(Integer id) throws ObjectNotFoundException {
		Optional<Produto> obj = repo.findById(id);//faz a busca no banco de dados com base no id e retorna o objeto rpontinho
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
		}
	
	public Page<Produto> search(String nome, List<Integer> ids ,Integer page, Integer linesPerPage, String orderBy, String direction){
			@SuppressWarnings("deprecation")
			PageRequest pageRequest = new PageRequest(page, linesPerPage,Direction.valueOf(direction),orderBy);
			List<Categoria> categorias = categoriaRepository.findAllById(ids);
			return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);	

			
		
	}
}
