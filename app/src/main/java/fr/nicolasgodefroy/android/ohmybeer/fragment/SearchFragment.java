package fr.nicolasgodefroy.android.ohmybeer.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rey.material.widget.ProgressView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.nicolasgodefroy.android.ohmybeer.R;
import fr.nicolasgodefroy.android.ohmybeer.activity.HomeActivity;
import fr.nicolasgodefroy.android.ohmybeer.listener.CountryListener;
import fr.nicolasgodefroy.android.ohmybeer.model.Beer;
import fr.nicolasgodefroy.android.ohmybeer.model.Country;
import fr.nicolasgodefroy.android.ohmybeer.service.WebServiceManager;
import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements CountryListener {


    @Bind(R.id.progressView)
    ProgressView progressView;
    @Bind(R.id.selected_country)
    LinearLayout selectedCountry;
    @Bind(R.id.imageCountry)
    ImageView imageCountry;
    @Bind(R.id.nameCountry)
    TextView nameCountry;

    @Bind(R.id.textMin)
    TextView textMin;
    @Bind(R.id.textMax)
    TextView textMax;

    private List<Beer> beers;

    private float min = 0.0f;
    private float max = 9.0f;

    private Country country;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        initialize();
        return view;
    }

    private void initialize() {
        textMin.setText(String.valueOf(min));
        textMax.setText(String.valueOf(max));
        selectedCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) getActivity()).onNavigationItemSelected(R.id.selected_country);
            }
        });
    }

    @Override
    public void onReceiveCountry(Country country) {
        this.country = country;
        Glide.with(getActivity()).load(country.getImageUrl()).fitCenter().crossFade().into(imageCountry);
        nameCountry.setText(country.getName());
    }

    @OnClick(R.id.search_button)
    void searchBeer(View view) {
        progressView.start();
        progressView.setVisibility(View.VISIBLE);
        WebServiceManager.getInstance().searchBeer(min, max, new Callback<List<Beer>>() {
            @Override
            public void onResponse(Call<List<Beer>> call, Response<List<Beer>> response) {
                progressView.stop();
                progressView.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    beers = response.body();
                    ((HomeActivity) getActivity()).onNavigationItemSelected(R.id.search_button);
                }
            }

            @Override
            public void onFailure(Call<List<Beer>> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.moinsMin)
    void moinsMinAction(View view) {
        min += min == 0 ? 0 : -0.1f;
        textMin.setText(String.valueOf(min));
    }

    @OnClick(R.id.moinsMax)
    void moinsMaxAction(View view) {
        max += max == 0 ? 0 : -0.1f;
        textMax.setText(String.valueOf(max));
    }

    @OnClick(R.id.plusMin)
    void plusMinAction(View view) {
        min += min == 100 ? 100 : +0.1f;
        textMin.setText(String.valueOf(min));
    }

    @OnClick(R.id.plusMax)
    void plusMaxAction(View view) {
        max += max == 100 ? 100 : +0.1f;
        textMax.setText(String.valueOf(max));
    }

    public List<Beer> getBeers() {
        return beers;
    }
}
