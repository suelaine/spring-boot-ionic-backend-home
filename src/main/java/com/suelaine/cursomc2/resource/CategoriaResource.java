package com.suelaine.cursomc2.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.suelaine.cursomc2.DTO.CategoriaDTO;
import com.suelaine.cursomc2.domain.Categoria;
import com.suelaine.cursomc2.services.CategoriaService;

//Conversa com o objeto de Serviço capaz de entregar uma categoria
@RestController
@RequestMapping(value = "/categorias") // endpoint
public class CategoriaResource {

	// instanciando automaticamente
	@Autowired
	private CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // endpoint
	// (@PathVariable serve para entender que o id de cima correesponde ao debaixo
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
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
	
	@RequestMapping(method = RequestMethod.GET) // endpoint
	// (@PathVariable serve para entender que o id de cima correesponde ao debaixo
	public ResponseEntity<List<CategoriaDTO>> findAll(){
		// tipo especial que armazena várias informações de uma resposta htttp para um
		// serviço REST
		List<Categoria> list = service.findAll();
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
		// metodo ok diz que a operação ocorreu com sucesso
		// objeto complexo do protocola http

	}
	//DTO = OBJETO QUE VAI TER SOMENTE OS DADOS QUE EU PRECISO
	

	@RequestMapping(value="/page",method = RequestMethod.GET) // endpoint
	// (@PathVariable serve para entender que o id de cima correesponde ao debaixo
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value = "page", defaultValue="0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value = "direction", defaultValue="ASC") String direction
			){
	
		Page<Categoria> list = service.findPage(page,linesPerPage,orderBy,direction);
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listDto);
	

	}
	

}
