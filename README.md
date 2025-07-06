# Sistema de Gestión de Contenido Audiovisual

## Descripción del Proyecto

Este proyecto implementa un sistema completo de gestión de contenido audiovisual utilizando conceptos avanzados de Programación Orientada a Objetos (POO) en Java. El sistema permite gestionar diferentes tipos de contenido audiovisual como películas, series de TV, documentales, videos de YouTube y cortometrajes, junto con sus relaciones correspondientes.

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
poo_unidad1-master/
├── src/
│   ├── uni1a/
│   │   ├── ContenidoAudiovisual.java    # Clase abstracta base
│   │   ├── Pelicula.java                # Subclase para películas
│   │   ├── SerieDeTV.java               # Subclase para series
│   │   ├── Documental.java              # Subclase para documentales
│   │   ├── VideoYouTube.java            # Subclase para videos de YouTube
│   │   ├── Cortometraje.java            # Subclase para cortometrajes
│   │   ├── Actor.java                   # Clase de relación
│   │   ├── Temporada.java               # Clase de relación
│   │   └── Investigador.java            # Clase de relación
│   └── poo/
│       └── PruebaAudioVisual.java       # Clase principal de prueba
├── bin/                                 # Archivos compilados
├── guide.md                            # Guía del proyecto
└── README.md                           # Este archivo
```

## Instrucciones de Instalación y Uso

### Requisitos Previos
- Java Development Kit (JDK) 8 o superior
- Un IDE de Java (Eclipse, IntelliJ IDEA, VS Code) o terminal con javac

### Instalación

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/tu-usuario/poo_unidad1-master.git
   cd poo_unidad1-master
   ```

2. **Compilar el proyecto**:
   ```bash
   # Compilar todas las clases
   javac -d bin src/uni1a/*.java src/poo/*.java
   ```

3. **Ejecutar el programa**:
   ```bash
   java -cp bin poo.PruebaAudioVisual
   ```

### Uso del Sistema

El programa principal (`PruebaAudioVisual`) demuestra todas las funcionalidades del sistema:

1. **Creación de Contenido**: Instancia diferentes tipos de contenido audiovisual
2. **Gestión de Relaciones**: Agrega actores a películas, temporadas a series, e investigadores a documentales
3. **Demostración de Funcionalidades**: Muestra información detallada y funcionalidades específicas de cada clase

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
Género: Fantasy
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

## Tecnologías Utilizadas

- **Lenguaje**: Java 8+
- **Paradigma**: Programación Orientada a Objetos
- **Estructuras de Datos**: ArrayList, Arrays
- **Patrones de Diseño**: Template Method (método abstracto), Strategy (polimorfismo)

## Contribución

Este proyecto fue desarrollado como parte de una actividad educativa de POO. Las mejoras y extensiones son bienvenidas siguiendo los principios de diseño orientado a objetos.

## Licencia

Este proyecto es de uso educativo y está disponible bajo licencia MIT.

---

**Desarrollado por**: Paul Crespo
**Curso**: Programación Orientada a Objetos  