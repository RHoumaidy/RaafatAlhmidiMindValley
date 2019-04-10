package com.raafat.raafatalhmidimindvalley.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.List;

/**
 * Created by Raafat Alhoumaidy on 4/10/2019 @4:56 PM.
 */
public class Photo extends BaseObservable {

    private String id;
    private String createdAt;
    private int width;
    private int height;
    private String color;
    private int likes;
    private boolean likedByUser;
    private User user;
    private Url urls;
    private List<Category> categories;
    private Link link;


    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Bindable
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Bindable
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Bindable
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Bindable
    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Bindable
    public boolean isLikedByUser() {
        return likedByUser;
    }

    public void setLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
    }

    @Bindable
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Bindable
    public Url getUrls() {
        return urls;
    }

    public void setUrls(Url urls) {
        this.urls = urls;
    }

    @Bindable
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public static class Link extends BaseObservable {
        private String self;
        private String html;
        private String download;

        @Bindable
        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }

        @Bindable
        public String getHtml() {
            return html;
        }

        public void setHtml(String html) {
            this.html = html;
        }

        @Bindable
        public String getDownload() {
            return download;
        }

        public void setDownload(String download) {
            this.download = download;
        }
    }

    public static class Url extends BaseObservable {
        private String raw;
        private String full;
        private String regular;
        private String small;
        private String thumb;

        @Bindable
        public String getRaw() {
            return raw;
        }

        public void setRaw(String raw) {
            this.raw = raw;
        }

        @Bindable
        public String getFull() {
            return full;
        }

        public void setFull(String full) {
            this.full = full;
        }

        @Bindable
        public String getRegular() {
            return regular;
        }

        public void setRegular(String regular) {
            this.regular = regular;
        }

        @Bindable
        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        @Bindable
        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }


    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }
}
