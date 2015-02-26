
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
import android.graphics.Shader;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyHistogramView extends View {

    public MyHistogramView(Context context) {
        super(context);
        init();
    }

    public MyHistogramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyHistogramView(Context context, AttributeSet attrs) {
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
        paint.setColor(Color.BLUE);
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

        animator = new ObjectAnimator().ofInt(new Object(), "bbb", 0, 100);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animValue = (Integer) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(500);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animator.start();
            }
        }, 1000);
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
        int count = 2 * datas.size() + 1;
        int perW = (int) ((float) screenW / (float) count);
        Log.i("test", count + " ds");
        Log.i("test", perW + " dddds");

        for (int i = 0; i < datas.size(); i++) {
            int value = datas.get(i);
            Random random = new Random(System.currentTimeMillis());
            int tempColor = colors.get(i);
            paint.setColor(tempColor);
            LinearGradient lg = new LinearGradient((2 * i + 1) * perW, screenH
                    * ((float) value / 100f), (2 * i + 2) * perW, screenH * ((float) value / 100f),
                    new int[] {
                            Color.GRAY, tempColor, Color.GRAY
                    }, new float[] {
                            0, 0.5f, 1f
                    }, Shader.TileMode.MIRROR); //
            paint.setShader(lg);
            canvas.drawRect((2 * i + 1) * perW,
                    screenH - (screenH - screenH * ((float) value / 100f))
                            * ((float) animValue / 100f),
                    (2 * i + 2) * perW, screenH, paint);

            paint.setTextSize(50);
            paint.setColor(Color.GREEN);
            canvas.drawText(value + "%", (2 * i + 1) * perW,
                    screenH - (screenH - screenH * ((float) value / 100f))
                            * ((float) animValue / 100f) - 30, paint);
        }
        paint.setShader(null);
        paint.setColor(Color.GRAY);
        canvas.drawLine(0, screenH - 1, screenW, screenH - 1, paint);
        paint.setColor(Color.WHITE);
        canvas.drawLine(0, screenH, screenW, screenH, paint);
    }
}
