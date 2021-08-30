package org.example.kardex.domain.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
	private static final long serialVersionUID = 432904185185289745L;
	private Long idUser;
	private String userName;
	private String password;
}
