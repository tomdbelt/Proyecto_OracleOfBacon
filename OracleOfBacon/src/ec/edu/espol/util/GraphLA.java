/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

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
public class GraphLA<E> implements Graph<E> {
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
    @Override
    public boolean addVertex(E data){
        Vertex<E> v = new Vertex<>(data);
        return (data == null || vertexes.contains(v))?false:vertexes.add(v);
    }

    @Override
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

    @Override
    public boolean addEdge(E src, E dst, int peso){
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

    @Override
    public boolean removeEdge(E src, E dst){
        if(src == null || dst == null) return false;
        Vertex<E> vs = searchVertex(src);
        Vertex<E> vd = searchVertex(dst);
        if(vs == null || vd == null) return false;
        Edge<E> e = new Edge<>(0, vs, vd);
        vs.getEdges().remove(e);
        if(!directed){
            e = new Edge<>(0, vd, vs);
            vd.getEdges().remove(e);
        }
        return true;
    }

    public boolean isEmpty(){
        return vertexes.isEmpty();
    }
    
    @Override
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

    @Override
    public int outDegree(E data) {
        Vertex<E> vertexSrc = searchVertex(data);
        if(vertexSrc == null) throw new IllegalArgumentException("El parámetro no debe ser nulo y debe pertenecer al grafo");
        return vertexSrc.getEdges().size();
    }
    
    public boolean isConnected(){
        return connectedComponents().size()>1?false:true;
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
    
    public List<Set<E>> connectedComponents(){
        List<Set<E>> lista = new LinkedList<>();
        GraphLA<E> reverseGraph = reverse();
        List<E> vertRest = getVertexes();
        while(!vertRest.isEmpty()){
            List<E> bfsNormal = bfs(vertRest.get(0));
            Set<E> conjBfsN = new HashSet<>(bfsNormal);
            List<E> bfsReverse = reverseGraph.bfs(vertRest.get(0));
            Set<E> conjBfsR = new HashSet<>(bfsReverse);
            conjBfsN.retainAll(conjBfsR);
            vertRest.removeAll(conjBfsN);
            lista.add(conjBfsN);
        }
        return lista;
    }
    
    public int minNumEdges(E inicio, E fin){
        if(inicio == null || fin == null) return -1;
        if(inicio.equals(fin)) return 0;
        unitaryDijkstra(inicio);
        int x = searchVertex(fin).getDistancia();
        cleanVertexes();
        return x;
    }
    
    public int menorDistancia(E inicio, E fin){
        if(inicio == null || fin == null) return -1;
        if(inicio.equals(fin)) return 0;
        dijkstra(inicio);
        int x = searchVertex(fin).getDistancia();
        cleanVertexes();
        return x;
    }
    
    public List<E> caminoMinimo(E inicio, E fin){
        List<E> list = new LinkedList<>();
        Vertex<E> v = searchVertex(fin);
        if(inicio == null || fin == null || v == null || inicio.equals(fin)) return list;
        dijkstra(inicio);
        Stack<E> pila = new Stack<>();
        pila.push(v.getData());
        while(v.getAntecesor() != null){
            v = v.getAntecesor();
            pila.push(v.getData());
        }
        while(!pila.empty()){
            list.add(pila.pop());
        }
        cleanVertexes();
        return list;
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
    
    //BUSQUEDA EN ANCHURA
    public List<E> bfs(E data){
        List<E> result = new LinkedList<>();
        if(data == null) return result;
        Vertex<E> v = searchVertex(data);
        if(v == null) return result;
        
        Queue<Vertex<E>> cola = new LinkedList<>();
        v.setVisited(true);       
        cola.offer(v);
        while(!cola.isEmpty()){
            v = cola.poll();
            result.add(v.getData());
            for(Edge<E> e: v.getEdges()){
                if(!e.getVDestino().isVisited()){
                    e.getVDestino().setVisited(true);
                    cola.offer(e.getVDestino());
                }
            }
        }
        cleanVertexes();
        return result;
    }
    
    //BUSQUEDA EN PROFUNDIDAD
    public List<E> dfs(E data){
        List<E> result = new LinkedList<>();
        if(data == null) return result;
        Vertex<E> v = searchVertex(data);
        if(v == null) return result;
        
        Deque<Vertex<E>> pila = new LinkedList<>();
        v.setVisited(true);       
        pila.push(v);
        while(!pila.isEmpty()){
            v = pila.pop();
            result.add(v.getData());
            for(Edge<E> e: v.getEdges()){
                if(!e.getVDestino().isVisited()){
                    e.getVDestino().setVisited(true);
                    pila.push(e.getVDestino());
                }
            }
        }
        cleanVertexes();
        return result;
    }
    
    //ALGORITMOS
    private boolean allVertexesVisited(){
        for(Vertex<E> v: vertexes){
            if(!v.isVisited())
                return false;
        }
        return true;
    }
    
    public GraphLA<E> prim(){ //basado en vertices
        GraphLA<E> result = new GraphLA<>(directed);
        if(directed || !isConnected()) return result;
        for(Vertex<E> v: vertexes){
            result.vertexes.add(new Vertex<>(v.getData()));
        }
        PriorityQueue<Edge<E>> cola = new PriorityQueue<>((Edge<E> e1, Edge<E> e2)->e1.getPeso()-e2.getPeso());
        Vertex<E> vSrc = vertexes.getFirst();
        vSrc.setVisited(true);
        while(!allVertexesVisited()){                 
            for(Edge<E> e: vSrc.getEdges()){
                if(!e.getVDestino().isVisited())
                    cola.offer(e);
            }    
            Edge<E> edgeMin = cola.poll();
            if(!edgeMin.getVDestino().isVisited())
                result.addEdge(edgeMin.getVOrigen().getData(), edgeMin.getVDestino().getData(), edgeMin.getPeso());
            vSrc = edgeMin.getVDestino();
            vSrc.setVisited(true);               
        }
        cleanVertexes();
        return result;
    }
    
    public GraphLA<E> kruskal(){ //basado en aristas
        GraphLA<E> result = new GraphLA<>(directed);
        if(directed || !isConnected()) return result;
        PriorityQueue<Edge<E>> cola = new PriorityQueue<>((Edge<E> e1, Edge<E> e2)->e1.getPeso()-e2.getPeso());
        for(Vertex<E> v: vertexes){
            result.vertexes.add(new Vertex<>(v.getData()));
        }
        for(Vertex<E> v: vertexes){
            for(Edge<E> e: v.getEdges()){
                cola.offer(e);
            }
        }
        while(!result.isConnected()){
            List<Set<E>> components = result.connectedComponents();
            Edge<E> e = cola.poll();
            for(Set<E> componente: components){
                if(componente.contains(e.getVOrigen().getData()) && !componente.contains(e.getVDestino().getData())){
                    result.addEdge(e.getVOrigen().getData(), e.getVDestino().getData(), e.getPeso());
                }
            }             
        }
        return result;
    }
    
    private void unitaryDijkstra(E inicio){
        Vertex<E> v = searchVertex(inicio);
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
                        cola.offer(e.getVDestino());
                    }
                }
            }
        }
    }
    
    private void dijkstra(E inicio){
        Vertex<E> v = searchVertex(inicio);
        PriorityQueue<Vertex<E>> cola = new PriorityQueue<>((Vertex<E> v1, Vertex<E> v2)->v1.getDistancia()- v2.getDistancia());
        v.setDistancia(0);
        cola.offer(v);
        while(!cola.isEmpty()){
            v = cola.poll();
            v.setVisited(true);
            for(Edge<E> e: v.getEdges()){
                if(!e.getVDestino().isVisited()){
                    if(v.getDistancia()+e.getPeso() < e.getVDestino().getDistancia()){
                        e.getVDestino().setDistancia(v.getDistancia()+e.getPeso());
                        e.getVDestino().setAntecesor(v);
                        cola.offer(e.getVDestino());
                    }
                }
            }
        }
    }
}