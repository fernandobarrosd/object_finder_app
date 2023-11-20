package br.ifsul.objectfinder_ifsul.models;

import android.net.Uri;

public class LostObject {
    private String name, description, createdDate, findDate;
    private Category category;

    private Uri photoUri;

    private LostObject() {
    }

    private LostObject(String name, String description, Category category, String createdDate, String findDate, Uri photoUri) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.createdDate = createdDate;
        this.findDate = findDate;
        this.photoUri = photoUri;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getFindDate() {
        return findDate;
    }

    public void setFindDate(String findDate) {
        this.findDate = findDate;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }

    public static class LostObjectBuilder {
        LostObject lostObject;

        public LostObjectBuilder() {
            lostObject = new LostObject();
        }

        public LostObjectBuilder setName(String name) {
            lostObject.name = name;
            return this;
        }

        public LostObjectBuilder setDescription(String description) {
            lostObject.description = description;
            return this;
        }

        public LostObjectBuilder setCreatedDate(String createdDate) {
            lostObject.createdDate = createdDate;
            return this;
        }

        public LostObjectBuilder setFindDate(String findDate) {
            lostObject.findDate = findDate;
            return this;
        }

        public LostObjectBuilder setCategory(Category category) {
            lostObject.category = category;
            return this;
        }

        public LostObjectBuilder setPhotoUri(Uri photoUri) {
            lostObject.photoUri = photoUri;
            return this;
        }

        public LostObject build() {
            return lostObject;
        }
    }
}