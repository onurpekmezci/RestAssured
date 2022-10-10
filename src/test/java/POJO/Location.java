package POJO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Location {

    private  String poastcode;
    private  String country;
    private  String countryabbreviation;
    private ArrayList<Place> places;


    public String getPoastcode() {
        return poastcode;
    }

    @JsonProperty("post code")
    public void setPoastcode(String poastcode) {
        this.poastcode = poastcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryabbreviation() {
        return countryabbreviation;
    }
@JsonProperty("country abbreviation")
    public void setCountryabbreviation(String countryabbreviation) {
        this.countryabbreviation = countryabbreviation;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }




    @Override
    public String toString() {
        return "Location{" +
                "poastcode='" + poastcode + '\'' +
                ", country='" + country + '\'' +
                ", countryabbreviation='" + countryabbreviation + '\'' +
                ", places=" + places +
                '}';
    }
}
