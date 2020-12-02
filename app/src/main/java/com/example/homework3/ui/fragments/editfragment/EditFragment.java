package com.example.homework3.ui.fragments.editfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.homework3.R;
import com.example.homework3.data.models.Post;
import com.example.homework3.data.network.PostService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Integer mParam1;
    private String mParam2;

    private EditText editTitle;
    private EditText editContent;
    private EditText editUser;
    private EditText editGroup;
    private Button btnEditPost;

    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(String param1, String param2) {
        EditFragment fragment = new EditFragment();
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
            mParam1 = getArguments().getInt("postId");
        }else
            Log.e("tag", "lol");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        btnEditPost = view.findViewById(R.id.btn_edit);
        btnEditPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmEdits();
            }
        });
    }

    private void confirmEdits() {
        Post post = new Post();
        post.setUser(Integer.valueOf(editUser.getText().toString()));
        post.setGroup(Integer.valueOf(editGroup.getText().toString()));
        post.setTitle(editTitle.getText().toString());
        post.setContent(editContent.getText().toString());
        PostService.getInstance().updatePost(mParam1, post.getTitle(), post.getContent(), post.getUser(), post.getGroup()).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()){
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
                    navController.navigateUp();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    private void initViews(View view) {
        editTitle = view.findViewById(R.id.et_title);
        editContent = view.findViewById(R.id.et_content);
        editUser = view.findViewById(R.id.et_user);
        editGroup = view.findViewById(R.id.et_group);
        PostService.getInstance().getPost(mParam1).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null){
                Post post = response.body();
                editTitle.setText(post.getTitle());
                editContent.setText(post.getContent());
                editUser.setText(String.valueOf(post.getUser()));
                editGroup.setText(String.valueOf(post.getGroup()));
                    Log.e("tag", "getDetails");
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }
}