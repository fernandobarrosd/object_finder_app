package br.ifsul.objectfinder_ifsul.models;


public enum Category {
    ESCOLAR("Escolar"),
    OUTROS("Outros"),
    PESSOAL("Pessoal");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
