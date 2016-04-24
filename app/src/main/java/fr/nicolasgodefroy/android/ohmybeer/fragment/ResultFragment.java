package fr.nicolasgodefroy.android.ohmybeer.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.nicolasgodefroy.android.ohmybeer.R;
import fr.nicolasgodefroy.android.ohmybeer.model.Beer;
import lombok.Setter;

/**
 * Created by nicolasgodefroy on 15/04/16.
 */
public class ResultFragment extends Fragment {


    @Bind(R.id.listView)
    ListView listView;

    @Bind(R.id.progressView)
    ProgressView progressView;

    @Setter
    private List<Beer> beerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country, container, false);
        ButterKnife.bind(this, view);
        initialize();
        return view;
    }

    private void initialize() {

        List<Map<String,String>> fillMaps = new ArrayList<>();
        String[] from = new String[] {"name", "abv"};
        int[] to = new int[] { android.R.id.text1, android.R.id.text2 };

        for (Beer beer : beerList) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("name", beer.getName());
            map.put("abv", beer.getAbv()+"%");
            fillMaps.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), fillMaps, android.R.layout.simple_list_item_2, from, to);
        listView.setAdapter(adapter);
    }
}
