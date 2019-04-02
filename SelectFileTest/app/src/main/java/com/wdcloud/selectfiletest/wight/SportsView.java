package com.wdcloud.selectfiletest.wight;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Umbrella on 2019/4/1.
 */

public class SportsView extends View{
    private float progress;

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public SportsView(Context context) {
        super(context);
    }

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SportsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        RectF rectF = new RectF(100,150,300,300);
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
//        paint.setAntiAlias(true);
//        canvas.drawCircle(getWidth()/2,getHeight()/2,100,paint);
        Path path = new Path();
        path.moveTo(80, 200);// 此点为多边形的起点
         path.lineTo(120, 250);
         path.lineTo(80, 250);
         path.close();
        // 使这些点构成封闭的多边形
         canvas.drawPath(path, paint);
//        ---------------------
//                作者：刘某人程序员
//        来源：CSDN
//        原文：https://blog.csdn.net/qq_26787115/article/details/50466655
//        版权声明：本文为博主原创文章，转载请附上博文链接！
//        canvas.drawLine(100,150,300,300,paint);
//        canvas.drawRect(100,150,300,300,paint);
//        canvas.drawArc(rectF,90,180,true,paint);
//        canvas.drawArc(rectF,200,progress*2.7f,false,paint);
//            canvas.drawRect(100,150,300,300,paint);
    }
//    public void startAnimation(View view)
//    {
//        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0, 65);
//        animator.start();
//    }
}
