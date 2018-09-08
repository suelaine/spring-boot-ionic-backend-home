package com.suelaine.cursomc2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.suelaine.cursomc2.DTO.ClienteDTO;
import com.suelaine.cursomc2.DTO.ClienteNewDTO;
import com.suelaine.cursomc2.domain.Cidade;
import com.suelaine.cursomc2.domain.Cliente;
import com.suelaine.cursomc2.domain.Endereco;
import com.suelaine.cursomc2.domain.enums.TipoCliente;
import com.suelaine.cursomc2.repositories.ClienteRepository;
import com.suelaine.cursomc2.repositories.EnderecoRepository;
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
	    @Autowired
		private EnderecoRepository enderecoRepository;

		
		public Cliente find(Integer id) {
			Optional<Cliente> obj = repo.findById(id);//faz a busca no banco de dados com base no id e retorna o objeto rpontinho
			return obj.orElseThrow(() -> new ObjectNotFoundException(
			"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
			}
		
		public Cliente insert(Cliente obj) {
			obj.setId(null);
			obj = repo.save(obj);
			enderecoRepository.saveAll(obj.getEnderecos());
			return obj;

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
				throw new DataIntegrityException2("Não é possível excluir porque há pedidos relacionados");
				
			}

		}
		
		public List< Cliente> findAll() {		
														// prontinho
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
		
		public Cliente fromDTO(ClienteNewDTO objDto) {
//			throw new UnsupportedOperationException();
			Cidade cid = new Cidade(objDto.getCidadeId(),null,null);
			Cliente cli = new Cliente(null,objDto.getNome(),objDto.getEmail(),objDto.getCpfOuCpf(),TipoCliente.toEnum(objDto.getTipo()));
			Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
			cli.getEnderecos().add(end);
			cli.getTelefones().add(objDto.getTelefone1());
			if(objDto.getTelefone2()!= null) {
				cli.getTelefones().add(objDto.getTelefone2());
			}
			if(objDto.getTelefone3()!= null) {
				cli.getTelefones().add(objDto.getTelefone3());
			}
			return cli;
			
		}
		
		
		private void updateData(Cliente newObj, Cliente obj){
			newObj.setNome(obj.getNome());
			newObj.setEmail(obj.getEmail());
		}

		
		
	}
