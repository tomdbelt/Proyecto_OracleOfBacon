/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.common;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author TBeltran
 */
public class Movie {
    private String title;
    private int year;
    private List<Actor> actors;

    public Movie(String title) {
        this.title = title;
        actors = new LinkedList<>();
    }
    
    public boolean addActor(Actor act){
        return actors.add(act);
    }

    @Override
    public String toString() {
        return title;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
    
    
}
