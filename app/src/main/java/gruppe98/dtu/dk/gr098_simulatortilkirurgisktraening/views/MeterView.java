package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MeterView extends View {

    private float interval, value;
    private Paint emptyColor, filledColor;

    public MeterView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setup();
    }

    public MeterView(Context context) {
        super(context);

        setup();
    }

    private void setup() {
        interval = 100;
        value = 0;
        emptyColor = new Paint();
        emptyColor.setARGB(255,255,255,255);
        filledColor = new Paint();
        filledColor.setARGB(255,255,0,0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getHeight();
        int width = getWidth();

        float middle = height * value / interval;

        canvas.drawRect(0,0, width, middle, emptyColor);
        canvas.drawRect(0, middle, width, height, filledColor);

    }

    public float getInterval() { return interval; }
    public void setInterval(float interval) { this.interval = interval; }

    public float getValue() { return value; }
    public void setValue(float value) {
        this.value = value;
    }

    public Paint getEmptyColor() { return emptyColor; }
    public void setEmptyColor(Paint emptyColor) { this.emptyColor = emptyColor; }

    public Paint getFilledColor() { return filledColor; }
    public void setFilledColor(Paint filledColor) { this.filledColor = filledColor; }
}
