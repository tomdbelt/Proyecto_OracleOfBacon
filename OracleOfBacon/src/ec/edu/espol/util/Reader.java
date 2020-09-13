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
        try (BufferedReader br = new BufferedReader(new FileReader("src/ec/edu/espol/resources/data.txt"))) {
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
        
    }
    
    public static GraphLA<Actor> cargarGraph(){
        GraphLA<Actor> graphOfB = new GraphLA<>(false);
        assignEdges(loadDataFromFile(), graphOfB);
        return graphOfB;
    }
    
    public static void main(String[] args){
        String l = toStringSequence();
        System.out.println(l);
        //cargarGraph();
        String s = "[Núria Espert";
        System.out.println(s.charAt(0)=='[');
        System.out.println("### TESTING ###");
        List<Movie> lm = loadDataFromFile();
        System.out.println(lm);
        for(Movie m: lm){
            for(Actor a: m.getActors()){
                System.out.println(a.toString());
            }
            System.out.println("-----");
        }
        //System.out.println(new Actor("Hola").equals(new Actor("HOla")));
        
        /*
        long inicio = System.nanoTime()
        Metodo()
        long final = System,nanoTime()
        System.currentTime()
        */
    }
}
