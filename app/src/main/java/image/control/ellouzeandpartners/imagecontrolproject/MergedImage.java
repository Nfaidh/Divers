package image.control.ellouzeandpartners.imagecontrolproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class MergedImage extends ActionBarActivity {

    Bitmap bmp;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merged_image);


        img = (ImageView) findViewById(R.id.imageView);

        Bitmap bitmap = getIntent().getParcelableExtra("merged_image");
        img.setImageBitmap(bitmap);


    }



}
