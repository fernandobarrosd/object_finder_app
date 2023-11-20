package br.ifsul.objectfinder_ifsul.dto;

public record LostObjectDTO(
        String name,
        String description,
        String locale,
        String category,
        Integer userID) {}