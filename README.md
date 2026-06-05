# FitGIt — Aplicación Android de Seguimiento de Entrenamiento

Proyecto de fin de ciclo del Grado Superior en Desarrollo de Aplicaciones Multiplataforma (DAM).

## ¿Qué es FitGIt?

FitGIt es una aplicación Android nativa desarrollada en Java que permite al usuario gestionar sus rutinas de ejercicio, registrar entrenamientos con pesos y repeticiones, y visualizar su progreso a lo largo del tiempo.

La aplicación se conecta a una API externa de ejercicios (ExerciseDB) para obtener un catálogo con GIFs e instrucciones, los traduce automáticamente al español con Google Cloud Translation y los guarda localmente con Room para funcionar sin conexión.

---

## Funcionalidades principales

- Registro e inicio de sesión con email/contraseña y con Google
- Catálogo de ejercicios con GIFs, filtros por músculo y buscador
- Creación y gestión de rutinas personalizadas
- Entrenamiento activo con cronómetro y registro de series (kg + repeticiones)
- Historial de entrenamientos con opción de eliminar sesiones o ejercicios individuales
- Gráfica de evolución por ejercicio y resumen semanal de días entrenados
- Perfil de usuario con foto (almacenada en Supabase), cambio de nombre, modo oscuro e idioma
- Sincronización en la nube con Firebase Firestore
- Soporte completo en español e inglés

---

## Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Java 11 | Lenguaje principal |
| Room 2.6.1 | Base de datos local (SQLite) |
| LiveData + ViewModel | Datos reactivos con ciclo de vida |
| Navigation Component 2.7.7 | Navegación entre fragments |
| Retrofit 2 | Peticiones a la API REST |
| Glide 4.16.0 | Carga de imágenes y GIFs |
| Firebase Auth | Autenticación de usuarios |
| Firebase Firestore | Sincronización de datos en la nube |
| Supabase Storage | Almacenamiento de fotos de perfil |
| Google Cloud Translation | Traducción automática de ejercicios |
| MPAndroidChart 3.1.0 | Gráfica de evolución de peso |
| Material Components 1.12.0 | UI (chips, tarjetas, diálogos, bottom sheets) |
| OkHttp | Interceptor de cabeceras y subida de imágenes |

---

## Arquitectura

El proyecto sigue el patrón **MVVM (Model-View-ViewModel)** con capa de Repository, tal y como recomienda Google para aplicaciones Android:

```
UI (Fragments / Activities)
         │
    ViewModel
         │
    Repositorio
         │
  DAO (Room) ──── Retrofit (API) ──── Firestore
         │
  Base de datos local
```

Los fragmentos observan `LiveData` del ViewModel. El ViewModel llama al Repositorio. El Repositorio decide si usar los datos locales de Room o hacer una petición a la API o a Firestore.

---

## Estructura del proyecto

```
com.example.fitgit
│
├── adapter/         # Adaptadores RecyclerView (ejercicios, rutinas, series, historial)
├── api/             # Cliente Retrofit + servicios de ejercicios y traducción
├── database/        # AppDatabase (Room), DAOs y conversor de tipos
├── model/           # Entidades Room y POJOs de consultas
├── repository/      # Repositorios que conectan ViewModel con Room/Retrofit/Firestore
├── ui/              # Activities, Fragments y BottomSheets
├── util/            # SwipeEliminarCallback y TraductorLocal
├── viewmodel/       # EjercicioViewModel, RutinaViewModel y SesionViewModel
└── FitGitApp.java   # Application: aplica tema e idioma al arrancar
```

---

## Servicios externos

| Servicio | Función |
|---|---|
| [ExerciseDB (RapidAPI)](https://rapidapi.com/justin-WFnsXH_t6/api/exercisedb) | Catálogo de ejercicios con GIFs e instrucciones |
| [Google Cloud Translation](https://cloud.google.com/translate) | Traducción automática al español |
| [Firebase Auth](https://firebase.google.com/products/auth) | Autenticación de usuarios |
| [Firebase Firestore](https://firebase.google.com/products/firestore) | Sincronización de datos en la nube |
| [Supabase Storage](https://supabase.com/storage) | Almacenamiento de fotos de perfil |

---

## Configuración para compilar

Las claves de API no están incluidas en el repositorio. Para compilar el proyecto es necesario crear el archivo `local.properties` en la raíz del proyecto con estas claves:

```
RAPIDAPI_KEY=tu_clave_aqui
GOOGLE_TRANSLATE_KEY=tu_clave_aqui
SUPABASE_URL=tu_url_aqui
SUPABASE_KEY=tu_clave_aqui
```

También es necesario añadir el archivo `google-services.json` de Firebase en la carpeta `app/`.

---

## Requisitos

- Android Studio Hedgehog o superior
- Android SDK 34 (compileSdk)
- Mínimo Android 7.0 (minSdk 24)
