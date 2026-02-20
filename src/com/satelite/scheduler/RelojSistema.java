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

public class RelojSistema extends Thread {
    private int cicloActual = 0;
    private int milisegundosPorCiclo = 1000; // 1 segundo por ciclo por defecto
    private boolean ejecutando = true;
    private VentanaPrincipal ventana; // Para avisarle a la GUI que cambie
    private ListaProcesos procesos;

    public RelojSistema(VentanaPrincipal ventana, ListaProcesos procesos) {
        this.ventana = ventana;
        this.procesos = procesos;
    }

    @Override
    public void run() {
        while (ejecutando) {
            try {
                // 1. Incrementar el reloj global
                cicloActual++;

                // 2. Simular ejecución (incrementar PC y MAR de los procesos en ejecución)
                actualizarProcesos();

                // 3. Actualizar la interfaz gráfica
                // Usamos SwingUtilities para que sea seguro actualizar la tabla desde otro hilo
                javax.swing.SwingUtilities.invokeLater(() -> {
                    ventana.actualizarInterfaz(cicloActual);
                });

                // 4. Pausa según la velocidad configurada
                Thread.sleep(milisegundosPorCiclo);
                
            } catch (InterruptedException e) {
                ejecutando = false;
            }
        }
    }

    private void actualizarProcesos() {
        Nodo actual = procesos.getCabeza();
        while (actual != null) {
            PCB p = actual.proceso;
            // Solo incrementamos PC y MAR si el proceso está en "Ejecución"
            if (p.estado.equals("Ejecucion")) {
                p.pc++;
                p.mar++;
            }
            actual = actual.siguiente;
        }
    }

    public void setVelocidad(int ms) {
        this.milisegundosPorCiclo = ms;
    }
}
