package com.suelaine.cursomc2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.suelaine.cursomc2.domain.Cliente;
import com.suelaine.cursomc2.repositories.ClienteRepository;

import javassist.tools.rmi.ObjectNotFoundException;

//******************************
//CAMADA DE SERVIÇOS
//******************************

//classe responsável por fazer a  consulta no meu repositório
@Service
public class ClienteService {
	
	//serve para instanciar autoaticamente pelo Spring
	@Autowired
	private ClienteRepository repo;//declarando dependência do repositorio -camada de acesos a dados

	
	public Cliente find(Integer id) throws ObjectNotFoundException {
		Optional<Cliente> obj = repo.findById(id);//faz a busca no banco de dados com base no id e retorna o objeto rpontinho
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		}
}
