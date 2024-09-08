package com.example.d308_mobile_application.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308_mobile_application.R;
import com.example.d308_mobile_application.entities.Excursion;


import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;

    private String vacationStart;
    private String vacationEnd;

    public ExcursionAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.excursion_list_item,parent,false);
        return new ExcursionViewHolder(itemView, vacationStart, vacationEnd);
    }

    @Override
    public void onBindViewHolder (@NonNull ExcursionViewHolder holder, int position) {
        if(mExcursions!=null){
            Excursion current=mExcursions.get(position);
            String name = current.getExcursionName();
            String date = current.getExcursionDate();
            holder.viewExcursionName.setText(name);
            holder.viewExcursionDate.setText(date);
        }
        else{
            holder.viewExcursionName.setText("No excursion name");
            holder.viewExcursionDate.setText("No excursion date");
        }
    }

    public void setExcursions(List<Excursion> excursions){
        mExcursions=excursions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mExcursions != null) return mExcursions.size();
        else return 0;
    }

    public void setVacationDates(String startDate, String endDate) {
        this.vacationStart = startDate;
        this.vacationEnd = endDate;
    }

    class ExcursionViewHolder extends RecyclerView.ViewHolder{
        private final TextView viewExcursionName;
        private final TextView viewExcursionDate;
        private ExcursionViewHolder(View itemView, String startDate, String endDate){
            super(itemView);
            viewExcursionName = itemView.findViewById(R.id.view_excursion_name);
            viewExcursionDate = itemView.findViewById(R.id.view_excursion_date);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Excursion current = mExcursions.get(position);
                Intent intent = new Intent(context, ExcursionDetails.class);
                intent.putExtra("excursionID", current.getExcursionID());
                intent.putExtra("excursionName", current.getExcursionName());
                intent.putExtra("excursionDate", current.getExcursionDate());
                intent.putExtra("vacationID", current.getVacationID());
                if (startDate != null && endDate != null){
                    intent.putExtra("vacationStart", startDate);
                    intent.putExtra("vacationEnd", endDate);
                }
                context.startActivity(intent);
            });

        }
    }

}
