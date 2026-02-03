package com.example.hbmSystem.dto;

import jakarta.persistence.Column;

public class DescInputDto {

    @Column(length = 2000)
    private String description;

    public DescInputDto() {
    }

    public DescInputDto(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DescDto{" +
                "description='" + description + '\'' +
                '}';
    }
}
