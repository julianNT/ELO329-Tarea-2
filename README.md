# Tarea 2: Simulador Gráfico de ELOTelTags y Aplicación Find My

Se simula graficamente la interacción entre dispositivos EloTelTags, celulares y tablets. Se utliza JavaFX y el programa muestra un mapa con el movimiento de los dispositivos.

## Instrucciones de Uso


### 1. Compilación y ejecución

El proyecto está diseñado para compilarse y ejecutarse de forma automatizada desde la terminal. Cada etapa de desarrollo (Stage) cuenta con su propio archivo `Makefile`.

Para ejecutar el simulador, abre la terminal, ingresa al directorio de la etapa que deseas evaluar y utiliza el comando de construcción. Por ejemplo, para la última etapa:

cd Stage4
make run

### 2. Generar documentación con Javadoc

Para generar la página web interactiva con la documentación, abre la terminal en el directorio del Stage correspondiente (idealmente en Stage4, que contiene la versión final de las clases) y ejecuta el siguiente comando:

javadoc -d doc *.java

Este proceso generará automáticamente una carpeta llamada doc. Para consultar la documentación, simplemente abre el archivo de índice en tu navegador de preferencia. Desde la terminal puedes lanzarlo directamente con:

xdg-open doc/index.html

### 3. Archivos

Se presentan carpetas la cuales contienen los archivos correspondientes a cada etapa:

#### Stage1:

- Stage1.java: inicializa la interfaz gráfica y carga la configuración desde config.txt
- Celullar.java
- CelullarView.java
- Equipo.java
- Territory.java
- TerritoryView.java
- config.txt: archivo de entrada
- Placeres.jpg: imagen del mapa

#### Stage2:

- Stage2.java: ejecuta una animación interactiva de los equipos en la simulación y añade controles de reproducción
- Celullar.java
- CelullarView.java
- EloTelTag.java
- EloTelTagView.java
- Equipo.java
- Tablet.java
- TabletView.java
- Territory.java
- TerritoryView.java
- config.txt: archivo de entrada
- Placeres.jpg: imagen del mapa

#### Stage3: 

- Stage3.java: integra ETNube y añade animaciones para actualizar posiciones en la consola
- Celullar.java
- CelullarView.java
- EloTelTag.java
- EloTelTagView.java
- Equipo.java
- ETNube.java
- Tablet.java
- TabletView.java
- Territory.java
- TerritoryView.java
- config.txt: archivo de entrada
- Placeres.jpg: imagen del mapa

#### Stage4:

- Stage4.java: integra todas las etapas anteriores
- Celullar.java
- CelullarView.java
- EloTelTag.java
- EloTelTagView.java
- Equipo.java
- ETNube.java
- Tablet.java
- TabletView.java
- Territory.java
- TerritoryView.java
- config.txt: archivo de entrada
- Placeres.jpg: imagen del mapa
