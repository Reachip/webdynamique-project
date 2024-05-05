package com.tuto.springboot.SPWebAppStep2.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Hero {
    private int id;
    private String name;
    private String superPowerName;
    private int superPowerValue;
    private String imgUrl;

    public Hero(int id, String name, String superPowerName, int superPowerValue, String imgUrl) {
        this.name = name;
        this.superPowerName = superPowerName;
        this.superPowerValue = superPowerValue;
        this.imgUrl = imgUrl;
        this.id = id;
    }

    public Hero() {
    }

    @Override
    public String toString() {
        return "HERO ["+this.id+"]: name:"+this.name+", superPowerName:"+this.superPowerName+", superPowerValue:"+this.superPowerValue+" imgUrl:"+this.imgUrl;
    }
}
