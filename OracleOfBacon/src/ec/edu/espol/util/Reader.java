/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

import ec.edu.espol.common.Actor;
import ec.edu.espol.common.Movie;
import ec.edu.espol.constants.CONSTANTES;
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
    
    private static List<Movie> loadDataFromFile(){
        List<Movie> listMovies = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CONSTANTES.RUTA_SHORT_DATA))) {
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
                        String[] infoActor = name.split("\\|");
                        if(infoActor.length>1){
                            name = infoActor[1];
                        }
                        Actor act = new Actor(name);
                        pelicula.addActor(act);    
                    }
                    listMovies.add(pelicula);
                }             
                line = br.readLine();
            }
            
        } catch (IOException ex) {
            return null;
        }/*catch(StringIndexOutOfBoundsException ex){
            
        }*/
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
}
