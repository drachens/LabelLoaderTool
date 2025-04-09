
# LABEL SCALES LOADER


Description:
-------------
This is a program developed with Spring Boot that is designed to load label files (.lbl) from a configured directory and send them to scales identified by IP addresses. The program coordinates the following components:

  - LabelService: Retrieves the label files from a directory specified in the application.properties configuration file.
  - ScaleService: Obtains the list of scale IP addresses from the "ipScales" property set in application.properties.
  - SyncDataLoader: Loads each label onto each scale via its IP address.
  - LabelController: Orchestrates the business logic by combining the retrieval of labels and IP addresses, and delegates the label loading to the scales.

Features:
-----------------
  - Reads .lbl label files from a configured directory.
  - Retrieves scale IP addresses through the "ipScales" property (comma-separated).
  - Loads each label onto all available scales.
  - Logs progress and errors during the process.
  - Handles exceptions and errors in a controlled manner, logging them for further analysis.

Configuration:
---------------
  - Properties file: application.properties
    ```bash
    directory = [path_to_label_directory] (for example: C:\Users\UserTest\LabelDirectory\)
    ipScales = [list_of_IPs] (for example: 127.0.0.1,192.168.0.2)
    ```

Usage:
----
1. Build the JAR:
    - With Maven: run "mvn clean package" in the project root.
    - An executable JAR file will be generated in the target directory.

2. Run the JAR:
   - Use the following command:
     ```bash
     java -jar target/application-name-example.jar
     ```
     
3. How it works:
    - Upon startup, the application will load the Spring Boot context and execute the loadLabels() method
      of LabelController (implemented via CommandLineRunner or similar).
    - It will read the label files and send them to each configured scale.
    - Logs can be checked to verify the process's success or to identify errors.  

Logs:
-----
  - The program uses SLF4J and Logback for log management.
  - Log levels can be adjusted via the configuration in application.properties or in a logback.xml file.

Additional Notes:
------------------
  - Make sure the directory configured in "directory" exists and contains files with the .lbl extension.
  - Verify that the "ipScales" property contains valid IP addresses in IPv4 format, separated by commas.
  - Ensure you have installed JDK version 17.
  - If errors are detected during IP validation, the ScaleService will throw an exception and log an error message.

Contact:
---------
For more information or in case of issues, please contact the development team.

========================================
