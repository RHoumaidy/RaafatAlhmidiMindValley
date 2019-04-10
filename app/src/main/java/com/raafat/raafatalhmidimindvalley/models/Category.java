package com.raafat.raafatalhmidimindvalley.models;

/**
 * Created by Raafat Alhoumaidy on 4/10/2019 @4:57 PM.
 */
public class Category {

    private int id;
    private String title;
    private int photoCount;
    private Link links;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }

    public Link getLinks() {
        return links;
    }

    public void setLinks(Link links) {
        this.links = links;
    }

    public static class Link {
        private String self;
        private String photos;

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }

        public String getPhotos() {
            return photos;
        }

        public void setPhotos(String photos) {
            this.photos = photos;
        }
    }
}
