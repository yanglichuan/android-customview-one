
package com.ylc.view;

import java.util.ArrayList;
import java.util.Random;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

public class MyHealthDialView extends View {

    public MyHealthDialView(Context context) {
        super(context);
        init();
    }

    public MyHealthDialView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyHealthDialView(Context context, AttributeSet attrs) {
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
        this.setRotation(-90);
        
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
        screenW = getMeasuredWidth();
        screenH = getMeasuredHeight();
        
         rrr = (int) ((float) screenW / 2f) - 50;
         ignoreDegree = (int) (Math.asin(lineWidth/4f/rrr)*(180f/Math.PI)); 
         fullDegree = 360-4*ignoreDegree - 4;
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

    
    private int lineWidth = 48 ;
    private int rrr = 0;
    private int ignoreDegree = 0;
    private int fullDegree = 360;
    private float nowDegree = 0.75f;
    @Override
    protected void onDraw(Canvas canvas) {
        int centerx = screenW / 2;
        int centery = screenH / 2;

        paint.setStyle(Style.FILL);
        paint.setColor(Color.GRAY);
        canvas.drawCircle(centerx, centery, rrr, paint);
        
//        paint.setStyle(Style.FILL);
//        paint.setColor(Color.CYAN);
//        paint.setTextSize(50);
//        canvas.save();
//        canvas.rotate(90f,centerx,centery);
//        canvas.drawText(((int)nowDegree*100)+"%", centerx, centery, paint);
//        canvas.restore();

        SweepGradient mSweepGradient = new SweepGradient(centerx, centery, new int[]
        {
                Color.CYAN,Color.BLUE, Color.RED
        },new float[]{0.0f, 0.5f, 1.0f});
        paint.setStyle(Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(lineWidth);
        paint.setStrokeCap(Cap.ROUND);
        
        paint.setShader(mSweepGradient);
        RectF oval = new RectF(centerx - rrr, centery - rrr, centerx + rrr, centery + rrr);
        canvas.drawArc(oval, 2*ignoreDegree+2, fullDegree*nowDegree, false, paint);
        paint.setShader(null);
        
        
    }
    
    
    /**
     * 设置程度
     * @param ft
     */
    public void setDegree(float ft){
        nowDegree = ft;
    }
    
}
