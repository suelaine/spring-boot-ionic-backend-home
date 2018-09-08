package com.suelaine.cursomc2.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.suelaine.cursomc2.DTO.ClienteNewDTO;
import com.suelaine.cursomc2.domain.Cliente;
import com.suelaine.cursomc2.domain.enums.TipoCliente;
import com.suelaine.cursomc2.repositories.ClienteRepository;
import com.suelaine.cursomc2.resource.exceptions.FieldMessage;
import com.suelaine.cursomc2.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	ClienteRepository repo;
	
	@Override
	public
	void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCodigo()) &&
				!BR.isValidCPF(objDto.getCpfOuCpf())){
			list.add(new FieldMessage("cpfOuCpf","CPF Inválido"));			
		}
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) &&
				!BR.isValidCNPJ(objDto.getCpfOuCpf())){
			list.add(new FieldMessage("cpfOuCpf","CNPJ Inválido"));			
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email","Email existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}