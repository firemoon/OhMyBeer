package fr.nicolasgodefroy.android.ohmybeer.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rey.material.widget.ProgressView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.nicolasgodefroy.android.ohmybeer.R;
import fr.nicolasgodefroy.android.ohmybeer.activity.BreweryActivity;
import fr.nicolasgodefroy.android.ohmybeer.adapter.CountryAdapter;
import fr.nicolasgodefroy.android.ohmybeer.listener.CountryListener;
import fr.nicolasgodefroy.android.ohmybeer.model.Country;
import fr.nicolasgodefroy.android.ohmybeer.service.WebServiceManager;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nicolasgodefroy on 15/04/16.
 */
public class CountryFragment extends Fragment {


    @Bind(R.id.listView)
    ListView listView;

    @Bind(R.id.progressView)
    ProgressView progressView;

    @Setter
    private CountryListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country, container, false);
        ButterKnife.bind(this, view);
        initialize();
        return view;
    }

    private void initialize() {
        final CountryAdapter countryAdapter = new CountryAdapter(getActivity());
        listView.setAdapter(countryAdapter);
        progressView.setVisibility(View.VISIBLE);
        // Click sur ma list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listener == null) {
                    Intent intent = new Intent(getActivity(), BreweryActivity.class);
                    intent.putExtra("country", (Country) countryAdapter.getItem(i));
                    startActivity(intent);
                } else {
                    getActivity().onBackPressed();
                    listener.onReceiveCountry((Country) countryAdapter.getItem(i));
                }
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
