package com.suelaine.cursomc2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.suelaine.cursomc2.DTO.ClienteDTO;
import com.suelaine.cursomc2.domain.Cliente;
import com.suelaine.cursomc2.repositories.ClienteRepository;
import com.suelaine.cursomc2.services.exceptions.DataIntegrityException2;
import com.suelaine.cursomc2.services.exceptions.ObjectNotFoundException;


//******************************
//CAMADA DE SERVIÇOS
//******************************

//classe responsável por fazer a  consulta no meu repositório
@Service
public class ClienteService {
	
	
	//serve para instanciar autoaticamente pelo Spring
		@Autowired
		private ClienteRepository repo;//declarando dependência do repositorio -camada de acesos a dados

		
		public Cliente find(Integer id) {
			Optional<Cliente> obj = repo.findById(id);//faz a busca no banco de dados com base no id e retorna o objeto rpontinho
			return obj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
			}
		
		public Cliente insert(Cliente obj) {
			obj.setId(null);
			return repo.save(obj);

		}

		public Cliente update(Cliente obj) {
			Cliente newObj = find(obj.getId());
			updateData(newObj,obj);
			return repo.save(newObj);

		}
		
		public void delete(Integer id)  {
			find(id);
			try {
			    repo.deleteById(id);
			}catch(DataIntegrityViolationException e) {
				throw new DataIntegrityException2("Não é possível excluir porque há entidades relacionadas");
				
			}

		}
		
		public List< Cliente> findAll() {		
														// rpontinho
			return repo.findAll();
		}
		
		public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
			PageRequest pageRequest = new PageRequest(page, linesPerPage,Direction.valueOf(direction),orderBy);
			return repo.findAll(pageRequest);
			
		} 
		
		public Cliente fromDTO(ClienteDTO objDto) {
//			throw new UnsupportedOperationException();
			return new Cliente(objDto.getId(),objDto.getNome(),objDto.getEmail(),null,null);
		}
		
		private void updateData(Cliente newObj, Cliente obj){
			newObj.setNome(obj.getNome());
			newObj.setEmail(obj.getEmail());
		}

		
		
	}
