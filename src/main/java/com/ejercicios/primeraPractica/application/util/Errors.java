package com.ejercicios.primeraPractica.application.util;

/**
 * Utility class that holds constant error messages for business exceptions.
 */
public class Errors {

	// Private constructor to prevent instantiation
	private Errors() {
		// Private constructor
	}

	/** Error message for exceeding maximum pagination limit */
	public static final String MAXIMUM_PAGINATION_EXCEEDED = "MAXIMUM_PAGINATION_EXCEEDED";

	/** Error message for person not found */
	public static final String PERSON_NOT_FOUND = "PERSON_NOT_FOUND";

	/** Error message for invalid person type */
	public static final String INVALID_PERSON_TYPE = "INVALID_PERSON_TYPE";

	/** Error message for appointment not found */
	public static final String APPOINTMENT_NOT_FOUND = "APPOINTMENT_NOT_FOUND";

	/** Error message for medical record not found */
	public static final String MEDICAL_RECORD_NOT_FOUND = "MEDICAL_RECORD_NOT_FOUND";

	/** Error message for user already existing in the database */
	public static final String USER_EXISTS = "USER_EXISTS_IN_DATABASE";

}
