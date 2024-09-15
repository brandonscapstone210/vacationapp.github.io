package com.example.d308_mobile_application.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Query;

import com.example.d308_mobile_application.R;
import com.example.d308_mobile_application.database.Repository;
import com.example.d308_mobile_application.entities.Excursion;
import com.example.d308_mobile_application.entities.Vacation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;
    private Repository repository;
    List<Vacation> allVacations;
    List<Excursion> allExcursions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button button=findViewById(R.id.button);

        repository = new Repository(getApplication());

        //below are the actions to take us to the next screen using intent.
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,VacationList.class);
                intent.putExtra("test", "information sent");
                startActivity(intent);
            }
        });

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try{
                    allVacations = repository.getAllVacations();
                    allExcursions = repository.getAllExcursions();
                } catch (ExecutionException | InterruptedException e) {
                    throw  new RuntimeException(e);
                }

                List<String> vacationNames = allVacations.stream().map(Vacation::getVacationName).collect(Collectors.toList());
                List<String> excursionNames = allExcursions.stream().map(Excursion::getExcursionName).collect(Collectors.toList());

                List<String> allSearch = new ArrayList<>();
                allSearch.addAll(vacationNames);
                allSearch.addAll(excursionNames);

                List<String> filteredList = allSearch.stream().filter(item -> item.toLowerCase().contains(newText.toLowerCase())).collect(Collectors.toList());

                RecyclerView recyclerView = findViewById(R.id.searchRecyclerView);


                return false;
            }
        });

    }
}