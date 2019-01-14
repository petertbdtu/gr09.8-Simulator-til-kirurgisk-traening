package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.fragments.DatePickerFragment;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.fragments.ShowLogsListFragment;

public class ShowLogsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button pickDate;
    int currentDay;
    int currentYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_logs);

        pickDate = findViewById(R.id.pickDate);
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        indlaesRecyclerViewFragment(0);

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        currentDay = calendar.get(Calendar.DAY_OF_YEAR);
        //Toast.makeText(this,calendar.getTime().toString(),Toast.LENGTH_LONG).show();
        indlaesRecyclerViewFragment(currentDay);
    }

    private void indlaesRecyclerViewFragment(int currentDay) {

        Bundle args = new Bundle();
        args.putInt("currentDay",currentDay);

        Fragment fragment = new ShowLogsListFragment();
        fragment.setArguments(args);
        System.out.println("DEBUG: indlaesRecyclerViewFrag startet"+currentDay);
        getFragmentManager().beginTransaction()
                .add(R.id.listContainer, fragment)
                .commit();
    }
}
