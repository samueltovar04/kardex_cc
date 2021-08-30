package org.example.kardex.service;

import java.util.List;
import java.util.Optional;
import org.example.kardex.domain.dto.CustomerDto;
import org.example.kardex.domain.dto.UserDto;

public interface CustomerService {
	Optional<CustomerDto> getById(Long idClient);
	List<CustomerDto> getAll();
	Optional<UserDto> validateUser(UserDto req);
	CustomerDto saveCustomer(CustomerDto customer);
	CustomerDto update(CustomerDto customer);
}
