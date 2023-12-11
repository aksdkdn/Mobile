package com.android.calendarmanagerproject_java;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.calendarmanagerproject_java.data.DbExercise;
import com.android.calendarmanagerproject_java.data.ExerciseData;
import com.android.calendarmanagerproject_java.databinding.SettingDlgBinding;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private ArrayList<ExerciseData> mDataset;
    private OnItemListener callback;
    private ArrayList<DbExercise> dbExercises;

    public ExerciseAdapter(ArrayList<ExerciseData> myDataset, ArrayList<DbExercise> dbExercises, OnItemListener listener) {
        mDataset = myDataset;
        callback = listener;
        this.dbExercises = dbExercises;
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.exercise_item, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int position) {
        Log.i("##INFO", "onBindViewHolder(): mDataset.get(position).getName() = "+mDataset.get(position).getId() + ", " + mDataset.get(position).getName()+ ", mDataset.get(position).getWeight() = " + mDataset.get(position).getWeight() + ", mDataset.get(position).getSet() = " + mDataset.get(position).getSet());
        holder.exerciseName.setText(mDataset.get(position).getName());
        holder.exerciseWeight.setText(mDataset.get(position).getWeight()+" kg");
        holder.exerciseSet.setText(mDataset.get(position).getSet()+" set");

        for (int i = 0; i < dbExercises.size(); i++) {
            if (mDataset.get(position).getName().equals(dbExercises.get(i).getName())) {
                holder.exerciseImage.setImageDrawable(dbExercises.get(i).getImage());
            }
        }
    }

    @Override
    public int getItemCount() {
        Log.i("##INFO", "getItemCount(): mDataset.size() = " + mDataset.size());
        return mDataset.size();
    }

    public void setExerciseList(ArrayList<ExerciseData> exerciseData) {
        mDataset = exerciseData;
        notifyDataSetChanged();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName, exerciseWeight, exerciseSet;
        ImageView exerciseImage, exerciseDelete;

        public ExerciseViewHolder(View view) {
            super(view);

            exerciseName = view.findViewById(R.id.tv_exercise);
            exerciseWeight = view.findViewById(R.id.tvWeight);
            exerciseSet = view.findViewById(R.id.tvSet);
            exerciseDelete = view.findViewById(R.id.imDeleteExercise);
            exerciseImage = view.findViewById(R.id.im_exercise);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dlg = new Dialog(itemView.getContext(), R.style.theme_dialog);
                    SettingDlgBinding dlgBinding;
                    dlgBinding = SettingDlgBinding.inflate(LayoutInflater.from(itemView.getContext()));
                    dlg.setContentView(dlgBinding.getRoot());
                    dlg.setTitle(mDataset.get(getAdapterPosition()).getName());
                    dlg.show();

                    dlgBinding.npKg.setMinValue(0);
                    dlgBinding.npKg.setMaxValue(100);
                    dlgBinding.npSet.setMinValue(0);
                    dlgBinding.npSet.setMaxValue(100);

                    dlgBinding.btnCancel.setOnClickListener(v1 -> {
                        dlg.dismiss();
                    });

                    dlgBinding.btnAdd.setOnClickListener(v1 -> {
                        mDataset.get(getAdapterPosition()).setWeight(String.valueOf(dlgBinding.npKg.getValue()));
                        mDataset.get(getAdapterPosition()).setSet(String.valueOf(dlgBinding.npSet.getValue()));

                        callback.onItemAdd(mDataset.get(getAdapterPosition()));
                        dlg.dismiss();
                    });
                }
            });

            exerciseDelete.setOnClickListener(v -> {
                callback.onItemDelete(mDataset.get(getAdapterPosition()));

                mDataset.remove(getAdapterPosition());
                notifyDataSetChanged();
            });
        }
    }
}

interface OnItemListener {
    void onItemAdd(ExerciseData exerciseData);
    void onItemDelete(ExerciseData exerciseData);
}
