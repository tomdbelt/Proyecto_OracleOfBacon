/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.common;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author TBeltran
 */
public class Actor {
    private String name;
    private String firstName;
    private String lastName;
    private Set<Movie> movies;

    public Actor(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
        movies = new HashSet<>();
    }

    public Actor(String name) {
        this.name = name;
        movies = new HashSet<>();
    }
    
    
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Movie> getMovies() {
        return movies;
    }
    
    public boolean addMovie(Movie m){
        return movies.add(m);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.name);
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
        final Actor other = (Actor) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    
    
}
