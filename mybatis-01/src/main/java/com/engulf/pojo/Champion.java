package com.engulf.pojo;

public class Champion {
    private Integer id;
    private String name;
    private Integer local_id;

    public Champion() {
    }

    public Champion(Integer id, String name, Integer local_id) {
        this.id = id;
        this.name = name;
        this.local_id = local_id;
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

    public Integer getLocal_id() {
        return local_id;
    }

    public void setLocal_id(Integer local_id) {
        this.local_id = local_id;
    }

    @Override
    public String toString() {
        return "Champion{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", local_id=" + local_id +
                '}';
    }
}
