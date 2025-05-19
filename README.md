# TSDC-Vinilos-moviles

Aplicación Android para la consulta y administración de álbumes musicales, desarrollada en Kotlin siguiendo la arquitectura MVVM.

---

## 👀 Pre-requisitos generales

Antes de correr la app, necesitas:

- Tener instalado [Android Studio](https://developer.android.com/studio) **o** [IntelliJ IDEA](https://www.jetbrains.com/idea/) (versión Ultimate o Community con plugins de Android).
- Tener instalado [Docker](https://docs.docker.com/get-docker/) y [Docker Compose](https://docs.docker.com/compose/install/).
- Git instalado para clonar los repositorios.


## 🚀 Paso 1: Clonar los repositorios

### Clonar el repositorio de la API REST (Backend)

```bash
git clone https://github.com/jameshell/BackVynilsModificado
```

### Clonar el repositorio de la app móvil (este repositorio)

```bash
git clone https://github.com/jposoriob1/TSDC-Vinilos-moviles.git
```


---

## 🧪 Paso 2: Levantar el Backend

### Instalar Docker y Docker Compose (si no los tienes)

- **Windows/Mac**: instala Docker Desktop que ya incluye Docker Compose.
- **Linux**:

```bash
sudo apt update
sudo apt install docker.io docker-compose -y
sudo systemctl start docker
sudo systemctl enable docker
```

Verifica que Docker y Docker Compose funcionan:

```bash
docker --version
docker-compose --version
```

### Correr la API REST

Desde la carpeta donde clonaste `BackVynils`:

```bash
cd BackVynils
docker-compose up
```

Esto levantará todos los servicios necesarios (API, base de datos, etc.). ¡Debes dejarlo corriendo mientras usas la app!

La API debería estar disponible en: `http://localhost:3000/albums`


---

## 🔧 Paso 3: Correr la app móvil en Android Studio o IntelliJ

### Android Studio

1. Abre **Android Studio**.
2. Selecciona **"Open"** y busca la carpeta donde clonaste `TSDC-Vinilos-moviles`.
3. Deja que gradle sincronice el proyecto.
4. Asegúrate de tener un emulador creado o un dispositivo físico conectado.
5. Dale click en **Run ▶️**.

### IntelliJ IDEA

1. Abre **IntelliJ IDEA**.
2. Selecciona **"Open"** y abre la carpeta `TSDC-Vinilos-moviles`.
3. Instala los plugins de Android si IntelliJ te los solicita.
4. Acepta la configuración de Gradle y espera que sincronice.
5. Corre la app como una aplicación Android.

> Si ves errores de configuración en IntelliJ, asegúrate de tener configurado el SDK de Android (lo puedes instalar desde File > Project Structure > SDKs).


---

## 🚜 Carpeta apk/ y uso del APK

Dentro de la carpeta apk/ esta carpeta encontrarás el archivo:

app-debug.apk

Este APK ya está compilado y listo para ser instalado.

🚀 Cómo instalar el APK en un emulador o dispositivo físico
Opción 1: Usando Android Emulator

- Opción 1: Abre el emulador en Android Studio.

  Simplemente arrastra y suelta el archivo app-debug.apk dentro de la ventana del emulador.

  ¡La aplicación se instalará automáticamente!

- Opción 2: Usando ADB (Android Debug Bridge)

  Conecta tu dispositivo Android (o usa un emulador abierto).

  Abre una terminal.

  Corre el siguiente comando (ajustando la ruta a donde tengas el APK):



  ```bash
  adb install apk/app-debug.apk
  ```
  Nota: Asegúrate de tener activado el Modo Desarrollador y la Depuración USB en tu dispositivo si usas un celular físico.


---

# 📄 Resumen Rápido

| Paso | Qué hacer |
|:----:|:--------|
| 1 | Clonar `BackVynils` y `TSDC-Vinilos-moviles` |
| 2 | Levantar la API REST con Docker Compose |
| 3 | Abrir proyecto en Android Studio/IntelliJ |
| 4 | Ejecutar la app o instalar el APK |

---


> Si tienes algún error, revisa los logs de Docker y de Android Studio. También asegúrate de que la API REST esté corriendo y disponible en `localhost:3000`.

