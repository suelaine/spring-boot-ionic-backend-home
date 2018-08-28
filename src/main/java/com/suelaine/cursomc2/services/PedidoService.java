 package com.suelaine.cursomc2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.suelaine.cursomc2.domain.Pedido;
import com.suelaine.cursomc2.repositories.PedidoRepository;

import javassist.tools.rmi.ObjectNotFoundException;

//******************************
//CAMADA DE SERVIÇOS
//******************************

//classe responsável por fazer a  consulta no meu repositório
@Service
public class PedidoService {
	
	//serve para instanciar autoaticamente pelo Spring
	@Autowired
	private PedidoRepository repo;//declarando dependência do repositorio -camada de acesos a dados
	
/*	public Pedido find(Integer id) throws ObjectNotFoundException {
		Optional<Pedido> obj = repo.findById(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: "+id
					+"Tipo: "+Pedido.class.getName());
		}
		
		return obj.orElse(null);
		}*/
	
	public Pedido find(Integer id) throws ObjectNotFoundException {
		Optional<Pedido> obj = repo.findById(id);//faz a busca no banco de dados com base no id e retorna o objeto rpontinho
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		}
}
