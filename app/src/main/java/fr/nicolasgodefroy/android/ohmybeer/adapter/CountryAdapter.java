package fr.nicolasgodefroy.android.ohmybeer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import fr.nicolasgodefroy.android.ohmybeer.R;
import fr.nicolasgodefroy.android.ohmybeer.model.Country;
import lombok.Setter;

/**
 * Created by nicolasgodefroy on 14/04/16.
 */
public class CountryAdapter extends BaseAdapter {

    private final Context context;
    private final LayoutInflater inflater;
    @Setter
    private List<Country> countries;

    public CountryAdapter(Context context) {
        super();
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return countries == null ? 0 : countries.size();
    }

    @Override
    public Object getItem(int i) {
        return countries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView = view;
        CountryHolder countryHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_country, viewGroup, false);
            countryHolder = new CountryHolder(convertView);
            convertView.setTag(countryHolder);
        } else {
            countryHolder = (CountryHolder) convertView.getTag();
        }
        countryHolder.setHolder(countries.get(i));
        return convertView;
    }

    class CountryHolder {
        private ImageView image;
        private TextView name;

        CountryHolder(View view) {
            image = (ImageView) view.findViewById(R.id.imageCountry);
            name = (TextView) view.findViewById(R.id.nameCountry);
        }

        public void setHolder(Country country) {
            Glide.with(context).load(country.getImageUrl()).fitCenter().crossFade().into(image);
            name.setText(country.getName());
        }
    }

}
