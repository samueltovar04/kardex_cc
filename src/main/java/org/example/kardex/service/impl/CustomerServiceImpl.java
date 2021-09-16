package org.example.kardex.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.kardex.domain.Customer;
import org.example.kardex.domain.dto.CustomerDto;
import org.example.kardex.domain.dto.UserDto;
import org.example.kardex.repositories.CustomerRepository;
import org.example.kardex.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;

	@Autowired
	public CustomerServiceImpl(
		final CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public Optional<CustomerDto> getById(Long idClient) {
		return Optional.ofNullable(mapperCustomer(customerRepository.getOne(idClient)));
	}

	@Override
	public List<CustomerDto> getAll() {
		return customerRepository.findAll()
			.stream().map(this::mapperCustomer)
			.collect(Collectors.toList());
	}

	@Override
	public Optional<UserDto> validateUser(UserDto req) {
		return customerRepository
			.findByIdAndPassword(req.getIdUser(), req.getPassword())
			.map(this::mapper);
/*		Customer customer = customerRepository.findOne(req.getIdUser());
		return Optional.of(this.mapper(customer));*/
	}

	@Override
	@Transactional
	public CustomerDto saveCustomer(CustomerDto customer) {
		return mapperCustomer(customerRepository.saveAndFlush(mapperCustomerDto(customer)));
	}

	@Override
	@Transactional
	public CustomerDto update(CustomerDto customer) {
		return mapperCustomer(customerRepository.save(mapperCustomerDto(customer)));
	}

	private UserDto mapper(Customer customer){
		return UserDto.builder()
			.idUser(customer.getId())
			.userName(customer.getUserName())
			.password(customer.getPassword())
			.build();
	}

	private CustomerDto mapperCustomer(Customer customer){
		return CustomerDto.builder()
			.id(customer.getId())
			.userName(customer.getUserName())
			.name(customer.getName())
			.lastName(customer.getLastName())
			.email(customer.getEmail())
			.phone(customer.getPhone())
			.build();
	}
	private Customer mapperCustomerDto(CustomerDto dto){
		return Customer.builder()
			.id(dto.getId())
			.userName(dto.getUserName())
			.password(dto.getPassword())
			.name(dto.getName())
			.lastName(dto.getLastName())
			.email(dto.getEmail())
			.phone(dto.getPhone())
			.build();
	}

}
