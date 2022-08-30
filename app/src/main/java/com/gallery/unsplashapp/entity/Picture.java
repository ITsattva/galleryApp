package com.gallery.unsplashapp.entity;

public class Picture {
    private String author;
    private String linkToSmallSize;
    private String linkToBigSize;

    public Picture(String author, String linkToSmallSize, String linkToBigSize) {
        this.author = author;
        this.linkToSmallSize = linkToSmallSize;
        this.linkToBigSize = linkToBigSize;
    }

    public String getAuthor() {
        return author;
    }

    public String getLinkToSmallSize() {
        return linkToSmallSize;
    }

    public String getLinkToBigSize() {
        return linkToBigSize;
    }
}