package image.control.ellouzeandpartners.imagecontrolproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity  implements View.OnClickListener {

    ArrayList<Bitmap> smallImages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         /*
         * This application will for merging number of smaller chunks to make the original
         * So for that we need some smaller image chunks. As I don't have any smaller images,
         * so I programmatically first split an image to get the smaller image chunks
         * and then merge them to get the original one.
         *
         * If you want you can also get some smaller chunks and directly merge them.
         *
         * The below 4 lines of code does the splitting operation
         */

        ImageView image = new ImageView(getApplicationContext());
        image.setImageResource(R.drawable.katewinslet);
        int chunkNumbers = 25;
        smallImages = splitImage(image, chunkNumbers);

        /*
         * This code is to show all the smaller image chunks into a grid structure
         */

        GridView grid = (GridView) findViewById(R.id.gridview);
        grid.setAdapter(new ImageAdapter(this, smallImages));
        grid.setNumColumns(5);

  /*
   * This gets the merge button and registers an click listener on it.
   */

        Button mergeButton = (Button) findViewById(R.id.merge_button);
        mergeButton.setOnClickListener(this);
    }


    /**
     * Splits the source image and show them all into a grid in a new activity
     *
     * @param image The source image to split
     * @param chunkNumbers The target number of small image chunks to be formed from the source image
     * @return ArrayList<bitmap> The resulted smaller image chunks in a List of Bitmaps
     */

    private ArrayList<Bitmap> splitImage(ImageView image, int chunkNumbers) {

        //For the number of rows and columns of the grid to be displayed
        int rows, cols;
        rows = cols = 5;

        //For height and width of the small image chunks
        int chunkHeight, chunkWidth;

        //To store all the small image chunks in bitmap format in this list
        ArrayList<Bitmap> chunkedImages = new ArrayList<Bitmap>(chunkNumbers);

        //Getting the scaled bitmap of the source image
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        rows = cols = (int) Math.sqrt(chunkNumbers);
        chunkHeight = bitmap.getHeight()/rows;
        chunkWidth = bitmap.getWidth()/cols;

        //xCoord and yCoord are the pixel positions of the image chunks
        int yCoord = 0;
        for(int x=0; x<rows; x++){
            int xCoord = 0;
            for(int y=0; y<cols; y++){
                chunkedImages.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }
        return chunkedImages;
    }


    /*
  * This method actually implements the code for merging the small images
  * @see android.view.View.OnClickListener#onClick(android.view.View)
  */

    @Override
    public void onClick(View v) {

        //Get the width and height of the smaller chunks
        int chunkWidth = smallImages.get(0).getWidth();
        int chunkHeight = smallImages.get(0).getHeight();

        //create a bitmap of a size which can hold the complete image after merging
        Bitmap bitmap = Bitmap.createBitmap(chunkWidth * 5, chunkHeight * 5, Bitmap.Config.ARGB_4444);

        //create a canvas for drawing all those small images
        Canvas canvas = new Canvas(bitmap);
        int count = 0;
        for(int rows = 0; rows < 5; rows++){
            for(int cols = 0; cols < 5; cols++){
                canvas.drawBitmap(smallImages.get(count), chunkWidth * cols, chunkHeight * rows, null);
                count++;
            }
        }

  /*
   * The result image is shown in a new Activity
   */

        Intent intent = new Intent(MainActivity.this, MergedImage.class);
        intent.putExtra("merged_image", bitmap);
        startActivity(intent);



    }
}
