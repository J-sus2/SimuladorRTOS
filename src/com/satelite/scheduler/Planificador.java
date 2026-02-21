/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.satelite.scheduler;

/**
 *
 * @author jesus alejandro
 */

import com.satelite.model.PCB;
import com.satelite.model.EstadoProceso;
import com.satelite.structures.ListaProcesos;
import com.satelite.structures.Nodo;

public class Planificador {
    
    // Algoritmo EDF: El que tenga el deadline más cercano (menor número) va primero
    public static void aplicarEDF(ListaProcesos procesos) {
        Nodo actual = procesos.getCabeza();
        PCB candidato = null;
        int menorDeadline = Integer.MAX_VALUE;

        // 1. Buscamos si ya hay alguien en Ejecución
        while (actual != null) {
            if (actual.proceso.estado == EstadoProceso.EJECUCION) {
                return; // Si ya hay alguien corriendo, no hacemos nada (por ahora)
            }
            actual = actual.siguiente;
        }

        // 2. Si no hay nadie en CPU, buscamos el "Listo" con el deadline más corto
        actual = procesos.getCabeza();
        while (actual != null) {
            if (actual.proceso.estado == EstadoProceso.LISTO) {
                if (actual.proceso.deadline < menorDeadline) {
                    menorDeadline = actual.proceso.deadline;
                    candidato = actual.proceso;
                }
            }
            actual = actual.siguiente;
        }

        // 3. Asignamos la CPU al ganador
        if (candidato != null) {
            candidato.estado = EstadoProceso.EJECUCION;
        }
    }
}
