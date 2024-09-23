#  📚 Enseñarte: Prototipo Funcional para la Enseñanza, Aprendizaje y Evaluación de la Lengua de Señas Colombiana (LSC) 📚

## Descripción

Enseñarte es una aplicación móvil desarrollada para facilitar la enseñanza, el aprendizaje y la evaluación de la Lengua de Señas Colombiana (LSC). La aplicación está dirigida principalmente a personas oyentes interesadas en aprender esta lengua, y utiliza tecnologías emergentes como Firebase para autenticación, almacenamiento de datos y reconocimiento de gestos.

La aplicación integra elementos de **gamificación** y **reconocimiento de video** para proporcionar una experiencia interactiva, motivadora y accesible para sus usuarios. Los principales módulos de la aplicación son: un **diccionario de señas**, un **sistema de aprendizaje basado en niveles** y un **módulo de evaluación de competencias**.

## Características Principales

- **Diccionario de Señas**: Acceso a un conjunto de señas seleccionadas del diccionario básico de la LSC, con videos demostrativos.
- **Lecciones Gamificadas**: Aprendizaje guiado con desafíos y ejercicios interactivos que permiten avanzar por niveles.
- **Evaluación de Aprendizaje**: Evaluación automática de los gestos a través del reconocimiento de video utilizando **TensorFlow Lite**.
- **Autenticación**: Integración con **Firebase Auth** para la gestión de usuarios.
- **Almacenamiento de Datos**: **Firestore** es utilizado para gestionar el almacenamiento y recuperación de datos.
- **Subida de Videos**: Los videos se almacenan en **Firebase Storage** para su análisis posterior.

## Requisitos del Sistema

- **Plataforma**: Android 7.0 (Nougat) o superior
- **Cámara**: Cámara frontal con resolución mínima de 1280x720 para la correcta captura de gestos.
- **Conexión a Internet**: Requerida para la autenticación de usuarios y la sincronización de datos con Firebase.

## Instalación

1. Clonar el repositorio del proyecto:
   ```bash
   git clone https://github.com/tuusuario/enseñarte-lsc.git
2. Abrir el proyecto en Android Studio.
3. Configurar el proyecto con las credenciales de Firebase:
   - Crear un proyecto en Firebase.
   - Descargar el archivo `google-services.json` y colocarlo en el directorio `app/` del proyecto.
4. Ejecutar el proyecto en un dispositivo Android.

## Arquitectura

El proyecto sigue una arquitectura monolítica donde la aplicación móvil Android se comunica directamente con Firebase para la autenticación, almacenamiento de datos y subida de videos. A continuación, una descripción básica de los principales componentes de la arquitectura:

- **Firebase Auth**: Maneja la autenticación de usuarios.
- **Firestore**: Almacena la información de los usuarios y el progreso de aprendizaje.
- **Firebase Storage**: Almacena los videos que los usuarios suben para la evaluación de señas.
- **TensorFlow Lite**: Se utiliza para el reconocimiento de gestos dinámicos a partir de los videos capturados.

![Arquitectura](https://media.discordapp.net/attachments/1182055171723903089/1282056332685475952/image.png?ex=66ddf781&is=66dca601&hm=cb6bd0c7b2620ab76cadaf9dc37f487ca3f218e83c8119e2aea9236e58aa7865&=&format=webp&quality=lossless&width=1103&height=602)

## Uso

1. **Registro e Inicio de Sesión**: Los usuarios pueden crear una cuenta o iniciar sesión utilizando **Firebase Auth**.
2. **Exploración del Diccionario**: Los usuarios pueden navegar por el diccionario de señas y ver videos de cada una.
3. **Lecciones y Niveles**: Los usuarios avanzan por niveles completando desafíos interactivos y gamificados.
4. **Evaluación de Señales**: Los usuarios pueden practicar las señas y recibir retroalimentación en tiempo real gracias al sistema de reconocimiento de video.

## Tecnologías Utilizadas

- **Lenguajes de Programación**: Java/Kotlin para el desarrollo en Android.
- **TensorFlow Lite**: Para el reconocimiento de gestos dinámicos.
- **Firebase**:
  - **Firebase Auth**: Autenticación de usuarios.
  - **Firestore**: Base de datos en tiempo real para almacenar progreso y datos de usuarios.
  - **Firebase Storage**: Almacenamiento de videos.

## Contribuciones

Si deseas contribuir a este proyecto, por favor sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama (`git checkout -b feature/tu-nueva-funcionalidad`).
3. Haz commit de tus cambios (`git commit -am 'Añadir nueva funcionalidad'`).
4. Haz push a la rama (`git push origin feature/tu-nueva-funcionalidad`).
5. Crea un nuevo **Pull Request**.

## Licencia

Este proyecto está licenciado bajo la **Licencia MIT**. Consulta el archivo [LICENSE](LICENSE) para más detalles.
