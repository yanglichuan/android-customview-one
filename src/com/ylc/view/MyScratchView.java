
package com.ylc.view;

import java.util.ArrayList;
import java.util.Random;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 刮刮乐
 * @author tom
 *
 */
public class MyScratchView extends View {

    public MyScratchView(Context context) {
        super(context);
        init();
    }

    public MyScratchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyScratchView(Context context, AttributeSet attrs) {
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:

                break;
            default:
                break;
        }
        Log.i("kkk", event.getX()+"  "+event.getY());
        invalidate();
        return true;
    }

    public int screenW = 0;
    public int screenH = 0;
    public Path path =new Path();

    @Override
    protected void onDraw(Canvas canvas) {
        screenW = getMeasuredWidth();
        screenH = getMeasuredHeight();
        paint.setXfermode(null);
        // canvas.drawColor(Color.YELLOW);

        int r1 = (int) ((float) screenW / 3f);
        int centerx = r1;
        int centery = 2 * r1;
        paint.setStyle(Style.FILL);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(centerx, centery, r1, paint);

        Bitmap bitmap = Bitmap.createBitmap(screenW, screenH, Config.ARGB_8888);
        Canvas cc = new Canvas(bitmap);
        paint.setColor(Color.YELLOW);
        cc.drawRect(0, 0, screenW, screenH, paint);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
        cc.drawPath(path, paint);

        canvas.drawBitmap(bitmap, 0, 0, paint);
    }
}
