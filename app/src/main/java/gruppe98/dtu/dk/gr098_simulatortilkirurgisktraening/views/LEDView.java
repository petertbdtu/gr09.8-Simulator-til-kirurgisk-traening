package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class LEDView extends View {

    private Paint color;

    public LEDView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setup();
    }

    public LEDView(Context context) {
        super(context);

        setup();
    }

    private void setup() {
        color = new Paint();
        color.setARGB(255,255,0,0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Get the available space
        float radius = (getHeight() < getWidth() ? getHeight() : getWidth()) / 2;
        canvas.drawCircle(radius, radius, radius, color);
    }

    public Paint getColor() { return color; }
    public void setColor(Paint color) { this.color = color; }
}
