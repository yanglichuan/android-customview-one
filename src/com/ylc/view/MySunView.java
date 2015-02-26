
package com.ylc.view;

import java.util.ArrayList;
import java.util.Random;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.Shader;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MySunView extends View {

    public MySunView(Context context) {
        super(context);
        init();
    }

    public MySunView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MySunView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArrayList<Integer> datas = new ArrayList<Integer>();
    public ArrayList<Integer> colors = new ArrayList<Integer>();
    // 这是默认的
    private int suggestW = 200;
    private int suggestH = 200;
    private Paint paint;
    public ObjectAnimator animator;

    @SuppressLint("NewApi")
    public void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL);
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(3);

        datas.add(50);
        datas.add(80);
        datas.add(20);
        datas.add(30);
        datas.add(90);
        Random random = new Random(System.currentTimeMillis());
        colors.add(0xff << 24 | random.nextInt(0xffffff));
        colors.add(0xff << 24 | random.nextInt(0xffffff));
        colors.add(0xff << 24 | random.nextInt(0xffffff));
        colors.add(0xff << 24 | random.nextInt(0xffffff));
        colors.add(0xff << 24 | random.nextInt(0xffffff));

    }

    public Handler handler = new Handler();
    public int animValue = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) (suggestW + getPaddingLeft()
                    + getPaddingRight());
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    /**
     * Determines the height of this view
     * 
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = suggestH + getPaddingTop()
                    + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by
                // measureSpec
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    public int screenW = 0;
    public int screenH = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        screenW = getMeasuredWidth();
        screenH = getMeasuredHeight();
        
        int r = (int) ((float)screenW / 4f);
        int r2 = (int) ((float)screenW / 2f);
        int r3 = (int) ((float)screenW / 4f) + 30;
        int centerx = screenH/2;
        int centery = screenH/2;
        float length = 40f;
        
        canvas.drawCircle(centerx, centery, r, paint);
        double deg0  = Math.toRadians(0);
        double deg45  = Math.toRadians(45);
        double deg90  = Math.toRadians(90);
        double deg135  = Math.toRadians(135);
        double deg180  = Math.toRadians(180);
        double deg225  = Math.toRadians(225);
        double deg270  = Math.toRadians(270);
        double deg315  = Math.toRadians(315);

        PointF pp0_begin = new PointF(centerx+r3, centery);
        PointF pp45_begin = new PointF((float)(centerx+ r3*Math.cos(deg45)),
                (float)(centery+ r3*Math.sin(deg45)));
        PointF pp90_begin = new PointF((float)(centerx+ r3*Math.cos(deg90)), 
                (float)(centery + r3*Math.sin(deg90)));
        PointF pp135_begin = new PointF((float)(centerx+ r3*Math.cos(deg135)),
                (float)(centery+ r3*Math.sin(deg135)));
        PointF pp180_begin = new PointF((float)(centerx+ r3*Math.cos(deg180)),
                (float)(centery + r3*Math.sin(deg180)));
        PointF pp225_begin = new PointF((float)(centerx+ r3*Math.cos(deg225)),
                (float)(centery+ r3*Math.sin(deg225)));
        PointF pp275_begin = new PointF((float)(centerx+ r3*Math.cos(deg270)),
                (float)(centery + r3*Math.sin(deg270)));
        PointF pp315_begin = new PointF((float)(centerx+ r3*Math.cos(deg315)), 
                (float)(centery+ r3*Math.sin(deg315)));
        
        PointF pp0_end = new PointF(centerx+r2, centery);
        PointF pp45_end = new PointF((float)(centerx+ r2*Math.cos(deg45)),
                (float)(centery+ r2*Math.sin(deg45)));
        PointF pp90_end = new PointF((float)(centerx+ r2*Math.cos(deg90)), 
                (float)(centery + r2*Math.sin(deg90)));
        PointF pp135_end = new PointF((float)(centerx+ r2*Math.cos(deg135)),
                (float)(centery+ r2*Math.sin(deg135)));
        PointF pp180_end = new PointF((float)(centerx+ r2*Math.cos(deg180)),
                (float)(centery + r2*Math.sin(deg180)));
        PointF pp225_end = new PointF((float)(centerx+ r2*Math.cos(deg225)),
                (float)(centery+ r2*Math.sin(deg225)));
        PointF pp275_end = new PointF((float)(centerx+ r2*Math.cos(deg270)),
                (float)(centery + r2*Math.sin(deg270)));
        PointF pp315_end = new PointF((float)(centerx+ r2*Math.cos(deg315)), 
                (float)(centery+ r2*Math.sin(deg315)));
        
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawLine(pp0_begin.x, pp0_begin.y, pp0_end.x, pp0_end.y, paint);
        canvas.drawLine(pp45_begin.x, pp45_begin.y,pp45_end.x, pp45_end.y, paint);
        canvas.drawLine(pp90_begin.x, pp90_begin.y,pp90_end.x, pp90_end.y, paint);
        canvas.drawLine(pp135_begin.x, pp135_begin.y,pp135_end.x, pp135_end.y, paint);
        canvas.drawLine(pp180_begin.x, pp180_begin.y,pp180_end.x, pp180_end.y, paint);
        canvas.drawLine(pp225_begin.x, pp225_begin.y,pp225_end.x, pp225_end.y, paint);
        canvas.drawLine(pp275_begin.x, pp275_begin.y,pp275_end.x, pp275_end.y, paint);
        canvas.drawLine(pp315_begin.x, pp315_begin.y,pp315_end.x, pp315_end.y, paint);
    }
}
