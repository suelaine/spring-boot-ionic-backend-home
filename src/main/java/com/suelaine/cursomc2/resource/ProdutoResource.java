package com.suelaine.cursomc2.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.suelaine.cursomc2.DTO.ProdutoDTO;
import com.suelaine.cursomc2.domain.Produto;
import com.suelaine.cursomc2.resource.util.URL;
import com.suelaine.cursomc2.services.ProdutoService;

import javassist.tools.rmi.ObjectNotFoundException;

//Conversa com o objeto de Serviço capaz de entregar uma categoria
@RestController
@RequestMapping(value="/produtos")//endpoint
public class ProdutoResource {
	
	//instanciando automaticamente
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)//endpoint
	//(@PathVariable serve para entender que o id de cima correesponde ao debaixo
	
	public ResponseEntity<Produto> find(@PathVariable Integer id) throws ObjectNotFoundException {
		//tipo especial que armazena várias informações de uma resposta htttp para um serviço REST
		Produto obj =  service.find(id);		
		return ResponseEntity.ok().body(obj);
		//metodo ok diz que a operação ocorreu com sucesso
		//objeto complexo do protocola http
		
	}
	
	@RequestMapping(method = RequestMethod.GET) // endpoint
	// (@PathVariable serve para entender que o id de cima correesponde ao debaixo
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue="") String nome,
			@RequestParam(value = "categorias", defaultValue="") String categorias,
			@RequestParam(value = "page", defaultValue="0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value = "direction", defaultValue="ASC") String direction
			){
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
	
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		//(page,linesPerPage,orderBy,direction);
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDto);
	

	}

}
