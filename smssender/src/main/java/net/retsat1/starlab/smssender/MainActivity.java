package net.retsat1.starlab.smssender;

import java.util.ArrayList;
import java.util.List;

import net.retsat1.starlab.smssender.ui.ItemTwoLine;
import net.retsat1.starlab.smssender.ui.adapter.MyListAdapterTwoLine;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * Just trying do something witch List.
 * 
 * @author mario
 */
// TODO delete after 1.0
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.itinerary);
        ListView listView = (ListView) findViewById(R.id.list_twoligne_itineraire);
        List<ItemTwoLine> mListe = new ArrayList<ItemTwoLine>();

        mListe.add(new ItemTwoLine(BitmapFactory.decodeResource(getResources(), android.R.drawable.arrow_down_float), "Archives municipales", "", false));
        mListe.add(new ItemTwoLine(BitmapFactory.decodeResource(getResources(), android.R.drawable.arrow_up_float), "Cabinet du Maire", "", true));
        mListe.add(new ItemTwoLine(BitmapFactory.decodeResource(getResources(), android.R.drawable.arrow_down_float), "Centre d'action sociale", "", false));
        mListe.add(new ItemTwoLine(BitmapFactory.decodeResource(getResources(), android.R.drawable.arrow_up_float), "Communication", "", false));
        mListe.add(new ItemTwoLine(BitmapFactory.decodeResource(getResources(), android.R.drawable.arrow_down_float), "Conseil Municipal des Enfants", "",
                false));
        mListe.add(new ItemTwoLine(BitmapFactory.decodeResource(getResources(), android.R.drawable.arrow_up_float), "Coordination enfance", "", false));
        mListe.add(new ItemTwoLine(BitmapFactory.decodeResource(getResources(), android.R.drawable.arrow_down_float),
                "Direction générale des services municipaux", "", false));
        mListe.add(new ItemTwoLine(BitmapFactory.decodeResource(getResources(), android.R.drawable.arrow_up_float), "Espace Culturel", "", false));
        mListe.add(new ItemTwoLine(BitmapFactory.decodeResource(getResources(), android.R.drawable.arrow_down_float), "Location de salle", "", false));
        mListe.add(new ItemTwoLine(BitmapFactory.decodeResource(getResources(), android.R.drawable.arrow_up_float), "Mairie", "", false));
        mListe.add(new ItemTwoLine(BitmapFactory.decodeResource(getResources(), android.R.drawable.arrow_down_float), "Mairie annexe", "", false));
        mListe.add(new ItemTwoLine(BitmapFactory.decodeResource(getResources(), android.R.drawable.arrow_up_float), "Marchés publiques", "", false));
        mListe.add(new ItemTwoLine(BitmapFactory.decodeResource(getResources(), android.R.drawable.arrow_down_float), "Police municipale", "", false));
        mListe.add(new ItemTwoLine(BitmapFactory.decodeResource(getResources(), android.R.drawable.arrow_up_float), "Ressources humaine", "", false));
        mListe.add(new ItemTwoLine(BitmapFactory.decodeResource(getResources(), android.R.drawable.arrow_down_float), "Services techniques", "", false));

        MyListAdapterTwoLine adapter = new MyListAdapterTwoLine(this, mListe);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {

            }
        });
    }

}