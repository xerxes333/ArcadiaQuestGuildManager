package com.example.jdraxler.arcadiaquestguildmanager;

import java.util.List;

/**
 * Created by jdraxler on 2/27/2015.
 */
public class Save {

    private long id;
    private String name;
    private String guild;
    private List<Hero> heroes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGuild() {
        return guild;
    }

    public void setGuild(String guild) {
        this.guild = guild;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

}
