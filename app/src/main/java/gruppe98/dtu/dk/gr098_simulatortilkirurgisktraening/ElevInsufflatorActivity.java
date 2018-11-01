package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ElevInsufflatorActivity extends AppCompatActivity {

    ImageView tubeblokeretLed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elev_insufflator);

        /*
         Noter om tubeblokeretLed XML her fordi jeg ikke ved hvordan man skriver kommentarer i en XML fil.
         app:layout_constraintdimensions_ratio="1:1" sørger for at billedets ratio beholdes.

         LED'en består af en drawable cirkel jeg fandt på stackoverflow. Den bruges i et imageView.
         Det imageView kan man så tage fat i og ændre farve på; se nedenstående.
          */

        tubeblokeretLed = findViewById(R.id.tubeblokeretLed);
        tubeblokeretLed.setColorFilter(Color.argb(255, 255, 0,0), PorterDuff.Mode.SRC_OVER);
    }
}
