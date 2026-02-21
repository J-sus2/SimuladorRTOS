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
    
    public static void gestionarMemoria(ListaProcesos procesos) {
        int procesosEnRAM = 0;
        Nodo actual = procesos.getCabeza();

        // 1. Contar cuántos procesos están ocupando "RAM"
        while (actual != null) {
            if (actual.proceso.estado == EstadoProceso.LISTO || 
                actual.proceso.estado == EstadoProceso.EJECUCION || 
                actual.proceso.estado == EstadoProceso.BLOQUEADO) {
                procesosEnRAM++;
            }
            actual = actual.siguiente;
        }

        // 2. Si hay saturación, buscamos al menos crítico para suspenderlo
        if (procesosEnRAM > 10) { // Supongamos 10 como límite
            PCB menosCritico = null;
            int mayorDeadline = -1;

            actual = procesos.getCabeza();
            while (actual != null) {
                // Solo suspendemos los que están en LISTO (no el que está en CPU)
                if (actual.proceso.estado == EstadoProceso.LISTO) {
                    if (actual.proceso.deadline > mayorDeadline) {
                        mayorDeadline = actual.proceso.deadline;
                        menosCritico = actual.proceso;
                    }
                }
                actual = actual.siguiente;
            }

            if (menosCritico != null) {
                menosCritico.estado = EstadoProceso.LISTO_SUSPENDIDO;
                System.out.println("MEMORIA SATURADA: Suspendiendo " + menosCritico.nombre);
            }
        }
    }
}
