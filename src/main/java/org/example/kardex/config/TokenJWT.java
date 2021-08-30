package org.example.kardex.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.example.kardex.domain.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Component
public class TokenJWT {

	@Value("${jwt.secret}")
	private String sercretKey;

	@Value("${jwt.caducidad}")
	private int caducidad;

	public String generarToken(UserDto loginReq) {
		List<GrantedAuthority> grantedAuthority = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
		String token = Jwts.builder()
			.setId(loginReq.getIdUser().toString())
			.setSubject(loginReq.getUserName())
			.claim("authorities", grantedAuthority.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList())
			)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + caducidad))
			.signWith(SignatureAlgorithm.HS256, sercretKey.getBytes())
			.compact();
		return "Bearer " + token;
	}
}
