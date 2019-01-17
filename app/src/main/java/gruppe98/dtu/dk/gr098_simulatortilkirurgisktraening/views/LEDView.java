package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class LEDView extends View {

    private boolean erTaendt;
    private Paint taendtFarve, slukketFarve;

    public LEDView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setup();
    }

    public LEDView(Context context) {
        super(context);

        setup();
    }

    private void setup() {
        erTaendt = false;
        slukketFarve = new Paint();
        slukketFarve.setARGB(255, 0, 0, 0);
        taendtFarve = new Paint();
        taendtFarve.setARGB(255, 255, 0, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Get the available space
        float radius = (getHeight() < getWidth() ? getHeight() : getWidth()) / 2;
        if (erTaendt) {
            canvas.drawCircle(radius, radius, radius, taendtFarve);
        } else {
            canvas.drawCircle(radius, radius, radius, slukketFarve);
        }
    }

    public boolean erTaendt() {
        return erTaendt;
    }

    public void taend() {
        this.erTaendt = true;
        invalidate();
    }

    public void sluk() {
        this.erTaendt = false;
        invalidate();
    }

    public Paint getTaendtFarve() {
        return taendtFarve;
    }

    public void setTaendtFarve(Paint taendtFarve) {
        this.taendtFarve = taendtFarve;
        invalidate();
    }

    public Paint getSlukketFarve() {
        return slukketFarve;
    }

    public void setSlukketFarve(Paint slukketFarve) {
        this.slukketFarve = slukketFarve;
        invalidate();
    }
}
