/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.codename1.demos.grub.entities;
/**
 *
 * @author MSI
 */
public class Course {
    private int id;
    private String titre;
    private String description;

    public Course(int id, String titre, String description) {
        this.id = id;
        this.titre = titre;
        this.description = description;
    }

    public Course() {
    }

    @Override
    public String toString() {
        return "Cours{" + "id=" + id + ", titre=" + titre + ", description=" + description + '}';
    }

    public Course(String titre, String description) {
        this.titre = titre;
        this.description = description;
    }




    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }




}