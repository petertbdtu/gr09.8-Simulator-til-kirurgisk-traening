package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MeterView extends View {

    private float interval, vaerdi;
    private Paint tomFarve, fyldFarve;

    public MeterView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setup();
    }

    public MeterView(Context context) {
        super(context);

        setup();
    }

    private void setup() {
        this.interval = 100;
        this.vaerdi = 0;
        this.tomFarve = new Paint();
        this.tomFarve.setARGB(0, 0, 0, 0);
        this.fyldFarve = new Paint();
        this.fyldFarve.setARGB(255, 255, 0, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getHeight();
        int width = getWidth();

        float middle = height - (height * vaerdi / interval);

        canvas.drawRect(0, 0, width, middle, tomFarve);
        canvas.drawRect(0, middle, width, height, fyldFarve);

    }

    public float getInterval() {
        return interval;
    }

    public void setInterval(float interval) {
        this.interval = interval;
    }

    public float getVaerdi() {
        return vaerdi;
    }

    public void setVaerdi(float vaerdi) {
        this.vaerdi = vaerdi;
    }

    public Paint getTomFarve() {
        return tomFarve;
    }

    public void setTomFarve(Paint tomFarve) {
        this.tomFarve = tomFarve;
    }

    public Paint getFyldFarve() {
        return fyldFarve;
    }

    public void setFyldFarve(Paint fyldFarve) {
        this.fyldFarve = fyldFarve;
    }
}
