package com.example.d308_mobile_application.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308_mobile_application.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<String> searchResults;
    private final Context context;


    public SearchAdapter(List<String> searchResults, Context context) {
        this.searchResults = searchResults;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(context).inflate(R.layout.search_view, parent, false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position){
        if (searchResults != null) {
            String result = searchResults.get(position);
            holder.searchResultTextView.setText(result);
        } else {
            holder.searchResultTextView.setText("No results found");
        }
    }

    @Override
    public int getItemCount() {
        if (searchResults != null) {
            return searchResults.size();
        } else return 0;
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        private final TextView searchResultTextView;

        private SearchViewHolder(View itemView) {
            super(itemView);
            searchResultTextView = itemView.findViewById(R.id.searchRecyclerView);

            // You can add click listeners or other logic here if needed
        }
    }

}
