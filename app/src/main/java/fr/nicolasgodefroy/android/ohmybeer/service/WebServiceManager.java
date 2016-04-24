package fr.nicolasgodefroy.android.ohmybeer.service;

import java.util.List;

import fr.nicolasgodefroy.android.ohmybeer.BuildConfig;
import fr.nicolasgodefroy.android.ohmybeer.model.Beer;
import fr.nicolasgodefroy.android.ohmybeer.model.Brewery;
import fr.nicolasgodefroy.android.ohmybeer.model.Country;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nicolasgodefroy on 14/04/16.
 */
public class WebServiceManager {

    private static WebServiceManager instance;
    private OhMyBeerService service;

    private WebServiceManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(OhMyBeerService.class);
    }

    public static WebServiceManager getInstance() {
        if (instance == null) {
            instance = new WebServiceManager();
        }
        return instance;
    }

    public void listCountry(Callback<List<Country>> callback) {
        Call<List<Country>> call = service.listCountry();
        call.enqueue(callback);
    }


    public void listBrewery(String brewery, Callback<List<Brewery>> callback) {
        Call<List<Brewery>> call = service.listBrewery(brewery);
        call.enqueue(callback);
    }

    public void searchBeer(float abvMin, float abvMax, Callback<List<Beer>> callback) {
        Call<List<Beer>> call = service.searchBeer(abvMin, abvMax);
        call.enqueue(callback);
    }

}
