package www.ittepic.edu.reloj;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Calendar;

public class Reloj extends View{
    private int height, width = 0;
    private int padding =0;
    private int fontsize =0;
    private int numeralSpacing =0;
    private int handTruncation, hourHandTruncation =0;
    private int radius=0;
    private Paint paint;
    private boolean exists;
    private int[]numbers ={1,2,3,4,5,6,7,8,9,10,11,12};
    private Rect rect = new Rect();
    public Reloj(Context context) {
        super(context);
    }

    public Reloj(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Reloj(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Reloj(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initClock(){
        height = getHeight();
        width = getWidth();
        padding = numeralSpacing + 50;
        fontsize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,13, getResources().getDisplayMetrics());
        int min = Math.min(height, width);
        radius = min / 2 - padding;
        handTruncation  = min/20;
        hourHandTruncation = min /7;
        paint = new Paint();
        exists = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!exists){
            initClock();
        }

        canvas.drawColor(Color.BLACK);
        drawCircle(canvas);
        drawCenter(canvas);
        drawNumbers(canvas);
        drawHands(canvas);

        postInvalidateDelayed(500);
    }

    private void drawHands(Canvas canvas, double loc, boolean isHour){
        double angle = Math.PI + loc / 30 - Math.PI/2;
        int handRadius = isHour? radius-handTruncation-hourHandTruncation: radius-handTruncation;
        canvas.drawLine(width/2, height/2, (float)(width/2+Math.cos(angle)*handRadius),
                (float) (height/2 +Math.sin(angle)*handRadius), paint);
    }

    private void drawHands(Canvas canvas) {
        Calendar c = Calendar.getInstance();
        float hour = c.get(Calendar.HOUR_OF_DAY);
        hour = hour > 12 ? hour - 12 : hour;
        drawHands(canvas, (hour + c.get(Calendar.MINUTE) / 60) * 5f, true);
        drawHands(canvas, c.get(Calendar.MINUTE), false);
        drawHands(canvas, c.get(Calendar.SECOND), false);
    }

    private void drawNumbers(Canvas canvas) {
        paint.setTextSize(fontsize);
        for (int n : numbers) {
            String tmp = String.valueOf(n);
            paint.getTextBounds(tmp, 0, tmp.length(), rect);
            double angle = Math.PI / 6 * (n - 3);
            int x = (int) (width / 2 + Math.cos(angle) * radius - rect.width() / 2);
            int y = (int) (width / 2 + Math.sin(angle) * radius - rect.width() / 2);
            canvas.drawText(tmp, x, y, paint);

        }
    }

    private void drawCenter(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2, height /2, 12, paint);
    }


    private void drawCircle(Canvas canvas) {

        paint.reset();
        paint.setColor(getResources().getColor(android.R.color.white));
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawCircle(width/ 2, height/2, radius + padding -10, paint);

    }


}
