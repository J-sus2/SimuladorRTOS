/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.satelite.scheduler;

/**
 *
 * @author jesus alejandro
 */

import com.satelite.gui.VentanaPrincipal;
import com.satelite.structures.ListaProcesos;
import com.satelite.model.PCB;
import com.satelite.structures.Nodo;
import javax.swing.SwingUtilities;

public class RelojSistema extends Thread {
    private int cicloActual = 0;
    private int milisegundosPorCiclo = 1000;
    private boolean ejecutando = true;
    private VentanaPrincipal ventana;
    private ListaProcesos procesos;

    public RelojSistema(VentanaPrincipal ventana, ListaProcesos procesos) {
        this.ventana = ventana;
        this.procesos = procesos;
    }

    @Override
    public void run() {
        while (ejecutando) {
            try {
                cicloActual++;

                // ORDEN DE OPERACIONES:
                // 1. Manejar los que están esperando E/S
                Planificador.gestionarBloqueados(procesos);

                // 2. Revisar si hay que suspender procesos por falta de RAM
                Planificador.gestionarMemoria(procesos);

                // 3. Elegir quién usa la CPU (Algoritmo EDF)
                Planificador.aplicarEDF(procesos); 

                // 4. Ejecutar físicamente el proceso en CPU
                actualizarProcesosEnEjecucion();

                SwingUtilities.invokeLater(() -> {
                    ventana.actualizarInterfaz(cicloActual);
                });

                Thread.sleep(milisegundosPorCiclo);
            } catch (InterruptedException e) {
                ejecutando = false;
            }
        }
    }

    private void actualizarProcesosEnEjecucion() {
        Nodo actual = procesos.getCabeza();
        while (actual != null) {
            PCB p = actual.proceso;
            if (p.estado.equals("Ejecucion")) {
                p.pc++;
                p.mar++;
                p.instruccionesEjecutadas++;
                if (p.instruccionesEjecutadas >= p.totalInstrucciones) {
                    p.estado = "Terminado";
                }
            }
            actual = actual.siguiente;
        }
    }
}