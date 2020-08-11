package com.example.finalproject_geodatapart;

public class CityInfo {
    protected String Info;
    //protected double lat,lon;
    protected long id;
    public CityInfo(String info,long id){
        this.Info=info;
        this.id=id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }
}
