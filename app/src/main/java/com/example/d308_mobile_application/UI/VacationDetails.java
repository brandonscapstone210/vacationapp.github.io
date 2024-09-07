package com.example.d308_mobile_application.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308_mobile_application.R;
import com.example.d308_mobile_application.database.Repository;
import com.example.d308_mobile_application.entities.Excursion;
import com.example.d308_mobile_application.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class VacationDetails extends AppCompatActivity {
    int vacationID;
    String vacationName;
    String hotelName;
    Vacation currentVacation;
    int numExcursions;


    EditText editTitle;
    EditText editHotel;


    Repository repository;
    Button startDateButton;
    Button endDateButton;
    DatePickerDialog.OnDateSetListener myStartDate;
    DatePickerDialog.OnDateSetListener myEndDate;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
    final Calendar myCalendar = Calendar.getInstance();
    Date selectedStartDate;
    Date selectedEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);

        editTitle = findViewById(R.id.vacationText);
        editHotel = findViewById(R.id.hotelText);


        vacationName = getIntent().getStringExtra("vacationName");
        vacationID = getIntent().getIntExtra("vacationID", -1);
        hotelName = getIntent().getStringExtra("hotelName");


        editTitle.setText(vacationName);
        editHotel.setText(hotelName);


        fab.setOnClickListener(view -> {
            Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
            intent.putExtra("vacationID", vacationID);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        try {
            excursionAdapter.setExcursions(repository.getAllExcursions());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        startDateButton = findViewById(R.id.startdatepicker);
        endDateButton = findViewById(R.id.enddatepicker);
        try {
            String value = getIntent().getStringExtra("startDate");
            if (value != null) {
                selectedStartDate = sdf.parse(value);
                startDateButton.setText(value);
            }
        } catch (ParseException e) {
            selectedStartDate = new Date();
            startDateButton.setText(sdf.format(selectedStartDate));
        }

        try {
            String value = getIntent().getStringExtra("endDate");
            if (value != null) {
                selectedEndDate = sdf.parse(value);
                endDateButton.setText(value);
            }
        } catch (ParseException e) {
            selectedEndDate = selectedStartDate;
            endDateButton.setText(sdf.format(selectedEndDate));
        }

        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = startDateButton.getText().toString();
                try {
                    myCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DatePickerDialog dpd =  new DatePickerDialog(VacationDetails.this, myStartDate,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)
                );
                dpd.getDatePicker().setMinDate(new Date().getTime());
                dpd.show();
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = endDateButton.getText().toString();
                try {
                    myCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DatePickerDialog dpd =  new DatePickerDialog(VacationDetails.this, myEndDate,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)
                );
                dpd.getDatePicker().setMinDate(selectedStartDate.getTime());
                dpd.show();
            }
        });



        myStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                selectedStartDate = myCalendar.getTime();
                updateLabel(startDateButton);
                if (selectedEndDate.before(selectedStartDate)) {
                    selectedEndDate = selectedStartDate;
                    updateLabel(endDateButton);
                }
            }
        };

        myEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                selectedEndDate = myCalendar.getTime();
                updateLabel(endDateButton);
            }
        };
    }


    private void updateLabel(Button selectedButton) {
        selectedButton.setText(sdf.format(myCalendar.getTime()));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.vactionsave) {
            Vacation vacation;
            if (vacationID == -1) {
                try {
                    if (repository.getAllVacations().isEmpty()) vacationID = 1;
                    else
                        vacationID = repository.getAllVacations().get(repository.getAllVacations().size() - 1).getVacationID() + 1;
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                vacation = new Vacation(vacationID, editTitle.getText().toString(),
                        editHotel.getText().toString(), startDateButton.getText().toString(),
                        endDateButton.getText().toString()
                );
                repository.insert(vacation);
                Toast.makeText(VacationDetails.this, "Vacation saved successfully!",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(), startDateButton.getText().toString(), endDateButton.getText().toString());
                repository.update(vacation);
                Toast.makeText(VacationDetails.this, "Vacation updated successfully!", Toast.LENGTH_SHORT).show();
            }
            return true;
        }



        if (item.getItemId() == R.id.vactiondelete) {
            try {
                for (Vacation vaca : repository.getAllVacations()) {
                    if (vaca.getVacationID() == vacationID) currentVacation = vaca;
                }
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            numExcursions = 0;
            try {
                for (Excursion excursion : repository.getAllExcursions()) {
                    if (excursion.getVacationID() == vacationID) ++numExcursions;
                }
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (numExcursions == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationName() + " was deleted", Toast.LENGTH_LONG).show();
            } else{
                Toast.makeText(VacationDetails.this, "Can't delete a vacation with excursions", Toast.LENGTH_LONG).show();
            }
            return true;
        }



        if (item.getItemId() == R.id.addSampleExcursions) {
            if (vacationID == -1)
                Toast.makeText(VacationDetails.this, "Please save vacation before adding excursions", Toast.LENGTH_LONG).show();

            else {
                int excurID;

                try {
                    if (repository.getAllExcursions().size() == 0) excurID = 1;
                    else
                        excurID = repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionID() + 1;
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Excursion excursion = new Excursion(excurID, vacationID, "snorkeling", "05/14/24");
                repository.insert(excursion);
                excursion = new Excursion(++excurID, vacationID, "hiking", "05/28/24");
                repository.insert(excursion);
                RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
                final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
                recyclerView.setAdapter(excursionAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                List<Excursion> filteredExcursions = new ArrayList<>();
                try {
                    for (Excursion e : repository.getAllExcursions()) {
                        if (e.getVacationID() == vacationID) filteredExcursions.add(e);
                    }
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                excursionAdapter.setExcursions(filteredExcursions);
                return true;
            }

            if(item.getItemId()== R.id.notify) {
                String dateFromScreen = startDateButton.getText().toString();
                Date myDate = null;
                try {
                    myDate = sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try{
                    Long trigger = myDate.getTime();
                    Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
                    intent.putExtra("key", "message I want to see");
                    PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);}
                catch (Exception e){

                }
                return true;
            }

            if (item.getItemId()== R.id.share) {
                String vacationDetails = "Vacation Title: " + vacationName + "\n" + "Hotel: " + hotelName + "\n" + "Dates: " + startDateButton.getText().toString() + " - " + endDateButton.getText().toString() + "\n" + "Excursions: \n";
                try {
                    List<Excursion> excursions = repository.getAllExcursions();
                    for (Excursion excursion : excursions) {
                        vacationDetails += "- " + excursion.getExcursionName() + " on " + excursion.getExcursionDate() + "\n";
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, vacationDetails);
                sendIntent.putExtra(Intent.EXTRA_TITLE, vacationName);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;

            }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        try {
            for (Excursion p : repository.getAllExcursions()) {
                if (p.getVacationID() == vacationID) filteredExcursions.add(p);
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        excursionAdapter.setExcursions(filteredExcursions);
    }
}