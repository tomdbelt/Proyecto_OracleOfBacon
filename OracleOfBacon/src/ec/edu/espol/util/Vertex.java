/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

import ec.edu.espol.common.Movie;
import java.util.LinkedList;
import java.util.Objects;

/**
 *
 * @author TBeltran
 * @param <E>
 */
public class Vertex <E> {
    private E data;
    private LinkedList<Edge<E>> edges;
    private boolean visited;
    private int distancia;
    private Vertex<E> antecesor;
    private Movie connectAntecesor;

    public Vertex(E data){
        this.data = data;
        edges = new LinkedList<>();
        distancia = Integer.MAX_VALUE;
    }
    
    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public LinkedList<Edge<E>> getEdges() {
        return edges;
    }

    public void setEdges(LinkedList<Edge<E>> edges) {
        this.edges = edges;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public Vertex<E> getAntecesor() {
        return antecesor;
    }

    public void setAntecesor(Vertex<E> antecesor) {
        this.antecesor = antecesor;
    }

    public Movie getConnectAntecesor() {
        return connectAntecesor;
    }

    public void setConnectAntecesor(Movie connectAntecesor) {
        this.connectAntecesor = connectAntecesor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.data);
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
        final Vertex<E> other = (Vertex<E>) obj;
        return Objects.equals(this.data, other.data);
    }
    
    @Override
    public String toString(){
        return data.toString();
    }
}
