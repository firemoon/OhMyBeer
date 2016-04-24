package fr.nicolasgodefroy.android.ohmybeer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.rey.material.widget.ProgressView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.nicolasgodefroy.android.ohmybeer.R;
import fr.nicolasgodefroy.android.ohmybeer.adapter.BreweryAdapter;
import fr.nicolasgodefroy.android.ohmybeer.model.Brewery;
import fr.nicolasgodefroy.android.ohmybeer.model.Country;
import fr.nicolasgodefroy.android.ohmybeer.service.WebServiceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreweryActivity extends AppCompatActivity {

    @Bind(R.id.listView)
    ListView listView;

    @Bind(R.id.progressView)
    ProgressView progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewery);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        progressView.start();
        progressView.setVisibility(View.VISIBLE);
        final BreweryAdapter breweryAdapter = new BreweryAdapter(this);
        listView.setAdapter(breweryAdapter);
        Country country = (Country) getIntent().getSerializableExtra("country");
        WebServiceManager.getInstance().listBrewery(country.getName(), new Callback<List<Brewery>>() {
            @Override
            public void onResponse(Call<List<Brewery>> call, Response<List<Brewery>> response) {

                if (response.isSuccessful()) {
                    breweryAdapter.setBreweries(response.body());
                    breweryAdapter.notifyDataSetChanged();
                }
                progressView.stop();
                progressView.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Brewery>> call, Throwable t) {
                progressView.stop();
                progressView.setVisibility(View.GONE);
            }
        });
    }
}
