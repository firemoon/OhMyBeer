package fr.nicolasgodefroy.android.ohmybeer.model;

import java.util.List;

import lombok.Data;

/**
 * Created by nicolasgodefroy on 14/04/16.
 */
@Data
public class Brewery {
    private String name;
    private String city;
    private String state;
    private String code;
    private String country;
    private String phone;
    private String website;
    private String type;
    private String updated;
    private String description;
    private List<String> address;
    private GeoLocation geo;
}

