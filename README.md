========================================
          LABEL SCALES LOADER
========================================

Descripción:
-------------
Este es un programa desarrollado con Spring Boot diseñado para cargar archivos de etiquetas (.lbl)
desde un directorio configurado y enviarlos a balanzas identificadas mediante direcciones IP.
El programa coordina los siguientes componentes:

  - LabelService: Recupera los archivos de etiquetas desde un directorio especificado.
  - ScaleService: Obtiene la lista de direcciones IP de las balanzas a partir de la propiedad "ipScales".
  - SyncDataLoader: Realiza la carga de cada etiqueta en cada balanza a través de su IP.
  - LabelController: Orquesta la lógica de negocio, combinando la obtención de etiquetas y direcciones IP
    y delega la carga de etiquetas a las balanzas.

Características:
-----------------
- Lee archivos de etiquetas con extensión .lbl de un directorio configurado.
- Obtiene direcciones IP de las balanzas a través de la propiedad "ipScales" (lista separada por comas).
- Carga cada etiqueta en todas las balanzas disponibles.
- Registra (log) el progreso y los errores durante el proceso.
- Maneja excepciones y errores de forma controlada, registrándolos para su posterior análisis.

Configuración:
---------------
- Archivo de propiedades: application.properties
  * directory = [ruta_del_directorio_con_las_etiquetas] (valor por defecto: "sas")
  * ipScales = [lista_de_IPs] (ejemplo: 127.0.0.1,192.168.0.2)

Uso:
----
1. Construir el JAR:
   - Con Maven: ejecutar "mvn clean package" en la raíz del proyecto.
   - Se generará un archivo JAR ejecutable en el directorio "target".

2. Ejecutar el JAR:
   - Usar el siguiente comando:
     java -jar target/tu-nombre-de-aplicacion.jar

3. Funcionamiento:
   - Al iniciar, la aplicación cargará el contexto de Spring Boot y ejecutará el método loadLabels()
     del LabelController (implementado a través de CommandLineRunner o similar).
   - Se leerán los archivos de etiquetas y se enviarán a cada balanza configurada.
   - Los logs se podrán consultar para verificar el éxito del proceso o identificar errores.

Logs:
-----
- El programa utiliza SLF4J y Logback para la gestión de logs.
- Se pueden ajustar los niveles de log mediante la configuración en application.properties o en un archivo
  logback-spring.xml.
- Para evitar mensajes de depuración de paquetes internos (por ejemplo, sun.rmi), se recomienda elevar
  el nivel de log o desactivarlos según se requiera.

Notas Adicionales:
------------------
- Asegúrate de que el directorio configurado en "directory" exista y contenga archivos con extensión .lbl.
- Verifica que la propiedad "ipScales" contenga direcciones IP válidas, en formato IPv4, separadas por comas.
- Si se detectan errores en la validación de IP, el servicio ScaleService lanzará una excepción y registrará
  un mensaje de error.

Contacto:
---------
Para más información o en caso de incidencias, por favor contactar al equipo de desarrollo o consultar la
documentación interna del proyecto.

========================================
