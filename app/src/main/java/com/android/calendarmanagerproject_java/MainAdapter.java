package com.android.calendarmanagerproject_java;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.calendarmanagerproject_java.data.ExerciseData;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private final ArrayList<ExerciseData> mDataset;
    private final String date;
    private Context ctx;

    public MainAdapter(Context ctx, ArrayList<ExerciseData> myDataset, String d) {
        mDataset = myDataset;
        date = d;
        this.ctx = ctx;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.date_item, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

        holder.exerciseName.setText(mDataset.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName;

        public MainViewHolder(android.view.View view) {
            super(view);
            exerciseName = view.findViewById(R.id.tvDateExerciseItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(itemView.getContext(), ExerciseActivity.class);
                    i.putExtra("date", date);
                    i.putExtra("exerciseList", mDataset);
                    itemView.getContext().startActivity(i);
                    ((MainActivity)ctx).finish();
                }
            });
        }
    }
}
