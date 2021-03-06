package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MeterView extends View {

    private final static int FORVENTET_VAERDI_HALV_BREDDE = 5;

    private float interval, aktuelVaerdi, forventetVaerdi;
    private Paint tomFarve, aktuelFarve, forventetFarve;

    public MeterView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setup();
    }

    public MeterView(Context context) {
        super(context);

        setup();
    }

    private void setup() {
        this.interval = 99;
        this.aktuelVaerdi = 0;
        this.forventetVaerdi = -FORVENTET_VAERDI_HALV_BREDDE; // Skjul forventetVaerdi indtil den sættes.
        this.tomFarve = new Paint();
        this.tomFarve.setARGB(255, 64, 64, 64);
        this.aktuelFarve = new Paint();
        this.aktuelFarve.setARGB(255, 255, 0, 0);
        this.forventetFarve = new Paint();
        this.forventetFarve.setARGB(255,0,255,0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getHeight();
        int width = getWidth();

        float aktuelHoejde = height - (height * aktuelVaerdi / interval);
        float forventetHoejde = height - (height * forventetVaerdi / interval);

        canvas.drawRect(0, 0, width, aktuelHoejde, tomFarve);
        canvas.drawRect(0, aktuelHoejde, width, height, aktuelFarve);
        canvas.drawRect(0, forventetHoejde-FORVENTET_VAERDI_HALV_BREDDE, width, forventetHoejde+FORVENTET_VAERDI_HALV_BREDDE, forventetFarve);
    }

    public float getInterval() {
        return interval;
    }

    public void setInterval(float interval) {
        this.interval = interval;
        invalidate();
    }

    public float getAktuelVaerdi() {
        return aktuelVaerdi;
    }

    public void setAktuelVaerdi(float aktuelVaerdi) {
        this.aktuelVaerdi = aktuelVaerdi;
        invalidate();
    }

    public float getForventetVaerdi() {
        return forventetVaerdi;
    }

    public void setForventetVaerdi(float forventetVaerdi) {
        this.forventetVaerdi = forventetVaerdi;
        invalidate();
    }
    public Paint getTomFarve() {
        return tomFarve;
    }

    public void setTomFarve(Paint tomFarve) {
        this.tomFarve = tomFarve;
        invalidate();
    }

    public Paint getAktuelFarve() {
        return aktuelFarve;
    }

    public void setAktuelFarve(Paint aktuelFarve) {
        this.aktuelFarve = aktuelFarve;
        invalidate();
    }

    public Paint getForventetFarve() {
        return forventetFarve;
    }

    public void setForventetFarve(Paint forventetFarve) {
        this.forventetFarve = forventetFarve;
        invalidate();
    }
}
