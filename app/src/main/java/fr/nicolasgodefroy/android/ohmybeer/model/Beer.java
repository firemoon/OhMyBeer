package fr.nicolasgodefroy.android.ohmybeer.model;

import lombok.Data;

/**
 * Created by nicolasgodefroy on 15/04/16.
 */
@Data
public class Beer {
    private String name;
    private float abv;
    private float ibu;
    private float srm;
    private float upc;
    private String type;
    private String brewery_id;
    private String updated;
    private String description;
    private String style;
    private String category;
}
