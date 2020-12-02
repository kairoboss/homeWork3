package com.example.homework3.ui.fragments.addfragment;

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
import com.example.homework3.ui.fragments.postfragment.PostAdapter;
import com.example.homework3.ui.fragments.postfragment.PostFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText addTitle;
    private EditText addContent;
    private EditText addUser;
    private EditText addGroup;
    private Button addPost;
    private Post post;
    private NavController navController;
    PostFragment postFragment;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
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
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        addPost.setOnClickListener(new View.OnClickListener() {
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
        post.setTitle(addTitle.getText().toString());
        post.setContent(addContent.getText().toString());
        post.setGroup(Integer.valueOf(addGroup.getText().toString()));
        post.setUser(Integer.valueOf(addUser.getText().toString()));
        PostService.getInstance().newPost(post).enqueue(new Callback<Post>() {
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

    private void init(View view) {
        addTitle = view.findViewById(R.id.add_title);
        addContent = view.findViewById(R.id.add_content);
        addUser = view.findViewById(R.id.add_user);
        addGroup = view.findViewById(R.id.add_group);
        addPost = view.findViewById(R.id.btn_add);
    }
}