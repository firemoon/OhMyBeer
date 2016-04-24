package fr.nicolasgodefroy.android.ohmybeer.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by nicolasgodefroy on 14/04/16.
 */
@Data
public class Country implements Serializable {
    private String name;
    private String imageUrl;
}
