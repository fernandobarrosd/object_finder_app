package br.ifsul.objectfinder_ifsul.dto;

import java.io.Serializable;

public class LostObjectDTO implements Serializable {
    private Long id;
    private String name;

    private String description;

    private String category;

    private String foundedDate;

    private String isFounded;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFoundedDate() {
        return foundedDate;
    }

    public void setFoundedDate(String foundedDate) {
        this.foundedDate = foundedDate;
    }

    public String getIsFounded() {
        return isFounded;
    }

    public void setIsFounded(String isFounded) {
        this.isFounded = isFounded;
    }
}
