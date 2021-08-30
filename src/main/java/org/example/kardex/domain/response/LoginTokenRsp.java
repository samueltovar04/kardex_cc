package org.example.kardex.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.kardex.domain.dto.ReponseDto;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class LoginTokenRsp extends ReponseDto {
	private String token;
}