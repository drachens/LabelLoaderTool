package com.marsol.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.regex.Pattern;

public class ConfiguracionPropertiesApp extends Application {

    // Patrón para validar direcciones IPv4
    private static final Pattern IPV4_PATTERN = Pattern.compile(
            "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$"
    );

    private TextField directoryField;
    private Label directoryErrorLabel;
    private TextField scalesField;
    private Label scalesErrorLabel;
    private TextArea logConsole;
    private Button iniciarButton;
    private Button detenerButton;
    private Button cerrarButton;

    private boolean started = false;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Configuración de Properties");

        // --- Componente: Directorio ---
        Label directoryLabel = new Label("Directorio:");
        directoryField = new TextField();
        directoryField.setPromptText("Ingrese el directorio...");
        directoryErrorLabel = new Label();
        directoryErrorLabel.setTextFill(Color.RED);
        VBox directoryBox = new VBox(5, directoryLabel, directoryField, directoryErrorLabel);

        // --- Componente: Lista de Balanzas (IPs) ---
        Label scalesLabel = new Label("Lista de Balanzas (IPs separadas por coma):");
        scalesField = new TextField();
        scalesField.setPromptText("Ingrese las IPs, separadas por coma...");
        scalesErrorLabel = new Label();
        scalesErrorLabel.setTextFill(Color.RED);
        VBox scalesBox = new VBox(5, scalesLabel, scalesField, scalesErrorLabel);

        // --- Componente: Consola de Logs ---
        Label consoleLabel = new Label("Consola de Logs:");
        logConsole = new TextArea();
        logConsole.setEditable(false);
        logConsole.setPrefHeight(150);
        VBox consoleBox = new VBox(5, consoleLabel, logConsole);

        // --- Botones ---
        iniciarButton = new Button("INICIAR");
        detenerButton = new Button("DETENER");
        cerrarButton = new Button("CERRAR");
        detenerButton.setDisable(true);  // Solo se habilita al iniciar

        HBox buttonsBox = new HBox(10, iniciarButton, detenerButton, cerrarButton);
        buttonsBox.setAlignment(Pos.CENTER);

        // --- Layout Principal ---
        VBox root = new VBox(15, directoryBox, scalesBox, consoleBox, buttonsBox);
        root.setPadding(new Insets(15));

        // --- Eventos ---
        iniciarButton.setOnAction(e -> iniciarAccion());
        detenerButton.setOnAction(e -> detenerAccion());
        cerrarButton.setOnAction(e -> Platform.exit());

        // Validar a medida que se ingresan datos
        directoryField.textProperty().addListener((obs, oldVal, newVal) -> validateDirectory());
        scalesField.textProperty().addListener((obs, oldVal, newVal) -> validateScales());

        Scene scene = new Scene(root, 500, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Valida que el texto ingresado en el campo de directorio corresponda a un directorio válido.
     */
    private boolean validateDirectory() {
        String dirText = directoryField.getText().trim();
        if (dirText.isEmpty() || !new File(dirText).isDirectory()) {
            directoryErrorLabel.setText("El directorio no es válido.");
            return false;
        } else {
            directoryErrorLabel.setText("");
            return true;
        }
    }

    /**
     * Valida que el campo de balanzas contenga una lista de IPs válidas.
     */
    private boolean validateScales() {
        String scalesText = scalesField.getText().trim();
        if (scalesText.isEmpty()) {
            scalesErrorLabel.setText("La lista de balanzas no puede estar vacía.");
            return false;
        }
        String[] ips = scalesText.split(",");
        for (String ip : ips) {
            String trimmedIp = ip.trim();
            if (!IPV4_PATTERN.matcher(trimmedIp).matches()) {
                scalesErrorLabel.setText("IP inválida: " + trimmedIp);
                return false;
            }
        }
        scalesErrorLabel.setText("");
        return true;
    }

    /**
     * Acción del botón INICIAR: valida los campos, inicia el proceso y habilita el botón DETENER.
     */
    private void iniciarAccion() {
        boolean validDir = validateDirectory();
        boolean validScales = validateScales();
        if (!validDir || !validScales) {
            appendLog("ERROR: Corrija los errores antes de iniciar.");
            return;
        }

        // Aquí podrías guardar los valores en el archivo .properties o iniciar otros procesos
        appendLog("INFO: Iniciando la aplicación...");
        started = true;
        iniciarButton.setDisable(true);
        detenerButton.setDisable(false);
    }

    /**
     * Acción del botón DETENER: detiene el proceso y actualiza la UI.
     */
    private void detenerAccion() {
        if (!started) return;
        appendLog("INFO: Deteniendo la aplicación...");
        started = false;
        iniciarButton.setDisable(false);
        detenerButton.setDisable(true);
    }

    /**
     * Agrega un mensaje a la consola de logs.
     */
    private void appendLog(String message) {
        logConsole.appendText(message + "\n");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
