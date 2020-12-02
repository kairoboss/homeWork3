package com.example.homework3.ui.fragments.postfragment;

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
import com.example.homework3.data.network.PostService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment implements PostAdapter.OnClicks {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView postRecyclerview;
    private PostAdapter adapter;
    private FloatingActionButton addFab;
    private NavController navController;

    public PostFragment() {
        // Required empty public constructor
    }

    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
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
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
        addFab = view.findViewById(R.id.add_fab);
        postRecyclerview = view.findViewById(R.id.posts_recycler);
        LinearLayoutManager lnm = new LinearLayoutManager(getContext());
        postRecyclerview.setLayoutManager(lnm);
        List<Post> list = setPostList();
        adapter = new PostAdapter(list,this);
        postRecyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddFragment();
            }
        });
    }

    private void openAddFragment() {
        navController.navigate(R.id.action_postFragment_to_addFragment);
    }

    private List<Post> setPostList() {
        List<Post> postList = new ArrayList<>();
        PostService.getInstance().getAllPosts().enqueue(new Callback<List<Post>>() {
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
        b.putInt("postId", adapter.getItem(pos).getId());
        navController.navigate(R.id.action_postFragment_to_editFragment, b);
    }

    @Override
    public void onItemLongClick(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Удаление");
        builder.setMessage("Удалить пост?");
        builder.setNegativeButton("Отмена", null);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        PostService.getInstance().deletePost(adapter.getItem(pos).getId()).enqueue(new Callback<Post>() {
                            @Override
                            public void onResponse(Call<Post> call, Response<Post> response) {
                                if (response.isSuccessful())
                                    Log.e("tag", "deleted");
                            }

                            @Override
                            public void onFailure(Call<Post> call, Throwable t) {

                            }
                        });
                        adapter.removeItem(pos);
                    }
                });
        builder.show();
    }
}