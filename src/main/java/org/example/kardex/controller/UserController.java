package org.example.kardex.controller;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.example.kardex.exception.ExceptionsBussines;
import org.example.kardex.config.TokenJWT;
import org.example.kardex.domain.dto.ErrorsDto;
import org.example.kardex.domain.dto.UserDto;
import org.example.kardex.domain.response.LoginTokenRsp;
import org.example.kardex.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Users")
public class UserController {
	private static final Logger LOGGER = Logger.getLogger(UserController.class);
	public static final String RESULTADO_OK= "OK";
	public static final String RESULTADO_KO= "KO";
	private final TokenJWT jwtUtil;
	private final CustomerService customerService;

	@Autowired
	public UserController(final TokenJWT jwtUtil,
		final CustomerService customerService) {
		this.jwtUtil = jwtUtil;
		this.customerService = customerService;
	}

	/**
	 * Apis rest para login y devolver un token
	 *
	 * @param req
	 * throws ExceptionsBussines
	 */
	@PostMapping("/Login")
	public ResponseEntity<LoginTokenRsp> loginUsuario(@RequestBody UserDto req) {
		LoginTokenRsp loginRsp = new LoginTokenRsp();
		HttpStatus httpStatus = HttpStatus.OK;

		try {

			UserDto login = validateUser(req);

			String token = jwtUtil.generarToken(login);

			loginRsp.setStatus(RESULTADO_OK);
			loginRsp.setToken(token);

		} catch (ExceptionsBussines e) {

			LOGGER.error("Error ExcepcionesNegocio "+e.getMessage());

			httpStatus = HttpStatus.BAD_REQUEST;
			loginRsp.setStatus(RESULTADO_KO);
			loginRsp.setErrors(e.getErrors());
		} catch (Exception e) {

			LOGGER.error("Error Exception"+e.getMessage());

			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			loginRsp.setStatus(RESULTADO_KO);
			loginRsp.setErrors(
				getErrors("Error Generico::"+e.getMessage(), "-999")
			);
		}
		return new ResponseEntity<>(loginRsp, httpStatus);
	}

	private UserDto validateUser(UserDto req) throws ExceptionsBussines {
		return customerService.validateUser(req).orElseThrow(
			() -> new ExceptionsBussines(
				getErrors(
					"Error autenticando usuario. username o password incorrecto",
					 "kardex-020")
			)
		);
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
