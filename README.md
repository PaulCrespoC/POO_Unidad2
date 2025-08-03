# Sistema de Gestión de Contenido Audiovisual v2.0

## Descripción del Proyecto

Este proyecto implementa un sistema completo de gestión de contenido audiovisual utilizando conceptos avanzados de Programación Orientada a Objetos (POO) en Java. La versión 2.0 incluye una interfaz gráfica con Java Swing, patrón arquitectónico MVC, manejo de archivos CSV, aplicación de principios SOLID y pruebas unitarias con JUnit.

### Características Principales v2.0

- **Interfaz Gráfica**: Aplicación desktop con Java Swing
- **Patrón MVC**: Separación clara entre Modelo, Vista y Controlador
- **Principios SOLID**: Implementación completa de todos los principios
- **Manejo de Archivos**: Persistencia de datos en formato CSV
- **Pruebas Unitarias**: Cobertura completa con JUnit 5
- **Inyección de Dependencias**: Configuración flexible de componentes

## Objetivos y Propósito

- **Objetivo Principal**: Demostrar la implementación de conceptos avanzados de POO como herencia, asociación, agregación y composición
- **Propósito Educativo**: Servir como ejemplo práctico de diseño de sistemas orientados a objetos
- **Funcionalidad**: Proporcionar un sistema funcional para gestionar contenido audiovisual y sus relaciones

## Características Principales

### Clases Base
- **ContenidoAudiovisual**: Clase abstracta que define la estructura base para todo contenido audiovisual
- Incluye atributos comunes como título, duración, género e ID único

### Subclases de Contenido
1. **Película**: Contenido cinematográfico con estudio productor y actores
2. **SerieDeTV**: Series televisivas con temporadas y episodios
3. **Documental**: Contenido educativo con investigadores colaboradores
4. **VideoYouTube**: Videos de plataforma digital con métricas de engagement
5. **Cortometraje**: Producciones cinematográficas cortas con información de festivales

### Clases de Relación
1. **Actor**: Información de actores que participan en películas (Agregación)
2. **Temporada**: Temporadas que pertenecen a series de TV (Composición)
3. **Investigador**: Investigadores que colaboran en documentales (Asociación)

## Tipos de Relaciones Implementadas

### 1. Herencia
- Todas las subclases heredan de `ContenidoAudiovisual`
- Implementan polimorfismo a través del método `mostrarDetalles()`

### 2. Agregación (Película ↔ Actor)
- Una película puede tener múltiples actores
- Los actores pueden existir independientemente de las películas
- Relación "tiene-un" débil

### 3. Composición (SerieDeTV ↔ Temporada)
- Una serie de TV contiene temporadas
- Las temporadas no pueden existir sin la serie
- Relación "parte-de" fuerte

### 4. Asociación (Documental ↔ Investigador)
- Los documentales colaboran con investigadores
- Los investigadores pueden participar en múltiples documentales
- Relación "usa-un" independiente

## Estructura del Proyecto

```
/
├── src/
│   ├── model/                          # Modelo (M en MVC)
│   │   ├── ContenidoAudiovisual.java    # Clase abstracta base
│   │   ├── Pelicula.java                # Subclase para películas
│   │   ├── SerieDeTV.java               # Subclase para series
│   │   ├── Documental.java              # Subclase para documentales
│   │   ├── VideoYouTube.java            # Subclase para videos de YouTube
│   │   ├── Cortometraje.java            # Subclase para cortometrajes
│   │   ├── Actor.java                   # Clase de relación (Agregación)
│   │   ├── Temporada.java               # Clase de relación (Composición)
│   │   └── Investigador.java            # Clase de relación (Asociación)
│   ├── view/                           # Vista (V en MVC)
│   │   ├── MainView.java                # Ventana principal
│   │   └── ContenidoDialog.java         # Diálogo para CRUD
│   ├── controller/                     # Controlador (C en MVC)
│   │   └── ContenidoController.java     # Controlador principal
│   ├── service/                        # Servicios de negocio
│   │   ├── ContenidoService.java        # Lógica de negocio
│   │   ├── ContenidoRepository.java     # Repositorio de datos
│   │   └── FileHandlerService.java      # Manejo de archivos
│   ├── interfaces/                     # Interfaces (DIP - SOLID)
│   │   ├── IContenidoService.java       # Interfaz del servicio
│   │   ├── IContenidoRepository.java    # Interfaz del repositorio
│   │   └── IFileHandler.java            # Interfaz para archivos
│   └── MainApplication.java            # Punto de entrada principal
├── test/                               # Pruebas unitarias (JUnit 5)
│   ├── model/                          # Tests del modelo
│   ├── service/                        # Tests de servicios
│   ├── controller/                     # Tests del controlador
│   └── TestSuite.java                  # Suite de pruebas
├── data/                               # Archivos de datos CSV
├── bin/                                # Archivos compilados
└── README.md                           # Este archivo
```

## Instrucciones de Instalación y Uso

### Requisitos Previos
- Java Development Kit (JDK) 11 o superior
- JUnit 5 para ejecutar pruebas unitarias
- Un IDE de Java (Eclipse, IntelliJ IDEA, VS Code) o terminal con javac

### Instalación

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/paulcrespoc/POO_Unidad2.git
   cd POO_Unidad2
   ```

2. **Crear directorios necesarios**:
   ```bash
   mkdir -p bin data
   ```

3. **Compilar el proyecto**:
   ```bash
   # Compilar todas las clases del sistema
   javac -d bin src/model/*.java src/view/*.java src/controller/*.java src/service/*.java src/interfaces/*.java src/*.java
   ```

4. **Ejecutar la aplicación**:
   ```bash
   # Ejecutar la interfaz gráfica (RECOMENDADO)
   java -cp bin MainApplication
   
   # O ejecutar las pruebas de consola originales
   java -cp bin poo.PruebaAudioVisual
   ```

5. **Ejecutar las pruebas unitarias** (opcional):
   ```bash
   # Requiere JUnit 5 en el classpath
   # Consultar documentación de tu IDE para configurar y ejecutar las pruebas
   ```

### Uso del Sistema

#### Interfaz Gráfica (Recomendado)

La aplicación principal (`MainApplication`) proporciona una interfaz gráfica intuitiva:

1. **Gestión de Contenido**: Agregar, editar y eliminar contenido audiovisual
2. **Búsqueda y Filtrado**: Buscar por título, género o tipo de contenido
3. **Persistencia**: Cargar y guardar datos en archivos CSV
4. **Estadísticas**: Ver resumen del contenido almacenado

#### Funcionalidades Principales

- **CRUD Completo**: Crear, leer, actualizar y eliminar contenido
- **Relaciones**: Gestionar actores en películas, temporadas en series, investigadores en documentales
- **Exportar/Importar**: Manejo de archivos CSV para persistencia
- **Validaciones**: Controles de entrada y validación de datos
- **Estadísticas**: Reportes automáticos del sistema

### Ejemplo de Salida

```
=== SISTEMA DE GESTIÓN DE CONTENIDO AUDIOVISUAL ===

Detalles de la película:
ID: 0
Título: Avatar
Duración en minutos: 162
Género: Ciencia Ficción
Estudio: 20th Century Studios
Actores:
  - Sam Worthington (Principal)
  - Zoe Saldana (Principal)

Detalles de la serie de TV:
ID: 2
Título: Game of Thrones
Duración en minutos: 60
Género: Fantasía
Temporadas: 8
Detalles de temporadas:
  - Temporada 1 (10 episodios)
  - Temporada 2 (10 episodios)
```

## Funcionalidades Nuevas Implementadas

### Clases Agregadas
- **Actor**: Gestión de información de actores con nacionalidad, edad y tipo de actuación
- **Temporada**: Manejo de temporadas con episodios, fechas de estreno y finalización
- **Investigador**: Información académica y profesional de investigadores
- **VideoYouTube**: Métricas de visualización, likes y calidad de video
- **Cortometraje**: Información de festivales, premios y categorización automática

### Métodos Especiales
- `calcularRatioLikes()`: Calcula el porcentaje de likes en videos de YouTube
- `getCategoria()`: Determina automáticamente la categoría de cortometrajes
- `contribuirDocumental()`: Simula la colaboración de investigadores
- `agregarEpisodio()`: Gestiona episodios dentro de temporadas

## Mejoras Adicionales Implementadas

1. **Validación de Datos**: Verificación de relaciones válidas
2. **Polimorfismo Completo**: Implementación consistente de `mostrarDetalles()`
3. **Encapsulación**: Uso apropiado de getters y setters
4. **Documentación**: Comentarios explicativos en todas las clases
5. **Manejo de Colecciones**: Uso de `ArrayList` para gestionar relaciones múltiples

## Diagrama de Clases

El proyecto incluye un diagrama de clases UML que muestra:
- Todas las clases y sus atributos/métodos
- Relaciones de herencia (extends)
- Relaciones de agregación, composición y asociación
- Multiplicidades y direcciones de las relaciones

## Casos de Uso

1. **Gestión de Películas**: Crear películas y asignar actores
2. **Organización de Series**: Estructurar series con múltiples temporadas
3. **Documentales Educativos**: Asociar investigadores con documentales
4. **Contenido Digital**: Gestionar videos de YouTube con métricas
5. **Producción Independiente**: Catalogar cortometrajes y sus reconocimientos

## Principios SOLID Aplicados

### 1. Single Responsibility Principle (SRP)
- **ContenidoAudiovisual**: Responsabilidad única de definir la estructura base
- **FileHandlerService**: Solo maneja operaciones de archivos
- **ContenidoService**: Solo contiene lógica de negocio
- **ContenidoController**: Solo coordina entre vista y modelo

### 2. Open/Closed Principle (OCP)
- **ContenidoAudiovisual**: Abierta para extensión (nuevas subclases), cerrada para modificación
- **Interfaces**: Permiten nuevas implementaciones sin modificar código existente

### 3. Liskov Substitution Principle (LSP)
- Todas las subclases de **ContenidoAudiovisual** pueden sustituir a la clase base
- Polimorfismo correcto en arrays y listas de contenido

### 4. Interface Segregation Principle (ISP)
- **IFileHandler**: Interfaz específica para manejo de archivos
- **IContenidoService**: Interfaz específica para servicios de contenido
- **IContenidoRepository**: Interfaz específica para repositorio de datos

### 5. Dependency Inversion Principle (DIP)
- **ContenidoController** depende de **IContenidoService** (abstracción)
- **ContenidoService** depende de **IContenidoRepository** (abstracción)
- **ContenidoRepository** depende de **IFileHandler** (abstracción)

## Patrón MVC Implementado

### Modelo (Model)
- **model/**: Todas las clases de entidad y lógica de datos
- **service/**: Servicios de negocio y repositorios
- **interfaces/**: Contratos y abstracciones

### Vista (View)
- **view/MainView**: Interfaz principal con Swing
- **view/ContenidoDialog**: Diálogos para CRUD operations

### Controlador (Controller)
- **controller/ContenidoController**: Coordina entre vista y modelo
- Maneja eventos de la interfaz gráfica
- Delega operaciones a los servicios

## Tecnologías Utilizadas

- **Lenguaje**: Java 11+
- **Paradigma**: Programación Orientada a Objetos
- **Interfaz Gráfica**: Java Swing
- **Arquitectura**: Patrón MVC
- **Principios**: SOLID
- **Testing**: JUnit 5
- **Persistencia**: Archivos CSV
- **Patrones de Diseño**: Repository, Dependency Injection, Template Method

## Contribución

Este proyecto fue desarrollado como parte de una actividad educativa de POO. Las mejoras y extensiones son bienvenidas siguiendo los principios de diseño orientado a objetos.
---

**Desarrollado por**: Paul Crespo
**Curso**: Programación Orientada a Objetos  