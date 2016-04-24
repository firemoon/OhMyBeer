package fr.nicolasgodefroy.android.ohmybeer.service;

import java.util.List;

import fr.nicolasgodefroy.android.ohmybeer.model.Beer;
import fr.nicolasgodefroy.android.ohmybeer.model.Brewery;
import fr.nicolasgodefroy.android.ohmybeer.model.Country;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by nicolasgodefroy on 14/04/16.
 */
public interface OhMyBeerService {

    @GET("brewerycountry/country/")
    Call<List<Country>> listCountry();

    @GET("whereismybrewery/country/{brewery}")
    Call<List<Brewery>> listBrewery(@Path("brewery") String brewery);

    @GET("whereismybeer/beerfilter")
    Call<List<Beer>> searchBeer(@Query("abvMin") float abvMin,@Query("abvMax") float abvMax);
}
