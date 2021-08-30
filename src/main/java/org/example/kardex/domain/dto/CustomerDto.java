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
public class CustomerDto implements Serializable {
	private static final long serialVersionUID = 4329041851852897478L;
	private Long id;
	private String userName;
	private String password;
	private String name;
	private String lastName;
	private String email;
	private String phone;
}
