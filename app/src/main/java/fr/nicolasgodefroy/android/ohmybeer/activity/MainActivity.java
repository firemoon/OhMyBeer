package fr.nicolasgodefroy.android.ohmybeer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rey.material.widget.ProgressView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.nicolasgodefroy.android.ohmybeer.R;
import fr.nicolasgodefroy.android.ohmybeer.adapter.CountryAdapter;
import fr.nicolasgodefroy.android.ohmybeer.model.Country;
import fr.nicolasgodefroy.android.ohmybeer.service.WebServiceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {


    @Bind(R.id.listView)
    ListView listView;

    @Bind(R.id.progressView)
    ProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        final CountryAdapter countryAdapter = new CountryAdapter(this);
        listView.setAdapter(countryAdapter);
        progressView.setVisibility(View.VISIBLE);
        // Click sur ma list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, BreweryActivity.class);
                intent.putExtra("country", (Country)countryAdapter.getItem(i));
                startActivity(intent);
            }
        });
        // Appel de service
        WebServiceManager.getInstance().listCountry(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                progressView.stop();
                progressView.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    countryAdapter.setCountries(response.body());
                    countryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                progressView.stop();
                progressView.setVisibility(View.GONE);
            }
        });
    }
}
