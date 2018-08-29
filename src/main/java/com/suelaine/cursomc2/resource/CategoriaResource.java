package com.suelaine.cursomc2.resource;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.suelaine.cursomc2.domain.Categoria;
import com.suelaine.cursomc2.services.CategoriaService;

import javassist.tools.rmi.ObjectNotFoundException;

//Conversa com o objeto de Serviço capaz de entregar uma categoria
@RestController
@RequestMapping(value = "/categorias") // endpoint
public class CategoriaResource {

	// instanciando automaticamente
	@Autowired
	private CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // endpoint
	// (@PathVariable serve para entender que o id de cima correesponde ao debaixo

	public ResponseEntity<Categoria> find(@PathVariable Integer id) throws ObjectNotFoundException {
		// tipo especial que armazena várias informações de uma resposta htttp para um
		// serviço REST
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
		// metodo ok diz que a operação ocorreu com sucesso
		// objeto complexo do protocola http

	}

	// método para receber a categoria e inserir no banco de dados
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) {// @RequestBody faz o Json ser convertido
		obj = service.insert(obj);// objeto nserido no bd que vai criar novo id
		// pega a URI (caminho tipo url do novo recurso que foi inserido
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	// atualização de uma categoria
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();

	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@RequestBody Categoria obj, @PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();

	}
	
	

}
