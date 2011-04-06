package net.retsat1.starlab.smssender.ui;

import android.graphics.Bitmap;

/**
 * Just trying implement better listView
 * 
 * @author mario
 * 
 */
// TODO Delete
public class ItemTwoLine {
    private Bitmap image;
    private String texte1;
    private String texte2;
    private boolean cb1;

    public ItemTwoLine(Bitmap image, String texte1, String texte2, boolean cb1) {
        super();
        this.image = image;
        this.texte1 = texte1;
        this.texte2 = texte2;
        this.cb1 = cb1;

    }

    public Bitmap getImage() {
        return image;
    }

    public String getTexte1() {
        return texte1;
    }

    public String getTexte2() {
        return texte2;
    }

    public boolean getCb1() {
        return cb1;
    }

    public void setCb1(boolean state) {
        this.cb1 = state;
    }

}