package com.google.jsteam.model;

public class Game {

    private int id;
    private String name;
    private String description;
    private String image;
    private Integer price;
    private Double rating;
    private String genre;

    public Game(Integer id, String name, String description, String image, Integer price, Double rating, String genre) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.rating = rating;
        this.genre = genre;
    }

    public String getValidatedImage(){
        return (image == null || image.equals("") || image.equals("null") || image.isEmpty()) ? "game_default_image" : image;
    }

    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFormattedPrice(){
        return "Rp. " + price;
    }
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
