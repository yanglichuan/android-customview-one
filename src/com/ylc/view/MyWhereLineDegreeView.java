
package com.ylc.view;

import java.util.ArrayList;
import java.util.Collections;
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
import android.graphics.Path;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.graphics.PointF;
import android.graphics.Shader;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyWhereLineDegreeView extends View {
    public MyWhereLineDegreeView(Context context) {
        super(context);
        init();
    }

    public MyWhereLineDegreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyWhereLineDegreeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArrayList<Integer> datas = new ArrayList<Integer>();
    public ArrayList<Integer> colors = new ArrayList<Integer>();
    public int max = 0;
    public int min = 0;
    // 这是默认的
    private int suggestW = 200;
    private int suggestH = 200;
    private Paint paint;
    public ObjectAnimator animator;

    class CicleNode {
        public int x;
        public int y;
    }

    ArrayList<CicleNode> nodes = new ArrayList<MyWhereLineDegreeView.CicleNode>();

    @SuppressLint("NewApi")
    public void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Style.STROKE);
        paint.setStrokeCap(Cap.ROUND);
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(10);
        datas.add(7);
        datas.add(5);
        datas.add(5);
        datas.add(10);
        datas.add(7);
        datas.add(9);
        max = Collections.max(datas);
        min = Collections.min(datas);

        Random random = new Random(System.currentTimeMillis());
        colors.add(0xff << 24 | random.nextInt(0xffffff));
        colors.add(0xff << 24 | random.nextInt(0xffffff));
        colors.add(0xff << 24 | random.nextInt(0xffffff));
        colors.add(0xff << 24 | random.nextInt(0xffffff));
        colors.add(0xff << 24 | random.nextInt(0xffffff));
        colors.add(0xff << 24 | random.nextInt(0xffffff));
        colors.add(0xff << 24 | random.nextInt(0xffffff));

        animator = ObjectAnimator.ofFloat(new Object(), "mimi", 0, datas.size() + 2);
        animator.setDuration(5000);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animValue = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animator.start();
            }
        }, 0);
    }

    public Handler handler = new Handler();
    public float animValue = 0;

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

    public int marginLeft = 50;
    public int marginRight = 50;
    public int marginTop = 10;
    public int marginBottom = 10;

    public Path path = new Path();

    float oldx = 0;
    float oldy = 0;

    @Override
    protected void onDraw(Canvas canvas) {

        screenW = getMeasuredWidth();
        screenH = getMeasuredHeight();
        paint.setStyle(Style.FILL);
        paint.setStrokeCap(Cap.ROUND);
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(20);

        if (animValue > 0) {
            int size = datas.size();

            // 一个数的整数部分和小数部分
            int a = (int) Math.floor(animValue);
            a = (Integer) (a > (size - 1) ? (size - 1) : a);
            float b = animValue - a;

            int sumW = screenW - marginLeft - marginRight;
            int sumH = screenH - marginTop - marginBottom;
            int perW = sumW / (size - 1);
            int perH = sumH / (max - min);

            int x = marginLeft + a * perW;
            int y = (max - datas.get(a)) * perH + marginTop;

            CicleNode n = null;
            if (a == 0) {
                nodes.clear();
                path.moveTo(x, y);
                oldx = x;
                oldy = y;
                n = new CicleNode();
                n.x = x;
                n.y = y;
            } else {
                path.lineTo(x, y);
                oldx = x;
                oldy = y;
                n = new CicleNode();
                n.x = x;
                n.y = y;
            }
            nodes.add(n);

            if (b > 0 && a != (size - 1)) {
                float midlex = oldx + b * perW;
                float midley = oldy - (datas.get(a + 1) - datas.get(a)) * perH * b;
                path.lineTo(midlex, midley);
            }

            paint.setStyle(Style.STROKE);
            paint.setStrokeCap(Cap.ROUND);
            paint.setColor(Color.CYAN);
            paint.setStrokeWidth(5);

            canvas.drawPath(path, paint);

            paint.setStyle(Style.FILL);
            paint.setStrokeCap(Cap.ROUND);
            paint.setColor(Color.YELLOW);
            paint.setStrokeWidth(20);
            for (int i = 0; i < nodes.size(); i++) {
                canvas.drawCircle(nodes.get(i).x, nodes.get(i).y, 10, paint);
            }
        }
    }
}
