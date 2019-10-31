package com.juaracoding.belajario;



import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class ToDoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{




    private ArrayList<ModelTDL> dataItemList;

    public ToDoListAdapter(ArrayList<ModelTDL> dataItemList) {
        this.dataItemList = dataItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todolist, parent, false);
            Penampung penampung = new Penampung(view);
            return penampung;


    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


            TextView txtTitle = ((Penampung)holder).txtTitle;
            TextView txtTanggal = ((Penampung)holder).txtTanggal;
            TextView txtNotes = ((Penampung)holder).txtNotes;
        txtTitle.setText(dataItemList.get(position).getTitle());
        txtTanggal.setText(dataItemList.get(position).getTanggal().toString());
        txtNotes.setText(dataItemList.get(position).getNotes());



    }

    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
    }


    static class Penampung extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtTitle;
        public TextView txtTanggal;
        public TextView txtNotes;
        public Penampung(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtTanggal = (TextView) itemView.findViewById(R.id.txtTanggal);
            txtNotes = (TextView) itemView.findViewById(R.id.txtNotes);
        }
        @Override
        public void onClick(View view) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " );
        }
    }


}