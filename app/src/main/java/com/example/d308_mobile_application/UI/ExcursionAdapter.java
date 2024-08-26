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


import org.w3c.dom.Text;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {

    class ExcursionViewHolder extends RecyclerView.ViewHolder{
        private final TextView excursionItemView;
        private final TextView excursionItemView2;
        private final TextView excursionItemView3;
        private ExcursionViewHolder(View itemView){
            super(itemView);
                excursionItemView = itemView.findViewById(R.id.textView);
                excursionItemView2 = itemView.findViewById(R.id.textView2);
                excursionItemView3 = itemView.findViewById(R.id.textView3);
                itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        final Excursion current =mExcursions.get(position);
                        Intent intent = new Intent(context, ExcursionDetails.class);
                        intent.putExtra("excursionID", current.getExcursionID());
                        intent.putExtra("excursionName", current.getExcursionName());
                        intent.putExtra("excursionDate", current.getExcursionDate());
                        context.startActivity(intent);
                    }
                });

    }
}
    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;

    public ExcursionAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.excursion_list_item,parent,false);
        return new ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder (@NonNull ExcursionViewHolder holder, int position) {
        if(mExcursions!=null){
            Excursion current=mExcursions.get(position);
            int vacationID= current.getExcursionID();
            String name = current.getExcursionName();
            String date = current.getExcursionDate();
            holder.excursionItemView.setText(Integer.toString(vacationID));
            holder.excursionItemView2.setText(name);
            holder.excursionItemView3.setText(date);
        }
        else{
            holder.excursionItemView.setText("No excursion name");
            holder.excursionItemView.setText("No vacation id");
        }
    }

    public void setParts(List<Excursion> excursions){
        mExcursions=excursions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mExcursions != null) return mExcursions.size();
        else return 0;
    }



}
