package fr.nicolasgodefroy.android.ohmybeer.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.nicolasgodefroy.android.ohmybeer.R;
import fr.nicolasgodefroy.android.ohmybeer.model.Brewery;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by nicolasgodefroy on 14/04/16.
 */
public class BreweryAdapter extends BaseAdapter {
    private final Context context;
    private final LayoutInflater inflater;
    @Setter
    private List<Brewery> breweries;

    public BreweryAdapter(Context context) {
        super();
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return breweries == null ? 0 : breweries.size();
    }

    @Override
    public Object getItem(int i) {
        return breweries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View convertView = view;
        BreweryHolder breweryHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.adapter_brewery, viewGroup, false);
            breweryHolder = new BreweryHolder(convertView);
            convertView.setTag(breweryHolder);
        } else {
            breweryHolder = (BreweryHolder) convertView.getTag();
        }
        breweryHolder.setHolder(breweries.get(i));
        breweryHolder.getCall().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Brewery brewery = breweries.get(i);
                showCallDialog(context, brewery.getPhone(), brewery.getName());
            }
        });
        if (breweries.get(i).getGeo() != null) {
            breweryHolder.getLoc().setVisibility(View.VISIBLE);
        } else {
            breweryHolder.getLoc().setVisibility(View.GONE);
        }
        breweryHolder.getLoc().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Brewery brewery = breweries.get(i);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps/@" + brewery.getGeo().getLat() +"," + brewery.getGeo().getLon() + ",14z"));
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    public void showCallDialog(final Context context, final String phoneNumber, final String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(R.string.call)
                .setMessage(context.getString(R.string.call_brewery) + " " + message);
        final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null && telephonyManager.getPhoneType() != 0) {

            builder.setPositiveButton(R.string.call, new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialogInterface, final int n) {
                    final Intent intent = new Intent("android.intent.action.DIAL");
                    intent.setData(Uri.parse("tel:" + Uri.encode("+" + phoneNumber)));
                    context.startActivity(intent);
                }
            });
        }
        builder.setNegativeButton(R.string.cancel, null);
        AlertDialog popupCall = builder.create();
        popupCall.show();
    }

    class BreweryHolder {
        @Getter
        private ImageView loc;
        @Getter
        private ImageView call;
        private TextView name;
        private TextView city;
        private TextView country;
        private TextView updated;

        BreweryHolder(View view) {
            loc = (ImageView) view.findViewById(R.id.imageLoc);
            call = (ImageView) view.findViewById(R.id.imageCall);
            name = (TextView) view.findViewById(R.id.nameBrewery);
            city = (TextView) view.findViewById(R.id.cityBrewery);
            country = (TextView) view.findViewById(R.id.countryBrewery);
            updated = (TextView) view.findViewById(R.id.updatedBrewery);
        }

        public void setHolder(Brewery brewery) {
            name.setText(brewery.getName());
            city.setText(brewery.getCity());
            country.setText(brewery.getCountry());
            updated.setText(brewery.getUpdated());
        }
    }

}
