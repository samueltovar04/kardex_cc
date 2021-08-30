package org.example.kardex.domain.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReponseDto implements Serializable {
		private static final long serialVersionUID = 432904185185289745L;
		/**
		 * Estado que nos informa si ha habido error o no.
		 * Success = OK, failure = KO.
		 */
		private String status;
		/**
		 * En caso de haber error. Lista de los errores que han ocurrido.
		 */
		private List<ErrorsDto> errors;
}
