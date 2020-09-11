/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

/**
 *
 * @author TBeltran
 * @param <E>
 */
public interface Graph<E> {
    boolean addVertex(E data);
    boolean removeVertex(E data);
    boolean addEdge(E src, E dst, int peso);
    boolean removeEdge(E src, E dst);
    int inDegree(E data);
    int outDegree(E data);
}
