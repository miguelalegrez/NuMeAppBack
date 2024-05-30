package com.ejercicios.primeraPractica.domain.exception;

public class BusinessException extends Exception {

	private static final long serialVersionUID = -7550293140980852273L;

	public BusinessException(String message) {
		super(message);
	}

}
