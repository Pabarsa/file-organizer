# File Organizer

Organiza automáticamente una carpeta moviendo cada archivo a subcarpetas por tipo: imágenes, documentos, vídeo, audio, comprimidos y otros.

## Categorías

| Carpeta       | Extensiones                                      |
|---------------|--------------------------------------------------|
| Imagenes      | jpg, jpeg, png, gif, webp, bmp, svg              |
| Documentos    | pdf, doc, docx, txt, odt, xls, xlsx, ppt, pptx  |
| Video         | mp4, mkv, avi, mov, webm                         |
| Audio         | mp3, wav, flac, ogg, m4a                         |
| Comprimidos   | zip, rar, 7z, tar, gz                            |
| Otros         | cualquier extensión no contemplada               |

Si ya existe un archivo con el mismo nombre en el destino, se renombra añadiendo un sufijo numérico (`foto.jpg` → `foto_1.jpg`). Las subcarpetas creadas por la herramienta nunca se re-organizan en ejecuciones sucesivas.

## Requisitos

- JDK 11 o superior

## Compilación

```bash
javac -d out src/organizer/*.java
```

## Uso

```bash
java -cp out organizer.Main <ruta>
```

### Modo simulación (`--dry-run`)

Muestra qué movería sin mover nada. Útil para revisar antes de ejecutar.

```bash
java -cp out organizer.Main <ruta> --dry-run
```

## Ejemplos

Organizar la carpeta `Descargas`:

```bash
java -cp out organizer.Main /home/pablo/Descargas
```

```
  Movido:    informe_2024.pdf     →  /home/pablo/Descargas/Documentos/informe_2024.pdf
  Movido:    vacaciones.jpg       →  /home/pablo/Descargas/Imagenes/vacaciones.jpg
  Movido:    cancion.mp3          →  /home/pablo/Descargas/Audio/cancion.mp3

Organizados 3 archivo(s).
```

Ver qué haría antes de ejecutar:

```bash
java -cp out organizer.Main /home/pablo/Descargas --dry-run
```

```
Modo simulación — no se moverá ningún archivo.

  [dry-run]  informe_2024.pdf     →  /home/pablo/Descargas/Documentos/informe_2024.pdf
  [dry-run]  vacaciones.jpg       →  /home/pablo/Descargas/Imagenes/vacaciones.jpg
  [dry-run]  cancion.mp3          →  /home/pablo/Descargas/Audio/cancion.mp3

Se moveran 3 archivo(s).
```

## Nota para Windows

Si ves caracteres extraños en la salida, ejecuta esto antes en la misma terminal:

```cmd
chcp 65001
```
