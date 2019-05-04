package com.mfzj.parttimer.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

public class CustomRoundAngleImageView extends AppCompatImageView {
    float width, height;

    public CustomRoundAngleImageView(Context context) {
        this(context, null);
        init(context, null);
    }

    public CustomRoundAngleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public CustomRoundAngleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int size=15;

        if (width >= size && height > size) {
            Path path = new Path();
            //四个圆角
            path.moveTo(size, 0);
            path.lineTo(width - size, 0);
            path.quadTo(width, 0, width, size);
            path.lineTo(width, height - size);
            path.quadTo(width, height, width - size, height);
            path.lineTo(size, height);
            path.quadTo(0, height, 0, height - size);
            path.lineTo(0, size);
            path.quadTo(0, 0, size, 0);

            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }

}
