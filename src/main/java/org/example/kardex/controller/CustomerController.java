package org.example.kardex.controller;

import java.util.ArrayList;
import java.util.List;
import org.example.kardex.exception.ExceptionsBussines;
import org.example.kardex.domain.dto.CustomerDto;
import org.example.kardex.domain.dto.ErrorsDto;
import org.example.kardex.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Customers")
public class CustomerController {
	private final CustomerService customerService;

	@Autowired
	public CustomerController(final CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping
	public ResponseEntity<List<CustomerDto>> getAll() {
		return new ResponseEntity<>(this.customerService.getAll(), HttpStatus.ACCEPTED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDto> getById(@PathVariable("id") Long idClient)
		throws ExceptionsBussines {
		return new ResponseEntity<>(this.customerService.getById(idClient).orElseThrow(
			() -> new ExceptionsBussines(
				getErrors(
					"Cliente id " + idClient + " No existe",
					"kardex-030")
			)
		),
			HttpStatus.ACCEPTED);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<CustomerDto> save(@RequestBody CustomerDto client) {
		return new ResponseEntity<>(this.customerService.saveCustomer(client), HttpStatus.CREATED);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<CustomerDto> update(@RequestBody CustomerDto client) {
		return new ResponseEntity<>(this.customerService.update(client), HttpStatus.ACCEPTED);
	}

	private List<ErrorsDto> getErrors(String msg, String codeError){
		List<ErrorsDto> errors = new ArrayList<>();
		final ErrorsDto error = new ErrorsDto();

		error.setMessage(msg);
		error.setErrorCode(codeError);
		errors.add(error);
		return errors;
	}
}
