package com.zozocab.app.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Blurpixel on 4/18/2016.
 */
public class Circle extends View {

    private static final int START_ANGLE_POINT = 0;

    private float angle;

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);

        final int strokeWidth = 30;

        //Initial Angle (optional, it can be zero)
        angle = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width=(float)getWidth();
        float height=(float)getHeight();
        float radius;
        if (width>height)
        {
            radius=height/2.6f;
        }
        else
        {
            radius=width/2.6f;
        }
        Path path=new Path();
       // path.addCircle(width, height, radius, Path.Direction.CW);
        Paint paint=new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(30);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        float center_x, center_y;
        center_x = width/2;
        center_y = height/2;
        final RectF rect=new RectF();

        paint.setColor(Color.RED);
        rect.set(center_x - radius, center_y - radius, center_x + radius, center_y + radius);
        float angle2 = 360;
        canvas.drawArc(rect, START_ANGLE_POINT, angle2, false, paint);

        paint.setColor(Color.WHITE);
        rect.set(center_x - radius, center_y - radius, center_x + radius, center_y + radius);
        canvas.drawArc(rect, START_ANGLE_POINT, angle, false, paint);

        //drawing another circle [outer circle]
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        rect.set(center_x-(radius+20f),center_y-(radius+20f),center_x+(radius+20f),center_y+(radius+20f));
        canvas.drawArc(rect, START_ANGLE_POINT, angle2, false, paint);

        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        rect.set(center_x-(radius-20f),center_y-(radius-20f),center_x+(radius-20f),center_y+(radius-20f));
        canvas.drawArc(rect, START_ANGLE_POINT, angle2, false, paint);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
