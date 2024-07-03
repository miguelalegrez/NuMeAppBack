Proyecto NuMeApp
Descripción
NuMeApp es un microservicio desarrollado con Spring Boot 3.1.9 diseñado para gestionar citas médicas entre pacientes y nutricionistas. Este sistema permite la creación, modificación y consulta de citas, manteniendo un registro detallado de cada interacción entre pacientes y nutricionistas.

Arquitectura
NuMeApp está construido utilizando una Arquitectura Hexagonal, que se divide en tres capas principales:

Dominio
Esta capa contiene los Servicios y el Modelo de Negocio, y cualquier cosa relacionada exclusivamente con el dominio. En este proyecto, usamos el Modelo Anémico (Anemic Model). Los puertos de salida, como los repositorios y eventos, también residen en esta capa si se opta por el Modelo Enriquecido (Rich Model).

Aplicación
Esta capa contiene los puertos de comunicación con la capa de Infraestructura. No es necesario un puerto de entrada adicional en Java si no se va a usar para otra cosa. Aquí también residen los servicios que implementan los casos de uso, utilizando el dominio según sea necesario. Las clases de datos en esta capa son parte del Dominio.

Infraestructura
Esta capa contiene los servicios de comunicación externa de nuestra aplicación. Se divide en los paquetes necesarios para desacoplar cada entrada y salida posible. Las funcionalidades se agrupan en los siguientes paquetes predefinidos:

ApiRest
Contiene los controladores, DTOs, seguridad y clases necesarias para la comunicación por API REST.

IntegrationEvents
Contiene los consumidores, productores y clases necesarias para la comunicación por eventos.

Repository
Contiene los repositorios, DTOs y clases necesarias para la persistencia de datos.

Diagrama de Entidad-Relación

erDiagram
PERSON ||--o{ APPOINTMENT : has
PERSON ||--o{ MEDICAL_RECORD : has
PERSON {
string id
PersonType type
PersonalInfo persoInfo
List appointmentId
List medicalRecordId
}
APPOINTMENT {
string id
LocalDateTime date
string patientId
string patientName
string patientSurname
string patientDocument
string nutritionistId
string nutritionistName
string nutritionistSurname
}
PERSONALINFO {
string name
string surname
DocumentType documentType
string document
}
MEDICAL_RECORD {
string id
LocalDateTime date
string observations
string patientId
string doctorId
}

Endpoints
Personas
GET /persons
Devuelve una lista de todas las personas (pacientes y nutricionistas).

GET /persons/{id}
Devuelve la información de una persona específica por su ID.

POST /patients
Crea un nuevo paciente.

POST /nutritionists
Crea un nuevo nutricionista.

PUT /persons/{id}
Modifica la información de una persona específica.

DELETE /persons/{id}
Elimina una persona específica.

Citas
GET /appointments
Devuelve una lista de todas las citas.

GET /appointments/{id}
Devuelve la información de una cita específica por su ID.

GET /appointments/person/{personId}
Devuelve una lista de citas de una persona específica.

POST /appointments
Crea una nueva cita.

PUT /appointments/{id}
Modifica una cita específica.

DELETE /appointments/{id}
Elimina una cita específica.

Fichas Médicas
GET /medical-records
Devuelve una lista de todas las fichas médicas.

GET /medical-records/{id}
Devuelve la información de una ficha médica específica por su ID.

GET /medical-records/person/{personId}
Devuelve una lista de fichas médicas de una persona específica.

POST /medical-records
Crea una nueva ficha médica.

PUT /medical-records/{id}
Modifica una ficha médica específica.

DELETE /medical-records/{id}
Elimina una ficha médica específica.

Swagger
No se necesita ninguna configuración adicional para utilizar Swagger.

El endpoint generado para visualizar la API es:

/swagger-ui/index.html
El JSON con la descripción de la API se encuentra en:

/v3/api-docs
Para desactivar estas URL en entornos productivos, configure las siguientes propiedades:

springdoc.api-docs.enabled: false
springdoc.swagger-ui.enabled: false

Contribuye
Para contribuir al proyecto o si necesitas más información, puedes contactarnos a través del siguiente correo electrónico:
miguelalegrez@gmail.com
