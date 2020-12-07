package com.example.homework3.ui.fragments.add;

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
import com.example.homework3.data.network.RetrofitService;
import com.example.homework3.databinding.FragmentAddBinding;
import com.example.homework3.ui.fragments.post.PostFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Post post;
    private NavController navController;
    private FragmentAddBinding binding;
    PostFragment postFragment;

    public AddFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
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
        binding = FragmentAddBinding.inflate(inflater, container, false);
        View v = binding.getRoot();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPost();
                navController = Navigation.findNavController(requireActivity(),R.id.nav_host);
                navController.navigateUp();
            }
        });

    }

    private void addNewPost() {
        post = new Post();
        post.setTitle(binding.addTitle.getText().toString());
        post.setContent(binding.addContent.getText().toString());
        post.setGroup(Integer.valueOf(binding.addGroup.getText().toString()));
        post.setUser(Integer.valueOf(binding.addGroup.getText().toString()));
        RetrofitService.getInstance().newPost(post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful())
                    Log.e("tag", "yaai");
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

}