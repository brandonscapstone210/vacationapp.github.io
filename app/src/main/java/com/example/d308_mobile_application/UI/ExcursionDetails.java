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

import com.example.d308_mobile_application.R;
import com.example.d308_mobile_application.database.Repository;
import com.example.d308_mobile_application.entities.Excursion;
import com.example.d308_mobile_application.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class ExcursionDetails extends AppCompatActivity {
    String title;
    String excursionDate;
    int excursionID;
    int vacationID;
    EditText editName;
    Button excursionDateButton;
    DatePickerDialog.OnDateSetListener myExcursionStartDate;
    Repository repository;
    final Calendar myCalendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        repository=new Repository(getApplication());
        title = getIntent().getStringExtra("excursionName");
        editName = findViewById(R.id.excursionTitle);
        editName.setText(title);
        excursionID = getIntent().getIntExtra("excursionID", -1);
        vacationID = getIntent().getIntExtra("vacationID", -1);
        excursionDateButton = findViewById(R.id.excursiondatepicker);

        excursionDate = getIntent().getStringExtra("excursionDate");
        if (excursionDate != null) {
            excursionDateButton.setText(excursionDate);
        } else {
            excursionDateButton.setText(sdf.format(new Date()));
        }



        excursionDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = excursionDateButton.getText().toString();
                try {
                    myCalendar.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(ExcursionDetails.this, myExcursionStartDate, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        myExcursionStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(year, monthOfYear, dayOfMonth);
                updateStartLabel(excursionDateButton);
            }
        };



        ArrayList<Vacation> vacationArrayList= new ArrayList<>();
        try {
            vacationArrayList.addAll(repository.getAllVacations());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Integer> vacationIDlist = new ArrayList<>();
        for(Vacation vacation:vacationArrayList){
            vacationIDlist.add(vacation.getVacationID());
        }





    }


    private void updateStartLabel(Button selectedButton) {
        selectedButton.setText(sdf.format(myCalendar.getTime()));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()== android.R.id.home){
            this.finish();
            return true;}


        if (item.getItemId()== R.id.excursionsave){
            Excursion excursion;
            if (excursionID == -1) {
                try {
                    if (repository.getAllExcursions().size() == 0)
                        excursionID = 1;
                    else
                        excursionID = repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionID() + 1;
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                excursion = new Excursion(excursionID, vacationID, editName.getText().toString(), excursionDateButton.getText().toString());
                repository.insert(excursion);
                Toast.makeText(ExcursionDetails.this, "Excursion saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                excursion = new Excursion(excursionID, vacationID, editName.getText().toString(), excursionDateButton.getText().toString());
                repository.update(excursion);
                Toast.makeText(ExcursionDetails.this, "Excursion updated successfully!", Toast.LENGTH_SHORT).show();
            }
            return true;}

        if (item.getItemId() == R.id.excursiondelete) {
            Excursion currentExcursion = new Excursion(excursionID, vacationID, title, excursionDate);

            repository.delete(currentExcursion);
            Toast.makeText(ExcursionDetails.this, currentExcursion.getExcursionName() + " was deleted", Toast.LENGTH_LONG).show();
            return true;
        }

        if(item.getItemId()== R.id.notify) {
            String dateFromScreen = excursionDateButton.getText().toString();
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try{
                Long trigger = myDate.getTime();
                Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
                intent.putExtra("key", "message I want to see");
                PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);}
            catch (Exception e){

            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}