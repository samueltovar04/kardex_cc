package org.example.kardex.exception;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.kardex.domain.dto.ErrorsDto;

@Data
@Builder
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionsBussines extends Exception {
	private static final long serialVersionUID = 432904185185289745L;
	private List<ErrorsDto> errors;
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return super.getMessage()+errors;
	}
}
