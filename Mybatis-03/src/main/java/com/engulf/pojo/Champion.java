package com.engulf.pojo;

public class Champion {
    private Integer id;
    private String name;
    private Integer localNum;

    public Champion() {
    }

    public Champion(Integer id, String name, Integer localNum) {
        this.id = id;
        this.name = name;
        this.localNum = localNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLocalNum() {
        return localNum;
    }

    public void setLocalNum(Integer localNum) {
        this.localNum = localNum;
    }

    @Override
    public String toString() {
        return "Champion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", localNum=" + localNum +
                '}';
    }
}
