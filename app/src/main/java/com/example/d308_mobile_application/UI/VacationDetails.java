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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
    String startDate;
    String endDate;
    Vacation currentVacation;
    int numExcursions;


    EditText editTitle;
    EditText editHotel;
    EditText editStartDate;
    EditText editEndDate;

    Repository repository;
    Button button;
    DatePickerDialog.OnDateSetListener myDate;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);

        editTitle= findViewById(R.id.vacationText);
        editHotel = findViewById(R.id.hotelText);
        editStartDate = findViewById(R.id.startDateText);
        editEndDate = findViewById(R.id.endDateText);

        vacationName = getIntent().getStringExtra("vacationName");
        hotelName = getIntent().getStringExtra("hotelName");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");

        editTitle.setText(vacationName);
        editHotel.setText(hotelName);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionAdapter.setParts(repository.getmALLExcursions());



        button = findViewById(R.id.datePicker);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String currentDate = sdf.format(new Date());
        button.setText(currentDate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = button.getText().toString();
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
                updateLabel();
            }
        };

    }


    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        button.setText(sdf.format(myCalendar.getTime()));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == R.id.vactionsave){
//            Vacation vacation;
//            if (vacationID == -1){
//                if(repository.getmALLVacations().size() == 0) vacationID = 1;
//                else vacationID = repository.getmALLVacations().get(repository.getmALLExcursions().size() - 1).getVacationID() + 1;
//                vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
//                repository.insert(vacation);
//                this.finish();
//            }
//            else{
//                vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
//                repository.update(vacation);
//                this.finish();
//            }
//        }
//        return true;
//    }

    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId()== android.R.id.home){
            this.finish();
            return true;}
        if(item.getItemId()== R.id.vactionsave){
            Vacation vacation;
            if (vacationID == -1) {
                if (repository.getmALLVacations().size() == 0) vacationID = 1;
                else
                    vacationID = repository.getmALLVacations().get(repository.getmALLVacations().size() - 1).getVacationID() + 1;
                vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                repository.insert(vacation);
            } else {
                    vacation = new Vacation(vacationID, editTitle.getText().toString(), editHotel.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                    repository.update(vacation);
                    Toast.makeText(VacationDetails.this, "Vacation Saved!", Toast.LENGTH_LONG).show();
            }

            return true;}
        if(item.getItemId()== R.id.vactiondelete) {
            for (Vacation vacation : repository.getmALLVacations()) {
                if (vacation.getVacationID() == vacationID) currentVacation = vacation;
            }

            numExcursions = 0;
            for (Excursion excursion : repository.getmALLExcursions()) {
                if (excursion.getExcursionID() == vacationID) ++numExcursions;
            }

            if (numExcursions == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationName() + " was deleted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(VacationDetails.this, "Can't delete a vacation with parts", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        if(item.getItemId()== R.id.sample){
            if (vacationID == -1)
                Toast.makeText(VacationDetails.this, "Please save product before adding parts", Toast.LENGTH_LONG).show();

            else {
                int excursionID;

                if (repository.getmALLExcursions().size() == 0) excursionID = 1;
                else
                    excursionID = repository.getmALLExcursions().get(repository.getmALLExcursions().size() - 1).getExcursionID() + 1;
                Excursion excursion = new Excursion(1, "biking", "04/15/24");
                repository.insert(excursion);
                excursion = new Excursion(2, "swimming", "06/15/24");
                repository.insert(excursion);
                RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
                final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
                recyclerView.setAdapter(excursionAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                List<Excursion> filteredExcursions = new ArrayList<>();
                for (Excursion p : repository.getmALLExcursions()) {
                    if (p.getExcursionID() == vacationID) filteredExcursions.add(p);
                }
                excursionAdapter.setParts(filteredExcursions);
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
        for (Excursion p : repository.getmALLExcursions()) {
            if (p.getExcursionID() == vacationID) filteredExcursions.add(p);
        }
        partAdapter.setParts(filteredExcursions);

        //Toast.makeText(ProductDetails.this,"refresh list",Toast.LENGTH_LONG).show();
    }

}