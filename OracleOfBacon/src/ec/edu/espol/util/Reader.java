/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

import ec.edu.espol.common.Actor;
import ec.edu.espol.common.Movie;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author TBeltran
 */
public class Reader {
    
    private static String toStringSequence(){
        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader("src/ec/edu/espol/resources/shortdata2.txt"))){
            String line = br.readLine();
            while(line!=null){
                sb.append(line);
                line = br.readLine();
            }
            return sb.toString();
        }catch(IOException ex){
            return null;
        }    
    }
    
    private static List<Movie> loadDataFromFile(){
        List<Movie> listMovies = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/ec/edu/espol/resources/shortdata.txt"))) {
            String line = br.readLine();
            while(line!=null) { 
                String[] dataM = line.split(":");
                String[] dataM2 = dataM[1].split(",");
                Movie pelicula = new Movie(dataM2[0]);
                
                String[] dataA = line.split("\\[");      
                if(dataA[1].charAt(0)!=']'){
                    String[] dataA2 = dataA[1].split("\\]");
                    String[] actors = dataA2[0].split(",");
                    for(String name: actors){
                        /*boolean separated = false;
                        for(int i=0; i<name.length(); i++){
                            if(name.charAt(i)=='|')
                                separated = true;
                        }
                        if(separated){
                            name = name.split("\\|")[1];
                        }*/
                        Actor act = new Actor(name);
                        pelicula.addActor(act);    
                    }
                    listMovies.add(pelicula);
                }
                
                line = br.readLine();
            }
            
        } catch (IOException ex) {
            return null;
        }catch(StringIndexOutOfBoundsException ex){
            
        }
        return listMovies;
    }  
    
    private static void assignEdges(List<Movie> list, GraphLA<Actor> gOfB){
        for(Movie m: list){
            for(Actor a: m.getActors()){
                gOfB.addVertex(a);     
            }
        }
        
        for(Movie m: list){
            for(Actor src: m.getActors()){
                for(Actor dst: m.getActors()){
                    gOfB.addEdge(src, dst, m);
                }
            }
        }
    }
    
    public static GraphLA<Actor> cargarGraph(){
        GraphLA<Actor> graphOfB = new GraphLA<>(false);
        assignEdges(loadDataFromFile(), graphOfB);
        return graphOfB;
    }
    
    public static void main(String[] args){
        System.out.println("### TESTING ###");
        /*List<Movie> lm = loadDataFromFile();
        System.out.println(lm);
        for(Movie m: lm){
            for(Actor a: m.getActors()){
                System.out.println(a.toString());
            }
            System.out.println("-----");
        }*/
        GraphLA<Actor> g = cargarGraph();
        System.out.println(g);
        //System.out.println(new Actor("Hola").equals(new Actor("HOla")));
        
        /*
        long inicio = System.nanoTime()
        Metodo()
        long final = System,nanoTime()
        System.currentTime()
        */
        //long startTime = System.nanoTime();
        System.out.println(g.numEdgesDijkstra(new Actor("Núria Espert"), new Actor("Anna Lizaran")));
        System.out.println(g.getCaminoDijkstra(new Actor("Núria Espert"), new Actor("Anna Lizaran")));
        //System.out.println(g.getCaminoDijkstra(new Actor("Núria Espert"), new Actor("Núria Espert")));
        //long endTime = System.nanoTime()-startTime;
        //System.out.println(endTime);
        System.out.println(g.numEdgesBFS(new Actor("Núria Espert"), new Actor("Anna Lizaran")));
        System.out.println(g.getCaminoBFS(new Actor("Núria Espert"), new Actor("Anna Lizaran")));
        
        System.out.println(g.numEdgesDFS(new Actor("Núria Espert"), new Actor("Anna Lizaran")));
        System.out.println(g.getCaminoDFS(new Actor("Núria Espert"), new Actor("Anna Lizaran")));
        //System.out.println(g.numEdgesDijkstra(new Actor("Kevin Bacon"), new Actor("Tom Cruise")));
        //System.out.println(g.getCaminoDijkstra(new Actor("Kevin Bacon"), new Actor("Tom Cruise")));
        //System.out.println(g.numEdgesDijkstra(new Actor("Kevin Bacona"), new Actor("Tommy Lee Jones")));
    }
}
