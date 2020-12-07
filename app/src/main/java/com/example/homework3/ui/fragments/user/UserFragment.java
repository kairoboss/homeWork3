package com.example.homework3.ui.fragments.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homework3.R;
import com.example.homework3.data.models.Post;
import com.example.homework3.data.network.RetrofitService;
import com.example.homework3.databinding.FragmentUserBinding;
import com.example.homework3.ui.fragments.post.PostAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment implements UserAdapter.OnClicks {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    private NavController navController;
    private FragmentUserBinding binding;
    private UserAdapter adapter;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
        binding.addUserFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.addFragment);
            }
        });
        LinearLayoutManager lnm = new LinearLayoutManager(getContext());
        binding.usersRecycler.setLayoutManager(lnm);
        List<Post> list = setPostList();
        adapter = new UserAdapter(list,this);
        binding.usersRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private List<Post> setPostList() {
        List<Post> postList = new ArrayList<>();
        RetrofitService.getInstance().getAllPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null)
                    postList.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
        return postList;
    }
    public void updateList(){
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int pos) {
        Bundle b = new Bundle();
        b.putInt("userNum", adapter.getItem(pos).getUser());
        navController.navigate(R.id.action_navigation_users_to_postFragment, b);
    }

    @Override
    public void onItemLongClick(int pos) {
    }
}