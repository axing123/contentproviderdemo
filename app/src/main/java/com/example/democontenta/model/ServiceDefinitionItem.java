package com.example.democontenta.model;

public class ServiceDefinitionItem {
    public String id;
    public String name;
    public String type;
    public String desc;
    public boolean enable;

    public ServiceDefinitionItem(String id, String name, String type, String desc, boolean enable) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.desc = desc;
        this.enable = enable;
    }
}
