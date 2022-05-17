package io.github.ifariskh.donationsystem.helper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.ifariskh.donationsystem.R;
import io.github.ifariskh.donationsystem.core.Admin;
import io.github.ifariskh.donationsystem.core.EndUser;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {

    private ArrayList<EndUser> endUsersList;
    private Context ctx;

    // Constructor
    public AdminAdapter(ArrayList<EndUser> endUsersList, Context ctx) {
        this.endUsersList = endUsersList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public AdminAdapter.AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycle_admin, parent, false);
        return new AdminAdapter.AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.AdminViewHolder holder, int position) {
        EndUser endUser = endUsersList.get(position);

        holder.id.setText("ID: " + endUser.getId());
        holder.name.setText("Name: " + endUser.getFullName());
        holder.dob.setText("DOB: " + endUser.getDob());
        holder.link.setText(Constant.BASE_URL + "/api/images/" + endUser.getId() + ".JPEG");
        holder.approve.setOnClickListener(view -> {
            new Admin().validate(endUser, this.ctx);
        });
    }

    @Override
    public int getItemCount() {
        return endUsersList.size();
    }

    class AdminViewHolder extends RecyclerView.ViewHolder {

        TextView id, name, dob, link;
        Button approve;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            dob = itemView.findViewById(R.id.dob);
            link = itemView.findViewById(R.id.link);
            approve = itemView.findViewById(R.id.approve);

        }
    }

}
