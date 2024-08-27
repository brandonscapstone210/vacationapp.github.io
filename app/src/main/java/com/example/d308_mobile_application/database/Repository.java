package com.example.d308_mobile_application.database;

import android.app.Application;
import android.icu.text.MessagePattern;

import com.example.d308_mobile_application.dao.ExcursionDAO;
import com.example.d308_mobile_application.dao.VacationDAO;
import com.example.d308_mobile_application.entities.Excursion;
import com.example.d308_mobile_application.entities.Vacation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Repository {

    private ExcursionDAO mExcursionDAO;
    private VacationDAO mVacationDAO;
    private List<Vacation> mAllVacations;
    private List<Excursion> mAllExcursions;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        VacationDatabaseBuilder db=VacationDatabaseBuilder.getDatabase(application);
        mExcursionDAO = db.excursionDAO();
        mVacationDAO = db.vacationDAO();
    }
    public List<Vacation>getAllVacations(){
        databaseExecutor.execute(()->{
            mAllVacations=mVacationDAO.getAllVacations();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllVacations;
    }
    public void insert(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDAO.insert(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDAO.update(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDAO.delete(vacation);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public List<Excursion>getAllExcursions(){
        databaseExecutor.execute(()->{
            mAllExcursions=mExcursionDAO.getAllExcursions();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExcursions;
    }
    public void insert(Excursion excursion){
        databaseExecutor.execute(()->{
            mExcursionDAO.insert(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Excursion excursion){
        databaseExecutor.execute(()->{
            mExcursionDAO.update(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Excursion excursion){
        databaseExecutor.execute(()->{
            mExcursionDAO.delete(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
//    private final ExcursionDAO mExcursionDAO;
//    private final VacationDAO mVacationDAO;
//
//
//    private static final int NUMBER_OF_THREADS = 4;
//    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
//
//    public Repository(Application application){
//        VacationDatabaseBuilder db = VacationDatabaseBuilder.getDatabase(application);
//        mExcursionDAO = db.excursionDAO();
//        mVacationDAO = db.vacationDAO();
//
//    }
//
//    public List<Vacation> getmALLVacations(){
//        try{
//            return databaseExecutor.submit(mVacationDAO::getAllVacations).get();
//        } catch (ExecutionException | InterruptedException e){
//            //Would log error
//        }
//        return new ArrayList<>();
//    }
//
//    public List<Excursion> getmALLExcursions() {
//        try {
//            return databaseExecutor.submit(mExcursionDAO::getAllExcursions).get();
//        } catch (ExecutionException | InterruptedException e) {
//            //would log error
//        }
//
//        return new ArrayList<>();
//    }
//
//
//    public void insert(Vacation vacation) {
//        databaseExecutor.execute(()->mVacationDAO.insert(vacation));
//    }
//    public void insert(Excursion excursion) {
//        databaseExecutor.execute(()->mExcursionDAO.insert(excursion));
//    }
//
//    public void update(Vacation vacation) {
//        databaseExecutor.execute(()->mVacationDAO.update(vacation));
//    }
//
//    public void update(Excursion excursion) {
//        databaseExecutor.execute(()->mExcursionDAO.update(excursion));
//    }
//
//
//    public void delete(Vacation vacation) {
//        databaseExecutor.execute(()->mVacationDAO.delete(vacation));
//    }
//
//    public void delete(Excursion excursion) {
//        databaseExecutor.execute(()->mExcursionDAO.delete(excursion));
//    }
//
//
//}
