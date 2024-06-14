/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sogo.malba.soapcliente;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import corp.capsule.DragonBallService;
import corp.capsule.DragonBallServiceService;
import corp.capsule.Personaje;
import java.util.Collections;

import java.util.List;
import java.util.Random;


public class Controlador {

    @FXML
    private TextField nameField;

    @FXML
    private TextArea resultArea;

    @FXML
    private Button getButton;
    
    @FXML
    private Button randomButton;
    
    private List<Personaje> personajes;
   

    @FXML
    private void initialize() {
        getButton.setOnAction(event -> getPersonaje());
        randomButton.setOnAction(event -> getPersonajeAleatorio());

        try {
            DragonBallServiceService service = new DragonBallServiceService();
            DragonBallService port = service.getDragonBallServicePort();
            personajes = port.obtenerTodosLosPersonajes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPersonaje() {
        try {
            DragonBallServiceService service = new DragonBallServiceService();
            DragonBallService port = service.getDragonBallServicePort();
            
            String nombre = nameField.getText();
            Personaje personaje = port.obtenerPersonajePorNombre(nombre);

            if (personaje != null) {
                resultArea.setText("Nombre: " + personaje.getNombre() + "\n"
                        + "Tipo: " + personaje.getTipo() + "\n"
                        + "Poder: " + personaje.getPoder() + "\n"
                        + "Nivel de Poder: " + personaje.getNivelDePoder());
            } else {
                resultArea.setText("Personaje no encontrado");
            }

        } catch (Exception e) {
            resultArea.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void getPersonajeAleatorio() {
        if (personajes != null && !personajes.isEmpty()) {
            Collections.shuffle(personajes);
            Random random = new Random();
            Personaje personajeAleatorio = personajes.get(random.nextInt(personajes.size()));
            resultArea.setText("Nombre: " + personajeAleatorio.getNombre() + "\n"
                    + "Tipo: " + personajeAleatorio.getTipo() + "\n"
                    + "Poder: " + personajeAleatorio.getPoder() + "\n"
                    + "Nivel de Poder: " + personajeAleatorio.getNivelDePoder());
        } else {
            resultArea.setText("No hay personajes disponibles.");
        }
    }
}
