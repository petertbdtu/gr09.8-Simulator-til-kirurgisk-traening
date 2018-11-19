package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class LEDView extends View {

    private boolean isOn;
    private Paint onColor, offColor;

    public LEDView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setup();
    }

    public LEDView(Context context) {
        super(context);

        setup();
    }

    private void setup() {
        isOn = false;
        offColor = new Paint();
        offColor.setARGB(255, 0, 0, 0);
        onColor = new Paint();
        onColor.setARGB(255, 255, 0, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Get the available space
        float radius = (getHeight() < getWidth() ? getHeight() : getWidth()) / 2;
        if (isOn) {
            canvas.drawCircle(radius, radius, radius, onColor);
        } else {
            canvas.drawCircle(radius, radius, radius, offColor);
        }
    }

    public boolean isOn() {
        return isOn;
    }
    public void turnOn() {
        this.isOn = true;
    }
    public void turnOff() {
        this.isOn = false;
    }

    public Paint getOnColor() { return onColor; }
    public void setOnColor(Paint onColor) { this.onColor = onColor; }

    public Paint getOffColor() { return offColor; }
    public void setOffColor(Paint offColor) { this.offColor = offColor; }
}
