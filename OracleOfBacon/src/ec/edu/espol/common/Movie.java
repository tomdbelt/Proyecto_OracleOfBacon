/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.common;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author TBeltran
 */
public class Movie {
    private String title;
    private List<Actor> actors;

    public Movie(String title) {
        this.title = title;
        actors = new LinkedList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public boolean addActor(Actor act){
        return actors.add(act);
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
    
    @Override
    public String toString() {
        return title;
    }
    
}
