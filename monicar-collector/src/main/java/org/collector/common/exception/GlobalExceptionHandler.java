package org.collector.common.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.collector.common.response.CommonResponse;
import org.collector.presentation.dto.CycleInfoRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public CommonResponse<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

		String mdn = null;

		Map<String, String> validationErrors = ex.getBindingResult().getFieldErrors().stream()
			.collect(Collectors.toMap(
				FieldError::getField,
				fieldError -> {
					String defaultMessage = fieldError.getDefaultMessage();
					return defaultMessage != null ? defaultMessage : "No message available";
				}
			));

		Object target = ex.getBindingResult().getTarget();
		if (target instanceof CycleInfoRequest) {
			mdn = String.valueOf(((CycleInfoRequest) target).mdn());
		}

		return CommonResponse.validationError(validationErrors, mdn);
	}
}
