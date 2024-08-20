package com.example.d308_mobile_application.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308_mobile_application.database.Repository;
import com.example.d308_mobile_application.entities.Excursion;
import com.example.d308_mobile_application.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.d308_mobile_application.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class VacationList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);
        FloatingActionButton fab=findViewById(R.id.floatingActionButton);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationList.this, MainActivity.class);
                startActivity(intent);
            }
        });


        System.out.println(getIntent().getStringExtra("test"));
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.mysample){
            repository=new Repository(getApplication());
            //Toast.makeText(VacationList.this,"put in sample data", Toast.LENGTH_LONG).show();
            Vacation vacation=new Vacation(0,"Italy",80);
            repository.insert(vacation);
            vacation = new Vacation(1, "France", 100);
            repository.insert(vacation);
            Excursion excursion= new Excursion(0, "surfing", 50, 0);
            repository.insert(excursion);
            excursion = new Excursion(1, "snorkeling",25, 1);
            repository.insert(excursion);
            return true;
        }

        if(item.getItemId()==android.R.id.home){
            this.finish();
            Intent intent=new Intent(VacationList.this, VacationDetails.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}