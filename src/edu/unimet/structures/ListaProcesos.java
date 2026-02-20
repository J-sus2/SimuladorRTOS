/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.unimet.structures;

/**
 *
 * @author jesus alejandro
 */
public class Nodo {
    PCB proceso;
    Nodo siguiente;

    public Nodo(PCB proceso) {
        this.proceso = proceso;
        this.siguiente = null;
    }
}

public class ListaProcesos {
    private Nodo cabeza;

    public void agregar(PCB p) {
        Nodo nuevo = new Nodo(p);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo temp = cabeza;
            while (temp.siguiente != null) {
                temp = temp.siguiente;
            }
            temp.siguiente = nuevo;
        }
    }

    public Nodo getCabeza() {
        return cabeza;
    }
}
