package com.ejercicios.primeraPractica;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ejercicios.primeraPractica.application.port.output.PersonRepositoryOutputPort;
import com.ejercicios.primeraPractica.application.service.PersonService;
import com.ejercicios.primeraPractica.application.util.DniValidator;

public class PersonServiceTest {
	@InjectMocks
	private PersonService personService;

	@Mock
	private PersonRepositoryOutputPort personRepository;

	@Mock
	private DniValidator dniValidator;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	/*
	 * @Test void testCreatePatient_Success() { // Preparamos datos Person person =
	 * new Person(); person.setType(PersonType.PATIENT); PersonalInfo personalInfo =
	 * new PersonalInfo(); personalInfo.setDocument("12345678A");
	 * personalInfo.setDocumentType(DocumentType.DNI); // Fijar directamente el tipo
	 * de documento personalInfo.setName("John"); personalInfo.setSurname("Doe");
	 * person.setPersoInfo(personalInfo);
	 * 
	 * // Simulamos que el repositorio devuelve "new-id" cuando se crea una persona
	 * when(personRepository.createPerson(any(Person.class))).thenReturn("new-id");
	 * 
	 * // Testeamos el metodo String result = personService.createPatient(person);
	 * 
	 * // Validamos // Verificamos que el resultado es "new-id"
	 * assertEquals("new-id", result); // Verificamos que el validador de DNI se
	 * llamó con el documento correcto
	 * verify(dniValidator).validatePersonExistsByDocument("12345678A"); //
	 * Verificamos que el método createPerson del repositorio se llamó con la //
	 * persona verify(personRepository).createPerson(person); }
	 * 
	 * @Test void testCreatePatient_InvalidType() { // Arrange Person person = new
	 * Person(); person.setType(PersonType.NUTRITIONIST);
	 * 
	 * // Act & Assert IllegalArgumentException exception =
	 * assertThrows(IllegalArgumentException.class, () -> {
	 * personService.createPatient(person); });
	 * 
	 * assertEquals("Invalid person type for creating a patient",
	 * exception.getMessage()); }
	 * 
	 * @Test void testCreatePatient_DuplicateDocument() { // Arrange Person person =
	 * new Person(); person.setType(PersonType.PATIENT); PersonalInfo personalInfo =
	 * new PersonalInfo(); personalInfo.setDocument("12345678A");
	 * person.setPersoInfo(personalInfo);
	 * 
	 * doThrow(new
	 * RuntimeException("A user with this document already exists.")).when(
	 * dniValidator) .validatePersonExistsByDocument("12345678A");
	 * 
	 * // Act & Assert RuntimeException exception =
	 * assertThrows(RuntimeException.class, () -> {
	 * personService.createPatient(person); });
	 * 
	 * assertEquals("A user with this document already exists.",
	 * exception.getMessage()); }
	 * 
	 */

}
