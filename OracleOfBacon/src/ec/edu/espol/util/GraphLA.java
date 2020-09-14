/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

import com.sun.javafx.logging.PlatformLogger.Level;
import ec.edu.espol.common.Movie;
import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author TBeltran
 * @param <E>
 */
public class GraphLA<E>{
    private LinkedList<Vertex<E>> vertexes;
    private boolean directed;

    public GraphLA(boolean directed){
        vertexes = new LinkedList<>();
        this.directed = directed;
    }

    //PRIVATE METHODS
    private Vertex<E> searchVertex(E data){
        if(data == null) return null;
        for(Vertex<E> v: vertexes){
            if(v.getData().equals(data))
                return v;
        }
        return null;
    }
    
    public E searchDataInGraph(E data){
        if(data == null) return null;
        for(Vertex<E> v: vertexes){
            if(v.getData().equals(data))
                return v.getData();
        }
        return null;
    }

    private Edge<E> searchEdge(Vertex<E> src, Vertex<E> dst){
        for(Edge e: src.getEdges()){
            if(e.getVOrigen().equals(src) && e.getVDestino().equals(dst))
                return e;
        }
        return null;
    }

    private void cleanVertexes(){
        for(Vertex<E> v: vertexes){
            v.setVisited(false);
            v.setDistancia(Integer.MAX_VALUE);
            v.setAntecesor(null);
        }
    }

    //METHODS
    public boolean addVertex(E data){
        Vertex<E> v = new Vertex<>(data);
        return (data == null || vertexes.contains(v))?false:vertexes.add(v);
    }

    public boolean removeVertex(E data){
        if(data == null || vertexes.isEmpty()) return false;
        ListIterator<Vertex<E>> iv = vertexes.listIterator();
        while(iv.hasNext()){
            Vertex<E> v = iv.next();
            ListIterator<Edge<E>> ie = v.getEdges().listIterator();
            while(ie.hasNext()){
                Edge<E> e = ie.next();
                if(e.getVDestino().getData().equals(data))
                    ie.remove();
            }
        }
        Vertex<E> vi = new Vertex<>(data);
        return vertexes.remove(vi);
    }

    public boolean addEdge(E src, E dst, Movie peso){
        if(src == null || dst == null) return false;
        Vertex<E> vs = searchVertex(src);
        Vertex<E> vd = searchVertex(dst);
        if(vs == null || vd == null) return false;
        Edge<E> e = new Edge<>(peso, vs, vd);
        if(!vs.getEdges().contains(e))
            vs.getEdges().add(e);
        if(!directed){
            Edge<E> ei = new Edge<>(peso, vd, vs);
            if(!vd.getEdges().contains(ei))
                vd.getEdges().add(ei);
        }
        return true;
    }

    public boolean removeEdge(E src, E dst){
        if(src == null || dst == null) return false;
        Vertex<E> vs = searchVertex(src);
        Vertex<E> vd = searchVertex(dst);
        if(vs == null || vd == null) return false;
        Edge<E> e = new Edge<>(null, vs, vd);
        vs.getEdges().remove(e);
        if(!directed){
            e = new Edge<>(null, vd, vs);
            vd.getEdges().remove(e);
        }
        return true;
    }

    public boolean isEmpty(){
        return vertexes.isEmpty();
    }

    public int inDegree(E data) {
        Vertex<E> vertexDst = searchVertex(data);
        if(vertexDst == null) throw new IllegalArgumentException("El parámetro no debe ser nulo y debe pertenecer al grafo");
        int cont = 0;
        for(Vertex<E> v: vertexes){
            for(Edge<E> e: v.getEdges()){
                if(e.getVDestino().equals(vertexDst))
                    cont++;
            }
        }
        return cont;
    }

    public int outDegree(E data) {
        Vertex<E> vertexSrc = searchVertex(data);
        if(vertexSrc == null) throw new IllegalArgumentException("El parámetro no debe ser nulo y debe pertenecer al grafo");
        return vertexSrc.getEdges().size();
    }

    public GraphLA<E> reverse(){
        GraphLA<E> rGraph = new GraphLA<>(directed);
        for(Vertex<E> v: vertexes){
            rGraph.vertexes.add(new Vertex<>(v.getData()));
        }
        for(Vertex<E> v: vertexes){
            for(Edge<E> e: v.getEdges()){
                rGraph.addEdge(e.getVDestino().getData(), e.getVOrigen().getData(), e.getPeso());   
            }
        }
        return rGraph;
    }

    public List<E> getVertexes(){
        List<E> lista = new LinkedList<>();
        for(Vertex<E> v: vertexes){
            lista.add(v.getData());
        }
        return lista;
    }

    private StringBuilder toStringVertexes(){
        StringBuilder sbVertexes = new StringBuilder();
        sbVertexes.append("V={");
        sbVertexes.append(vertexes.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
        sbVertexes.append("}");
        return sbVertexes;
    }

    private StringBuilder toStringEdges(){
        StringBuilder sbEdges = new StringBuilder();
        sbEdges.append("A={");
        for(Vertex<E> v: vertexes){
            for(Edge<E> e: v.getEdges()){
                sbEdges.append(e.toString());
                sbEdges.append(", ");
            }
        }
        if(sbEdges.length()>3){
            sbEdges.replace(sbEdges.length()-2, sbEdges.length(), "");
        }
        sbEdges.append("}");
        return sbEdges;
    }

    @Override
    public String toString(){
        if(vertexes.isEmpty()) return "V={} A={}"; 
        return toStringVertexes().toString() + " " + toStringEdges().toString();
    }

    //ALGORITMOS
    private void dijkstra(E inicio){
        Vertex<E> v = searchVertex(inicio);
        if(v == null) throw new NullPointerException();
        PriorityQueue<Vertex<E>> cola = new PriorityQueue<>((Vertex<E> v1, Vertex<E> v2)->v1.getDistancia()- v2.getDistancia());
        v.setDistancia(0);
        cola.offer(v);
        while(!cola.isEmpty()){
            v = cola.poll();
            v.setVisited(true);
            for(Edge<E> e: v.getEdges()){
                if(!e.getVDestino().isVisited()){
                    if(v.getDistancia()+1 < e.getVDestino().getDistancia()){
                        e.getVDestino().setDistancia(v.getDistancia()+1);
                        e.getVDestino().setAntecesor(v);
                        e.getVDestino().setConnectAntecesor(e.getPeso());
                        cola.offer(e.getVDestino());
                    }
                }
            }
        }
    }
    
    private void bfs(E data){
        Vertex<E> v = searchVertex(data);
        if(v == null) throw new NullPointerException();

        Queue<Vertex<E>> cola = new LinkedList<>();
        v.setDistancia(0);      
        cola.offer(v);
        while(!cola.isEmpty()){
            v = cola.poll();
            v.setVisited(true); 
            for(Edge<E> e: v.getEdges()){
                if(!e.getVDestino().isVisited()){
                    e.getVDestino().setDistancia(v.getDistancia()+1);
                    e.getVDestino().setAntecesor(v);
                    e.getVDestino().setConnectAntecesor(e.getPeso());
                    cola.offer(e.getVDestino());
                }
            }
        }
    }

    private void dfs(E data){
        Vertex<E> v = searchVertex(data);
        if(v == null) throw new NullPointerException();

        Deque<Vertex<E>> pila = new LinkedList<>();
        v.setDistancia(0);      
        pila.push(v);
        while(!pila.isEmpty()){
            v = pila.pop();
            v.setVisited(true); 
            for(Edge<E> e: v.getEdges()){
                if(!e.getVDestino().isVisited()){
                    e.getVDestino().setDistancia(v.getDistancia()+1);
                    e.getVDestino().setAntecesor(v);
                    e.getVDestino().setConnectAntecesor(e.getPeso());
                    pila.push(e.getVDestino());
                }
            }
        }
    }
    
    public int numEdgesDijkstra(E inicio, E fin){
        if(inicio == null || fin == null) return -1;
        if(inicio.equals(fin)) return 0;
        dijkstra(inicio);
        Vertex<E> v = searchVertex(fin);
        if(v==null) return 0;
        int x = v.getDistancia();
        cleanVertexes();
        return x;
    }
    
    public int numEdgesBFS(E inicio, E fin){
        if(inicio == null || fin == null) return -1;
        if(inicio.equals(fin)) return 0;
        bfs(inicio);
        Vertex<E> v = searchVertex(fin);
        if(v==null) return 0;
        int x = v.getDistancia();
        cleanVertexes();
        return x;
    }
    
    public int numEdgesDFS(E inicio, E fin){
        if(inicio == null || fin == null) return -1;
        if(inicio.equals(fin)) return 0;
        dfs(inicio);
        Vertex<E> v = searchVertex(fin);
        if(v==null) return 0;
        int x = v.getDistancia();
        cleanVertexes();
        return x;
    }

    public List<String> getCaminoDijkstra(E inicio, E fin){
        List<String> list = new ArrayList<>();
        Vertex<E> v = searchVertex(fin);
        if(inicio == null || fin == null || v == null) return list;
        dijkstra(inicio);
        list.add(v.getData().toString());
        while(v.getAntecesor() != null){
            list.add(v.getConnectAntecesor().toString());
            v = v.getAntecesor();
            list.add(v.getData().toString());
        }
        cleanVertexes();
        return list;
    }
    
    public List<String> getCaminoBFS(E inicio, E fin){
        List<String> list = new ArrayList<>();
        Vertex<E> v = searchVertex(fin);
        if(inicio == null || fin == null || v == null) return list;
        bfs(inicio);
        list.add(v.getData().toString());
        while(v.getAntecesor() != null){
            list.add(v.getConnectAntecesor().toString());
            v = v.getAntecesor();
            list.add(v.getData().toString());
        }
        cleanVertexes();
        return list;
    }
    
    public List<String> getCaminoDFS(E inicio, E fin){
        List<String> list = new ArrayList<>();
        Vertex<E> v = searchVertex(fin);
        if(inicio == null || fin == null || v == null) return list;
        dfs(inicio);
        list.add(v.getData().toString());
        while(v.getAntecesor() != null){
            list.add(v.getConnectAntecesor().toString());
            v = v.getAntecesor();
            list.add(v.getData().toString());
        }
        cleanVertexes();
        return list;
    }
}