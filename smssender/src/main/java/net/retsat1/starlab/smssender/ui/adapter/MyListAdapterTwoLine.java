package net.retsat1.starlab.smssender.ui.adapter;

import java.util.List;

import net.retsat1.starlab.smssender.R;
import net.retsat1.starlab.smssender.ui.ItemTwoLine;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author mario
 * 
 */
// TODO delete
public class MyListAdapterTwoLine extends BaseAdapter {

    private List<ItemTwoLine> mListe;
    private LayoutInflater inflater;

    public MyListAdapterTwoLine(Context context, List<ItemTwoLine> mListe) {
        inflater = LayoutInflater.from(context);
        this.mListe = mListe;
    }

    @Override
    public int getCount() {
        return mListe.size();
    }

    @Override
    public Object getItem(int position) {
        return mListe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        RelativeLayout item;
        ImageView image;
        TextView texte1;
        TextView texte2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.list_item2, null);

        if (position % 2 == 0) {
            int myColor = Color.parseColor("#88FFFFFF");
            RelativeLayout itemRecup = (RelativeLayout) convertView.findViewById(R.id.item);
            itemRecup.setBackgroundColor(myColor);
        } else {
            int myColor = Color.parseColor("#88000000");
            RelativeLayout itemRecup = (RelativeLayout) convertView.findViewById(R.id.item);
            itemRecup.setBackgroundColor(myColor);
        }

        ImageView imageRecup = (ImageView) convertView.findViewById(R.id.icon);
        imageRecup.setImageBitmap(mListe.get(position).getImage());
        imageRecup.setPadding(5, 5, 0, 5);
        TextView texte1Recup = (TextView) convertView.findViewById(R.id.firstLine);
        texte1Recup.setText(mListe.get(position).getTexte1());
        TextView texte2Recup = (TextView) convertView.findViewById(R.id.secondLine);
        texte2Recup.setText(mListe.get(position).getTexte2());

        // final CheckBox cb1Recup = (CheckBox)
        // convertView.findViewById(R.id.cb1);
        // cb1Recup.setChecked(mListe.get(position).getCb1());
        // cb1Recup.setOnClickListener(new OnClickListener() {
        //
        // public void onClick(View arg0) {
        //
        // if (cb1Recup.isChecked() == true) {
        // mListe.get(position).setCb1(true);
        // } else {
        // mListe.get(position).setCb1(false);
        // }
        //
        // }
        // });
        if (mListe.get(position).getTexte2().compareTo("") == 0) {
            texte2Recup.setHeight(0);
            texte2Recup.setWidth(0);
        }
        return convertView;

    }

}