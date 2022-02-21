package io.github.ifariskh.donationsystem.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.github.ifariskh.donationsystem.R;
import io.github.ifariskh.donationsystem.helper.RecycleAdapter;

public class SearchFragment extends Fragment {

    private ArrayList<String> list = new ArrayList<>();

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initRecycleView(view);
        return view;
    }

    private void initRecycleView(View view) {
        testBuild();
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_search);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(linearLayoutManager);

        RecycleAdapter adapter = new RecycleAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    private void testBuild() {
        for (int i = 1000000000; i < 1000000011; i++) {
            list.add(i + "");
        }
    }
}