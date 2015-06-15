package at.tba.treasurehunt.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.util.AttributeSet;

/**
 * Created by dAmihl on 24.05.15.
 */
public class RectangleDrawView extends View {
    Paint paint = new Paint();
    int currentColor = Color.BLACK;


    public RectangleDrawView(Context context) {
        super(context);
    }

    public RectangleDrawView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RectangleDrawView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    public void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        /*
        paint.setColor(currentColor);
        paint.setStrokeWidth(3);
        int rectangleWidth = (int) (canvas.getWidth() * 0.5);
        int rectangleHeight = (int) (canvas.getHeight() * 0.05);
        int topleftx = canvas.getWidth()/2 - rectangleWidth/2;
        int toplefty = 0 + 30;
        int bottomrightx = canvas.getWidth()/2 + rectangleWidth /2;
        int bottomrighty = toplefty + rectangleHeight;
        */

        /*
        Test fullscreen color overlay with alpha
         */
        paint.setColor(currentColor);
        paint.setAlpha(80);
        paint.setStrokeWidth(3);

        int topleftx = 0;
        int toplefty = 0;
        int bottomrightx = canvas.getWidth();
        int bottomrighty = canvas.getHeight();

        canvas.drawRect(topleftx, toplefty, bottomrightx, bottomrighty, paint);
    }

    public void setRectangleColor(int color){
        this.currentColor = color;
    }

    public void setRectangleColor(int r, int g, int b){
        this.currentColor = Color.rgb(r, g, b);
    }

}
