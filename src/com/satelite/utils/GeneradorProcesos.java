/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.satelite.utils;

/**
 *
 * @author jesus alejandro
 */
import com.satelite.model.PCB;
import com.satelite.structures.ListaProcesos;
import java.util.Random;

public class GeneradorProcesos {
    private static int contadorID = 1;
    private static Random random = new Random();

    public static void generar20Aleatorios(ListaProcesos lista) {
        for (int i = 0; i < 20; i++) {
            // Datos aleatorios para simular el microsatélite
            int instrucciones = 10 + random.nextInt(50); // de 10 a 60 instrucciones
            int prioridad = 1 + random.nextInt(5);      // prioridad 1 (alta) a 5 (baja)
            int deadline = 100 + random.nextInt(200);   // deadline entre 100 y 300 ciclos
            
            PCB nuevo = new PCB(contadorID, "SatTask-" + contadorID, prioridad, deadline);
            nuevo.totalInstrucciones = instrucciones; // Asumiendo que añadiste este campo al PCB
            
            lista.agregar(nuevo);
            contadorID++;
        }
    }
}
