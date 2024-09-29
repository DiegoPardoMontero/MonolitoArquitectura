#  Taller Monolito Arquitectura 🖥️📚📕
## Diccionario de Lengua de Señas Colombiana (LSC)

Este es un proyecto de una aplicación móvil monolítica para Android que sirve como diccionario interactivo de la Lengua de Señas Colombiana (LSC). La aplicación está clasificada por categorías para facilitar la búsqueda de palabras y su respectiva traducción en lengua de señas a través del uso de videos.

## Estructura del Proyecto

El proyecto sigue la estructura típica de un proyecto Android utilizando Gradle como herramienta de construcción. A continuación se describen las carpetas y archivos principales:

### /app/
Contiene el código fuente de la aplicación, dividido en módulos y funcionalidades:

- Actividades y Fragmentos: Controlan las pantallas y la lógica de la interfaz de usuario.
- Adapters: Facilitan la visualización y navegación a través de las listas de palabras y categorías.
- Data: Contiene la lógica para manejar las palabras y las categorías almacenadas localmente utilizando SQLite. Así mismo, especifica las entidades y modelos de datos, como Palabra y Categoria.
  
### /gradle/ y /gradle/wrapper/
Carpeta que contiene los archivos necesarios para la configuración de Gradle y las dependencias del proyecto. Asegura que el proyecto se pueda construir de manera consistente en diferentes entornos.

### .gitignore
Este archivo especifica qué archivos y directorios deben ser ignorados por Git, asegurando que no se suban archivos innecesarios como los de configuración de Android Studio o compilaciones locales.

### README.md
Este archivo, donde se describe el proyecto, su estructura y cómo colaborar.

### build.gradle.kts
Archivo de configuración principal de Gradle en formato Kotlin Script. Define las dependencias del proyecto y las tareas de construcción.

## Colaboradores

Este proyecto es mantenido por los siguientes colaboradores:

- [ValEscoSierra](https://github.com/ValEscoSierra)
- [DiegoPardoMontero](https://github.com/DiegoPardoMontero)

## Dependencias y Librerías

El proyecto utiliza las siguientes tecnologías y librerías:

- Glide: Para la carga y visualización de imágenes y videos dentro de la aplicación.
- SQLite: Para el almacenamiento local de palabras y categorías.
- Kotlin: Lenguaje principal de la aplicación.
- Android Architecture Components: Como ViewModel y LiveData para la gestión de datos en la interfaz de usuario.

## Instalación y Configuración

### Requisitos Previos

- Android Studio (versión recomendada: Arctic Fox o superior)
- Gradle 6.5 o superior
- Dispositivo Android (o emulador) con Android 6.0 (API nivel 23) o superior.

### Clonar el Proyecto

Para clonar este proyecto, utiliza el siguiente comando:

```
git clone https://github.com/DiegoPardoMontero/MonolitoArquitectura.git
```

### Compilar el Proyecto

Una vez clonado el repositorio, abre el proyecto en Android Studio y sincroniza Gradle para descargar todas las dependencias necesarias:

1. Abre Android Studio.
2. Selecciona "Open an Existing Project" y navega hasta el directorio clonado.
3. Espera a que Gradle sincronice las dependencias.
4. Una vez finalizado, selecciona tu dispositivo/emulador y haz clic en el botón "Run" para compilar e instalar la aplicación.

## Funcionalidades Principales

1. Diccionario Clasificado por Categorías: Los usuarios pueden navegar por diferentes categorías para encontrar palabras específicas en la Lengua de Señas Colombiana.
   
2. Visualización de Videos: Para cada palabra, la aplicación muestra videos explicativos, que incluyen:
   - Video de la Palabra: Muestra cómo se realiza la seña.
   - Video de la Definición: Explicación visual de la palabra en contexto.
   - Video de Ejemplo: Uso de la palabra en una oración.

3. Búsqueda de Palabras: Los usuarios pueden buscar directamente palabras y acceder a sus traducciones en lengua de señas.

4. Almacenamiento Local: La aplicación utiliza SQLite para almacenar localmente las palabras y sus videos, permitiendo su uso offline.

## Contribuir al Proyecto

Si deseas contribuir al desarrollo del proyecto, sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama para tu funcionalidad o corrección de error:

### Compilar el Proyecto

Una vez clonado el repositorio, abre el proyecto en Android Studio y sincroniza Gradle para descargar todas las dependencias necesarias:

1. Abre Android Studio.
2. Selecciona "Open an Existing Project" y navega hasta el directorio clonado.
3. Espera a que Gradle sincronice las dependencias.
4. Una vez finalizado, selecciona tu dispositivo/emulador y haz clic en el botón "Run" para compilar e instalar la aplicación.

## Funcionalidades Principales

1. Diccionario Clasificado por Categorías: Los usuarios pueden navegar por diferentes categorías para encontrar palabras específicas en la Lengua de Señas Colombiana.
   
2. Visualización de Videos: Para cada palabra, la aplicación muestra videos explicativos, que incluyen:
   - Video de la Palabra: Muestra cómo se realiza la seña.
   - Video de la Definición: Explicación visual de la palabra en contexto.
   - Video de Ejemplo: Uso de la palabra en una oración.

3. Búsqueda de Palabras: Los usuarios pueden buscar directamente palabras y acceder a sus traducciones en lengua de señas.

4. Almacenamiento Local: La aplicación utiliza SQLite para almacenar localmente las palabras y sus videos, permitiendo su uso offline.
