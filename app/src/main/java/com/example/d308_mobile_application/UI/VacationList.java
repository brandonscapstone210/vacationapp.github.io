package com.example.d308_mobile_application.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.d308_mobile_application.database.Repository;
import com.example.d308_mobile_application.entities.Excursion;
import com.example.d308_mobile_application.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.d308_mobile_application.R;


public class VacationList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(VacationList.this, VacationDetails.class);
            startActivity(intent);
        });

//        repository = new Repository(getApplication());
//        System.out.println(getIntent().getStringExtra("test"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){


        if(item.getItemId() == R.id.sample){
            repository=new Repository(getApplication());
            //Toast.makeText(VacationList.this,"put in sample data", Toast.LENGTH_LONG).show();
            Vacation vacation=new Vacation(0, "Italy", "Marriot", "01/15/24", "01/25/24");
            repository.insert(vacation);
            vacation = new Vacation(1, "France", "Sleepy Inn", "02/02/24", "02/12/24");
            repository.insert(vacation);
            Excursion excursion= new Excursion(0, "surfing", "06/14/2024");
            repository.insert(excursion);
            excursion = new Excursion(1, "snorkeling","07/16/2024");
            repository.insert(excursion);
            return true;
        }

        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}