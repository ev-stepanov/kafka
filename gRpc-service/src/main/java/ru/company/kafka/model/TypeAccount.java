package ru.company.kafka.model;

public enum TypeAccount {
    CREDIT("CREDIT"), DEBIT("DEBIT");

    public String name;

    TypeAccount(String name) {
        this.name = name;
    }
}
