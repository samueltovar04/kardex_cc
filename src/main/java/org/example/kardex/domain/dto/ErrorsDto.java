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
public class ErrorsDto implements Serializable {
	private static final long serialVersionUID = 432904185185289746L;
	private String errorCode ;
	/**
	 * Campo sobre el que se ha realizado la validacion.
	 * Es opcional.
	 */
	private String message;
}
