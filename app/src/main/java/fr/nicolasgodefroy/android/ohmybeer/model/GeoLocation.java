package fr.nicolasgodefroy.android.ohmybeer.model;

import lombok.Data;

/**
 * Created by nicolasgodefroy on 14/04/16.
 */
@Data
public class GeoLocation {
    private String accuracy;
    private double lat;
    private double lon;
}
