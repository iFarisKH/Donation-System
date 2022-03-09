package io.github.ifariskh.donationsystem.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.ifariskh.donationsystem.R;
import io.github.ifariskh.donationsystem.core.EndUser;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private Context context;
    private ArrayList<EndUser> endUsersList;

    public SearchAdapter(Context context, ArrayList<EndUser> endUsersList) {
        this.context = context;
        this.endUsersList = endUsersList;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_recycle_search, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        EndUser endUser = endUsersList.get(position);

        holder.id.setText(endUser.getId());
        holder.gender.setText(endUser.getGender());
        holder.salary.setText(String.valueOf(endUser.getSalary()));
        holder.socialStatus.setText(endUser.getSocialStatus());
        holder.age.setText(endUser.getDob());
    }

    @Override
    public int getItemCount() {
        return endUsersList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView id, gender, salary, socialStatus, age;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            gender = itemView.findViewById(R.id.gender);
            salary = itemView.findViewById(R.id.salary);
            socialStatus = itemView.findViewById(R.id.ss);
            age = itemView.findViewById(R.id.age);
        }

    }
}
