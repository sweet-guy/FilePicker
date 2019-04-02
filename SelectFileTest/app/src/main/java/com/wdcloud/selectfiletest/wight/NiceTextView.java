package com.wdcloud.selectfiletest.wight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.wdcloud.selectfiletest.R;

/**
 * Created by Umbrella on 2019/3/28.
 */

@SuppressLint("AppCompatCustomView")
public class NiceTextView extends TextView{

    private int alinecolor;
    private int alinewidth;
    private float alinehight;
    private Paint paint;
    private Boolean show=true;
    public NiceTextView(Context context) {
        super(context);
    }

    public NiceTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NiceTextView);
        alinecolor = typedArray.getColor(R.styleable.NiceTextView_text_lineColor, Color.parseColor("#FFD700"));
        alinewidth = typedArray.getInt(R.styleable.NiceTextView_text_lineWidth, 30);
        alinehight = typedArray.getDimension(R.styleable.NiceTextView_text_lineHight,  TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        typedArray.recycle();
    }

    public NiceTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(alinecolor);
        int width = getWidth();
        int height = getHeight();
        if(show==true)
        {
            canvas.drawRect(0,getHeight()-alinehight,getWidth(),getHeight()+120,paint);
        }
    }
    public void setTextWidth(int width)
    {
        alinewidth=width;
    }
    public void setShowLine(Boolean isshow)
    {
        show=isshow;
        invalidate();
    }
}
