package com.example.d308_mobile_application.UI;

import android.app.DatePickerDialog;
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
    DatePickerDialog.OnDateSetListener myDate;
    DatePickerDialog startDatePickerDialog;
    DatePickerDialog endDatePickerDialog;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);

        editTitle= findViewById(R.id.vacationText);
        editHotel = findViewById(R.id.hotelText);


        vacationName = getIntent().getStringExtra("vacationName");
        hotelName = getIntent().getStringExtra("hotelName");


        editTitle.setText(vacationName);
        editHotel.setText(hotelName);


        fab.setOnClickListener(view -> {
            Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionAdapter.setExcursions(repository.getAllExcursions());



        startDateButton = findViewById(R.id.startdatepicker);
        endDateButton = findViewById(R.id.enddatepicker);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String currentDate = sdf.format(new Date());
        startDateButton.setText(currentDate);
        endDateButton.setText(currentDate);

        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = startDateButton.getText().toString();
                try {
                    myCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, myDate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = endDateButton.getText().toString();
                try {
                    myCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(VacationDetails.this, myDate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        myDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Button selectedButton;
                if (datePicker == startDateButton.getParent()) {
                    selectedButton = startDateButton;
                } else {
                    selectedButton = endDateButton;
                }
                updateLabel(selectedButton);
            }
        };

    }


    private void updateLabel(Button selectedButton) {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
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
                if (repository.getAllVacations().size() == 0) vacationID = 1;
                else
                    vacationID = repository.getAllVacations().get(repository.getAllVacations().size() - 1).getVacationID() + 1;
                vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(), startDateButton.getText().toString(), endDateButton.getText().toString());
                repository.insert(vacation);
                Toast.makeText(VacationDetails.this, "Vacation saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(), startDateButton.getText().toString(), endDateButton.getText().toString());
                repository.update(vacation);
                Toast.makeText(VacationDetails.this, "Vacation updated successfully!", Toast.LENGTH_SHORT).show();
            }
            return true;
        }



        if (item.getItemId() == R.id.vactiondelete) {
            for (Vacation vaca : repository.getAllVacations()) {
                if (vaca.getVacationID() == vacationID) currentVacation = vaca;
            }

            numExcursions = 0;
            for (Excursion excursion : repository.getAllExcursions()) {
                if (excursion.getVacationID() == vacationID) ++numExcursions;
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

                if (repository.getAllExcursions().size() == 0) excurID = 1;
                else
                    excurID = repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionID() + 1;
                Excursion excursion = new Excursion(excurID, vacationID, "snorkeling", "05/14/24");
                repository.insert(excursion);
                excursion = new Excursion(++excurID, vacationID, "hiking", "05/28/24");
                repository.insert(excursion);
                RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
                final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
                recyclerView.setAdapter(excursionAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                List<Excursion> filteredParts = new ArrayList<>();
                for (Excursion e : repository.getAllExcursions()) {
                    if (e.getVacationID() == vacationID) filteredParts.add(e);
                }
                excursionAdapter.setExcursions(filteredParts);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        final ExcursionAdapter partAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(partAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion p : repository.getAllExcursions()) {
            if (p.getExcursionID() == vacationID) filteredExcursions.add(p);
        }
        partAdapter.setExcursions(filteredExcursions);
    }
}