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

public class InterrupcionEvent extends Thread {
    private ListaProcesos lista;

    public InterrupcionEvent(ListaProcesos lista) {
        this.lista = lista;
    }

    @Override
    public void run() {
        // Simulamos que la interrupción tarda un poco en procesarse
        System.out.println("INTERRUPCIÓN: Impacto detectado, suspendiendo CPU...");
        
        // Buscamos al que está en ejecución y lo mandamos a Listo para atender la emergencia
        Nodo actual = lista.getCabeza();
        while (actual != null) {
            if (actual.proceso.estado == EstadoProceso.EJECUCION) {
                actual.proceso.estado = EstadoProceso.LISTO; // Suspensión inmediata
                break;
            }
            actual = actual.siguiente;
        }
        
        // Creamos la tarea de emergencia con prioridad máxima
        PCB emergencia = new PCB(999, "EMERGENCIA-S01", 1, 10, 5); 
        emergencia.estado = EstadoProceso.EJECUCION; // Toma la CPU por asalto
        lista.agregar(emergencia);
    }
}
