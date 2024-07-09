package com.ejercicios.primeraPractica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.ejercicios.primeraPractica.application.port.output.PersonRepositoryOutputPort;
import com.ejercicios.primeraPractica.application.service.PersonService;
import com.ejercicios.primeraPractica.application.util.Constants;
import com.ejercicios.primeraPractica.application.util.DniValidator;
import com.ejercicios.primeraPractica.application.util.Errors;
import com.ejercicios.primeraPractica.domain.exception.BusinessException;
import com.ejercicios.primeraPractica.domain.mapper.PersonPatchMapper;
import com.ejercicios.primeraPractica.domain.model.Person;
import com.ejercicios.primeraPractica.domain.model.PersonType;

class PersonServiceTest {

	@Mock
	private PersonRepositoryOutputPort personRepository;

	@Mock
	private PersonPatchMapper personPatchMapper;

	@Mock
	private DniValidator dniValidator;

	@InjectMocks
	private PersonService personService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetPersonsByType() throws BusinessException {
		Pageable pageable = mock(Pageable.class);
		Page<Person> page = new PageImpl<>(List.of(new Person()));
		when(pageable.getPageSize()).thenReturn(10);
		when(personRepository.getPersonsByPersonType(any(PersonType.class), any(Pageable.class))).thenReturn(page);

		Page<Person> result = personService.getPersonsByType(PersonType.PATIENT, pageable);

		assertNotNull(result);
		verify(personRepository).getPersonsByPersonType(any(PersonType.class), any(Pageable.class));
	}

	@Test
	void testGetPersonsByTypePaginationExceeded() throws BusinessException {
		Pageable pageable = mock(Pageable.class);
		when(pageable.getPageSize()).thenReturn(Constants.MAXIMUM_PAGINATION + 1);

		BusinessException thrown = assertThrows(BusinessException.class,
				() -> personService.getPersonsByType(PersonType.PATIENT, pageable),
				"Expected getPersonsByType() to throw, but it didn't");

		assertEquals(Errors.MAXIMUM_PAGINATION_EXCEEDED, thrown.getMessage());
	}

	@Test
	void testGetPersonById() {
		String id = "1";
		Person person = new Person();
		when(personRepository.getPersonById(id)).thenReturn(Optional.of(person));

		Optional<Person> result = personService.getPersonById(id);

		assertTrue(result.isPresent());
		verify(personRepository).getPersonById(id);
	}

	@Test
	void testGetPersonByIdNotFound() {
		String id = "1";
		when(personRepository.getPersonById(id)).thenReturn(Optional.empty());

		Optional<Person> result = personService.getPersonById(id);

		assertFalse(result.isPresent());
		verify(personRepository).getPersonById(id);
	}

	@Test
	void testGetPersonByNameAndSurnameAndType() throws BusinessException {
		Pageable pageable = mock(Pageable.class);
		Page<Person> page = new PageImpl<>(List.of(new Person()));
		when(pageable.getPageSize()).thenReturn(10);
		when(personRepository.findByNameAndSurnameAndPersonType(anyString(), anyString(), any(PersonType.class),
				any(Pageable.class))).thenReturn(page);

		Page<Person> result = personService.getPersonByNameAndSurnameAndType("John", "Doe", PersonType.PATIENT,
				pageable);

		assertNotNull(result);
		verify(personRepository).findByNameAndSurnameAndPersonType(anyString(), anyString(), any(PersonType.class),
				any(Pageable.class));
	}

	@Test
	void testGetPersonByNameAndSurnameAndTypePaginationExceeded() throws BusinessException {
		Pageable pageable = mock(Pageable.class);
		when(pageable.getPageSize()).thenReturn(Constants.MAXIMUM_PAGINATION + 1);

		BusinessException thrown = assertThrows(BusinessException.class,
				() -> personService.getPersonByNameAndSurnameAndType("John", "Doe", PersonType.PATIENT, pageable),
				"Expected getPersonByNameAndSurnameAndType() to throw, but it didn't");

		assertEquals(Errors.MAXIMUM_PAGINATION_EXCEEDED, thrown.getMessage());
	}

	@Test
	void testFindByPersoInfoDocument() {
		String document = "12345678A";
		Person person = new Person();
		when(personRepository.findByPersoInfoDocument(document)).thenReturn(Optional.of(person));

		Optional<Person> result = personService.findByPersoInfoDocument(document);

		assertTrue(result.isPresent());
		verify(personRepository).findByPersoInfoDocument(document);
	}

	@Test
	void testFindByPersoInfoDocumentNotFound() {
		String document = "12345678A";
		when(personRepository.findByPersoInfoDocument(document)).thenReturn(Optional.empty());

		Optional<Person> result = personService.findByPersoInfoDocument(document);

		assertFalse(result.isPresent());
		verify(personRepository).findByPersoInfoDocument(document);
	}

	@Test
	void testCreatePatient() throws BusinessException {
		Person person = new Person();
		person.setPersonType(PersonType.PATIENT);
		doNothing().when(dniValidator).validatePersonExistsByDocument(anyString());
		when(personRepository.createPerson(any(Person.class))).thenReturn("1");

		String result = personService.createPatient(person);

		assertNotNull(result);
		verify(personRepository).createPerson(any(Person.class));
	}

	@Test
	void testCreatePatientInvalidPersonType() {
		Person person = new Person();
		person.setPersonType(PersonType.NUTRITIONIST);

		BusinessException thrown = assertThrows(BusinessException.class, () -> personService.createPatient(person),
				"Expected createPatient() to throw, but it didn't");

		assertEquals(Errors.INVALID_PERSON_TYPE, thrown.getMessage());
	}

	@Test
	void testCreatePatientDuplicateDocument() throws BusinessException {
		Person person = new Person();
		person.setPersonType(PersonType.PATIENT);
		doThrow(new BusinessException(Errors.USER_EXISTS)).when(dniValidator)
				.validatePersonExistsByDocument(anyString());

		BusinessException thrown = assertThrows(BusinessException.class, () -> personService.createPatient(person),
				"Expected createPatient() to throw, but it didn't");

		assertEquals(Errors.USER_EXISTS, thrown.getMessage());
	}

	@Test
	void testCreateNutritionist() throws BusinessException {
		Person person = new Person();
		person.setPersonType(PersonType.NUTRITIONIST);
		doNothing().when(dniValidator).validatePersonExistsByDocument(anyString());
		when(personRepository.createPerson(any(Person.class))).thenReturn("1");

		String result = personService.createNutritionist(person);

		assertNotNull(result);
		verify(personRepository).createPerson(any(Person.class));
	}

	@Test
	void testCreateNutritionistInvalidPersonType() {
		Person person = new Person();
		person.setPersonType(PersonType.PATIENT);

		BusinessException thrown = assertThrows(BusinessException.class, () -> personService.createNutritionist(person),
				"Expected createNutritionist() to throw, but it didn't");

		assertEquals(Errors.INVALID_PERSON_TYPE, thrown.getMessage());
	}

	@Test
	void testCreateNutritionistDuplicateDocument() throws BusinessException {
		Person person = new Person();
		person.setPersonType(PersonType.NUTRITIONIST);
		doThrow(new BusinessException(Errors.USER_EXISTS)).when(dniValidator)
				.validatePersonExistsByDocument(anyString());

		BusinessException thrown = assertThrows(BusinessException.class, () -> personService.createNutritionist(person),
				"Expected createNutritionist() to throw, but it didn't");

		assertEquals(Errors.USER_EXISTS, thrown.getMessage());
	}

	@Test
	void testModifyPerson() throws BusinessException {
		Person person = new Person();
		person.setId("1");
		when(personRepository.getPersonById(person.getId())).thenReturn(Optional.of(person));

		personService.modifyPerson(person);

		verify(personRepository).modifyPerson(any(Person.class));
	}

	@Test
	void testModifyPersonNotFound() {
		Person person = new Person();
		person.setId("1");
		when(personRepository.getPersonById(person.getId())).thenReturn(Optional.empty());

		BusinessException thrown = assertThrows(BusinessException.class, () -> personService.modifyPerson(person),
				"Expected modifyPerson() to throw, but it didn't");

		assertEquals(Errors.PERSON_NOT_FOUND, thrown.getMessage());
	}

	@Test
	void testModifyPartialPerson() throws BusinessException {
		Person person = new Person();
		person.setId("1");
		when(personRepository.getPersonById(person.getId())).thenReturn(Optional.of(person));

		personService.modifyPartialPerson(person);

		verify(personPatchMapper).update(any(Person.class), any(Person.class));
		verify(personRepository).modifyPerson(any(Person.class));
	}

	@Test
	void testModifyPartialPersonNotFound() {
		Person person = new Person();
		person.setId("1");
		when(personRepository.getPersonById(person.getId())).thenReturn(Optional.empty());

		BusinessException thrown = assertThrows(BusinessException.class,
				() -> personService.modifyPartialPerson(person),
				"Expected modifyPartialPerson() to throw, but it didn't");

		assertEquals(Errors.PERSON_NOT_FOUND, thrown.getMessage());
	}

	@Test
	void testDeletePerson() throws BusinessException {
		String id = "1";
		Person person = new Person();
		when(personRepository.getPersonById(id)).thenReturn(Optional.of(person));

		personService.deletePerson(id);

		verify(personRepository).deletePerson(id);
	}

	@Test
	void testDeletePersonNotFound() {
		String id = "1";
		when(personRepository.getPersonById(id)).thenReturn(Optional.empty());

		BusinessException thrown = assertThrows(BusinessException.class, () -> personService.deletePerson(id),
				"Expected deletePerson() to throw, but it didn't");

		assertEquals(Errors.PERSON_NOT_FOUND, thrown.getMessage());
	}
}