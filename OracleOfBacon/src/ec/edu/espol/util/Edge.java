/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

import ec.edu.espol.common.Movie;
import java.util.Objects;

/**
 *
 * @author TBeltran
 * @param <E>
 */
public class Edge<E> {
    private Movie peso;
    private Vertex<E> vOrigen;
    private Vertex<E> vDestino;

    public Edge(Movie peso, Vertex<E> vOrigen, Vertex<E> vDestino) {
        this.peso = peso;
        this.vOrigen = vOrigen;
        this.vDestino = vDestino;
    }

    public Movie getPeso() {
        return peso;
    }

    public void setPeso(Movie peso) {
        this.peso = peso;
    }

    public Vertex<E> getVOrigen() {
        return vOrigen;
    }

    public void setVOrigen(Vertex<E> vOrigen) {
        this.vOrigen = vOrigen;
    }

    public Vertex<E> getVDestino() {
        return vDestino;
    }

    public void setVDestino(Vertex<E> vDestino) {
        this.vDestino = vDestino;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Edge<E> other = (Edge<E>) obj;
        return Objects.equals(this.vOrigen, other.vOrigen) && Objects.equals(this.vDestino, other.vDestino);
    }
    
    @Override
    public String toString(){
        return "(" + vOrigen + "," + vDestino + "," + peso + ")";
    }
    
    public String toStringMovie(){
        return peso.toString();
    }
}
