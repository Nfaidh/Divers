package image.control.ellouzeandpartners.imagecontrolproject.two;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import image.control.ellouzeandpartners.imagecontrolproject.R;

public class Main2Activity extends ActionBarActivity implements View.OnTouchListener {

    ImageView img;

    Bitmap bitmap;

    // these matrices will be used to move and zoom image
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();

    // we can be in one of these 3 states
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    // remember some things for zooming
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;
    private float d = 0f;
    private float newRot = 0f;
    private float[] lastEvent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        img = (ImageView) findViewById(R.id.imageView2);

       // BitmapFactory.Options options = GetBitmapOptionsOfImageAsync();
       //Bitmap bitmapToDisplay = LoadScaledDownBitmapForDisplayAsync (this.getResources(), options, 500, 500);

        //bitmap = CombineBitmap();
       /* try {
            bitmap = horizontalCombine();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


       // img.setImageBitmap(bitmapToDisplay);


        try {
            bitmap = horizontalCombine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        img.setImageBitmap(bitmap);

        img.setOnTouchListener(this);


    }



    private Bitmap CombineBitmap() throws IOException {
        Bitmap c = null;
        Bitmap s = null;
        Bitmap newBitmap = null;

        //c = drawableToBitmap(getResources().getDrawable(R.drawable.center_right));

           // s = drawableToBitmap(getResources().getDrawable(R.drawable.center_left));




          /*  int w;
            if(c.getWidth() >= s.getWidth()){
                w = c.getWidth();
            }else{
                w = s.getWidth();
            }

            int h;
            if(c.getHeight() >= s.getHeight()){
                h = c.getHeight();
            }else{
                h = s.getHeight();
            }

            Bitmap.Config config = c.getConfig();
            if(config == null){
                config = Bitmap.Config.ARGB_8888;
            }*/

        int width, height = 0;

        if(c.getWidth() > s.getWidth()) {
            width = c.getWidth() + s.getWidth();
            height = c.getHeight();
        } else {
            width = s.getWidth() + s.getWidth();
            height = c.getHeight();
        }

        newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(newBitmap);

        comboImage.drawBitmap(c, 0f, 0f, null);
        comboImage.drawBitmap(s, c.getWidth(), 0f, null);



      /*  newBitmap = Bitmap.createBitmap(w, h, config);
            Canvas newCanvas = new Canvas(newBitmap);

            newCanvas.drawBitmap(c, 0f, 0f, null);

            Paint paint = new Paint();
            paint.setAlpha(125);
            newCanvas.drawBitmap(s, 40, 0, paint);*/



        return newBitmap;
    }

    private Bitmap horizontalCombine() throws IOException {

        Bitmap top= null;
        Bitmap bottom = null;
        Bitmap res = null;

       // right = drawableToBitmap(getResources().getDrawable(R.drawable.center_right));

        //top
        BitmapFactory.Options options_top = GetBitmapOptionsOfImageAsync(R.drawable.ic_top_p1);
        top = LoadScaledDownBitmapForDisplayAsync (this.getResources(),R.drawable.ic_top_p1, options_top, 320, 240);

        //bottom
        BitmapFactory.Options options_bottom = GetBitmapOptionsOfImageAsync(R.drawable.ic_bottom_p1);
        bottom = LoadScaledDownBitmapForDisplayAsync (this.getResources(),R.drawable.ic_bottom_p1, options_bottom,320, 240);




        //Drawable right = getAssetImage(this,"ic_right_bottom");
        //Drawable left = getAssetImage(this,"ic_left_bottom");

        //c = drawableToBitmap(right);
       // s=drawableToBitmap(left);



       /* int width = left.getWidth() + right.getWidth();
        int height = Math.max(left.getHeight(), right.getHeight());
        leftRight = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(leftRight);

        canvas.drawBitmap(top_left,0,0,new Paint());
        canvas.drawBitmap(top_right,top_left.getWidth(),0,new Paint());

        return leftRight;*/

        int width =top.getWidth()+bottom.getWidth();
        int height = top.getHeight() + bottom.getHeight();
        res = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(res);

        canvas.drawBitmap(top,0,0,null);
        canvas.drawBitmap(bottom,0,640,null);


        return  res;
    }


    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Drawable getAssetImage(Context context, String filename) throws IOException {
        AssetManager assets = context.getResources().getAssets();
        InputStream buffer = new BufferedInputStream((assets.open("drawable/" + filename + ".png")));
        Bitmap bitmap = BitmapFactory.decodeStream(buffer);
        return new BitmapDrawable(context.getResources(), bitmap);
    }


    // get dimensions of image
    public BitmapFactory.Options GetBitmapOptionsOfImageAsync(int id_resource)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;


        // The result will be null because InJustDecodeBounds == true.
        //Bitmap result=  new  BitmapFactory.DecodeResourceAsync(Resources, getResources().getDrawable(R.drawable.katewinslet), options);
        Bitmap result = BitmapFactory.decodeResource(getApplicationContext().getResources(),id_resource,options);

        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;

        //_originalDimensions.Text = string.Format("Original Size= {0}x{1}", imageWidth, imageHeight);

        Log.e("GetBitmapOptions","height "+imageHeight+" width "+imageWidth);

        return options;
    }


    // scaling down the image
    // image 4000x3000  will be scaled down to 150x150

    public static int CalculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // Raw height and width of image
        float height = options.outHeight;
        float width = options.outWidth;
        double inSampleSize = 1D;

        if (height > reqHeight || width > reqWidth)
        {
            int halfHeight = (int)(height / 2);
            int halfWidth = (int)(width / 2);

            // Calculate a inSampleSize that is a power of 2 - the decoder will use a value that is a power of two anyway.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth)
            {
                inSampleSize *= 2;
            }
        }

        return (int)inSampleSize;
    }




    public Bitmap LoadScaledDownBitmapForDisplayAsync(Resources res, int id_resource, BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // Calculate inSampleSize
        options.inSampleSize = CalculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return  BitmapFactory.decodeResource(res,id_resource , options);
    }


    //-------------------------------------- Zooming
    public boolean onTouch(View v, MotionEvent event) {
        // handle touch events here
        ImageView view = (ImageView) v;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    float dx = event.getX() - start.x;
                    float dy = event.getY() - start.y;
                    matrix.postTranslate(dx, dy);
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = (newDist / oldDist);
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                    if (lastEvent != null && event.getPointerCount() == 3) {
                        newRot = rotation(event);
                        float r = newRot - d;
                        float[] values = new float[9];
                        matrix.getValues(values);
                        float tx = values[2];
                        float ty = values[5];
                        float sx = values[0];
                        float xc = (view.getWidth() / 2) * sx;
                        float yc = (view.getHeight() / 2) * sx;
                        matrix.postRotate(r, tx + xc, ty + yc);
                    }
                }
                break;
        }

        view.setImageMatrix(matrix);
        return true;
    }

    /**
     * Determine the space between the first two fingers
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    /**
     * Calculate the mid point of the first two fingers
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * Calculate the degree to be rotated by.
     *
     * @param event
     * @return Degrees
     */
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }


}

