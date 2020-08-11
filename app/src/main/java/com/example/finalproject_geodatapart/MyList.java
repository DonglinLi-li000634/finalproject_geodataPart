package com.example.finalproject_geodatapart;

public class MyList {
    protected String Country, Region, City, Currency;
    protected double lat,lon;
    protected long id;
    public MyList(String Country,String Region,String City,String Currency,double lat,double lon,long id){
        this.City=City;
        this.Region=Region;
        this.Country=Country;
        this.Currency=Currency;
        this.lat=lat;
        this.lon=lon;
        this.id=id;
    }
    public MyList(String Country,String Region,String City,String Currency,double lat,double lon){
        this.City=City;
        this.Region=Region;
        this.Country=Country;
        this.Currency=Currency;
        this.lat=lat;
        this.lon=lon;
    }
    public MyList(){
        this(null,null,null,null,0.0,0.0,0);
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getCity() {
        return City;
    }

    public String getCountry() {
        return Country;
    }

    public String getCurrency() {
        return Currency;
    }

    public String getRegion() {
        return Region;
    }

    public String getInfo(){
        return "City: "+City+"\nCountry: "+Country+"\nCurrency: "+Currency+"\nlat: "+lat+"\nlon: "+lon;
    }

    public long getId() {
        return id;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public void setId(long id) {
        this.id = id;
    }
}
