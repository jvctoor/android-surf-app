package com.example.waves013;

import android.graphics.drawable.Drawable;

public class Pico {

    private int id;
    private String nome;
    private String lat;
    private String lng;
    private String city;
    private boolean isFavorite;

    public Pico(int id, String nome, String lat, String lng, String city, boolean isFavorite) {
        this.id = id;
        this.nome = nome;
        this.lat = lat;
        this.lng = lng;
        this.city = city;
        this.isFavorite = isFavorite;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

}
