package br.ifsul.objectfinder_ifsul.models;

import java.util.ArrayList;

public class Usuario {
    private String name;
    private String email;
    private String password;

    private ArrayList<LostObject> lostObjects;

    public Usuario() {

    }

    public Usuario(String name, String email, String password, ArrayList<LostObject> lostObjects) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.lostObjects = lostObjects;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<LostObject> getLostObjects() {
        return lostObjects;
    }

    public void setLostObjects(ArrayList<LostObject> lostObjects) {
        this.lostObjects = lostObjects;
    }
}
