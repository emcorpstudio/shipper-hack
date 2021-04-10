package emcorp.studio.pickpackgo.model;

import android.graphics.drawable.Drawable;

public class Social {

    public int image;
    public Drawable imageDrw;
    public String name;
    public String id;
    public String bin_name;
    public boolean expanded = false;
    public boolean parent = false;

    // flag when item swiped
    public boolean swiped = false;

    public Social() {
    }

    public Social(int image, String name, String bin_name, String id) {
        this.image = image;
        this.name = name;
        this.bin_name = bin_name;
        this.id = id;
    }
}
