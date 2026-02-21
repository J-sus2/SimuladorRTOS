/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.satelite.model;

/**
 *
 * @author jesus alejandro
 */

public class PCB {
    public int id;
    public String nombre;
    public String estado;
    public int pc;
    public int mar;
    public int prioridad;
    public int deadline;
    public int totalInstrucciones; // <--- ASEGÚRATE DE QUE ESTO ESTÉ AQUÍ

    public PCB(int id, String nombre, int prioridad, int deadline) {
        this.id = id;
        this.nombre = nombre;
        this.estado = "Listo";
        this.pc = 0;
        this.mar = 0;
        this.prioridad = prioridad;
        this.deadline = deadline;
        this.totalInstrucciones = 0; 
    }
}