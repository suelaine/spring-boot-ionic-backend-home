package com.suelaine.cursomc2.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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

import com.suelaine.cursomc2.DTO.ClienteDTO;
import com.suelaine.cursomc2.DTO.ClienteNewDTO;
import com.suelaine.cursomc2.domain.Cliente;
import com.suelaine.cursomc2.services.ClienteService;

import javassist.tools.rmi.ObjectNotFoundException;

//Conversa com o objeto de Serviço capaz de entregar uma categoria
@RestController
@RequestMapping(value="/cliente")//endpoint
public class ClienteResource {
	
	//instanciando automaticamente
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)//endpoint
	//(@PathVariable serve para entender que o id de cima correesponde ao debaixo
	
	public ResponseEntity<Cliente> find(@PathVariable Integer id) throws ObjectNotFoundException {
		//tipo especial que armazena várias informações de uma resposta htttp para um serviço REST
		Cliente obj =  service.find(id);		
		return ResponseEntity.ok().body(obj);
		//metodo ok diz que a operação ocorreu com sucesso
		//objeto complexo do protocola http
		
	}
	
	// método para receber a categoria e inserir no banco de dados
		@RequestMapping(method = RequestMethod.POST)
		public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) {// @RequestBody faz o Json ser convertido
			Cliente obj = service.fromDTO(objDto);
			obj = service.insert(obj);// objeto nserido no bd que vai criar novo id
			// pega a URI (caminho tipo url do novo recurso que foi inserido
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
			return ResponseEntity.created(uri).build();
		}

		// atualização de uma categoria
		@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
		public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id){
			Cliente obj = service.fromDTO(objDto);
			obj.setId(id);
			obj = service.update(obj);
			return ResponseEntity.noContent().build();

		}
		
		@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
		public ResponseEntity<Void> delete(@RequestBody Cliente obj, @PathVariable Integer id) {
			service.delete(id);
			return ResponseEntity.noContent().build();

		}
		
		@RequestMapping(method = RequestMethod.GET) // endpoint
		// (@PathVariable serve para entender que o id de cima correesponde ao debaixo
		public ResponseEntity<List<ClienteDTO>> findAll(){
			// tipo especial que armazena várias informações de uma resposta htttp para um
			// serviço REST
			List<Cliente> list = service.findAll();
			List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
			return ResponseEntity.ok().body(listDto);
			// metodo ok diz que a operação ocorreu com sucesso
			// objeto complexo do protocola http

		}
		//DTO = OBJETO QUE VAI TER SOMENTE OS DADOS QUE EU PRECISO
		

		@RequestMapping(value="/page",method = RequestMethod.GET) // endpoint
		// (@PathVariable serve para entender que o id de cima correesponde ao debaixo
		public ResponseEntity<Page<ClienteDTO>> findPage(
				@RequestParam(value = "page", defaultValue="0") Integer page,
				@RequestParam(value = "linesPerPage", defaultValue="24") Integer linesPerPage,
				@RequestParam(value = "orderBy", defaultValue="nome") String orderBy,
				@RequestParam(value = "direction", defaultValue="ASC") String direction
				){
		
			Page<Cliente> list = service.findPage(page,linesPerPage,orderBy,direction);
			Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
			return ResponseEntity.ok().body(listDto);
		

		}

}
