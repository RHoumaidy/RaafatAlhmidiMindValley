package com.raafat.raafatalhmidimindvalley.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Raafat Alhoumaidy on 4/10/2019 @4:54 PM.
 */
public class User extends BaseObservable{

    private String id;
    private String userName;
    private String fullName;
    private ProfileImage profileImage;
    private Link link;


    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Bindable
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Bindable
    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    @Bindable
    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public static class Link extends BaseObservable{
        private String self;
        private String html;
        private String photos;
        private String likes;

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
        public String getPhotos() {
            return photos;
        }

        public void setPhotos(String photos) {
            this.photos = photos;
        }

        @Bindable
        public String getLikes() {
            return likes;
        }

        public void setLikes(String likes) {
            this.likes = likes;
        }
    }


    public static class ProfileImage extends BaseObservable {

        private String small;
        private String medium;
        private String large;

        @Bindable
        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        @Bindable
        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        @Bindable
        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }
    }
}
