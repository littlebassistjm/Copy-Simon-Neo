package ph.edu.dlsu.mobapde.copy_simon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivityFirebase extends AppCompatActivity {


    RecyclerView rvPosts;
    EditText etUsername, etPost;
    Button buttonPost, btnClassic, btnSpeed;
    String key;

    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    //FirebaseRecyclerAdapter<Post, PostViewHolder> postPostViewHolderFirebaseRecyclerAdapter;
    FirebaseRecyclerAdapter<ScoreFirebase, PostViewHolder> postPostViewHolderFirebaseRecyclerAdapter;
    DatabaseReference postDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_firebase);

        rvPosts = (RecyclerView) findViewById(R.id.rv_posts);
        etPost = (EditText) findViewById(R.id.et_post);
        etUsername = (EditText) findViewById(R.id.et_username);
        buttonPost = (Button) findViewById(R.id.button_post);

        btnClassic = (Button) findViewById(R.id.btnClassic);
        btnSpeed = (Button) findViewById(R.id.btnSpeed);

        btnClassic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("ClassicHighScore");
                Query query = databaseReference.orderByChild("score").limitToLast(10);
                databaseReference2 = FirebaseDatabase.getInstance().getReference();
                postDatabaseReference = databaseReference2.child("ClassicHighScore");

                postPostViewHolderFirebaseRecyclerAdapter
                        = new FirebaseRecyclerAdapter<ScoreFirebase, PostViewHolder>
                        (ScoreFirebase.class, R.layout.list_post, PostViewHolder.class, query) {
                    @Override
                    protected void populateViewHolder(PostViewHolder viewHolder, ScoreFirebase model, int position) {
                        viewHolder.tvPost.setText(model.getScore());
                        viewHolder.tvUsername.setText(model.getName());


                    }
                };

                rvPosts.setAdapter(postPostViewHolderFirebaseRecyclerAdapter);
                //rvPosts.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);
                rvPosts.setLayoutManager(layoutManager);
            }
        });

        btnSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("SpeedHighScore");
                Query query = databaseReference.orderByChild("score").limitToLast(10);
                databaseReference2 = FirebaseDatabase.getInstance().getReference();
                postDatabaseReference = databaseReference2.child("SpeedHighScore");

                postPostViewHolderFirebaseRecyclerAdapter
                        = new FirebaseRecyclerAdapter<ScoreFirebase, PostViewHolder>
                        (ScoreFirebase.class, R.layout.list_post, PostViewHolder.class, query) {
                    @Override
                    protected void populateViewHolder(PostViewHolder viewHolder, ScoreFirebase model, int position) {
                        viewHolder.tvPost.setText(model.getScore());
                        viewHolder.tvUsername.setText(model.getName());

                        /*String key = getRef(position).getKey();
                        viewHolder.itemView.setTag(key);
                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getBaseContext(), ViewPostActivity.class);
                                intent.putExtra(Post.EXTRA_KEY, view.getTag().toString());
                                startActivity(intent);
                            }
                        });*/

                    }
                };

                rvPosts.setAdapter(postPostViewHolderFirebaseRecyclerAdapter);
                //rvPosts.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);
                rvPosts.setLayoutManager(layoutManager);

            }
        });





        //databaseReference = FirebaseDatabase.getInstance().getReference();

        //final DatabaseReference postDatabaseReference = databaseReference.child("Post");


        /*postPostViewHolderFirebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<Post, PostViewHolder>
                (Post.class, R.layout.list_post, PostViewHolder.class, postDatabaseReference) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, Post model, int position) {
                viewHolder.tvPost.setText(model.getPost());
                viewHolder.tvUsername.setText(model.getUsername());

                String key = getRef(position).getKey();
                viewHolder.itemView.setTag(key);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getBaseContext(), ViewPostActivity.class);
                        intent.putExtra(Post.EXTRA_KEY, view.getTag().toString());
                        startActivity(intent);
                    }
                });

            }
        };*/



        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = postDatabaseReference.push().getKey();

                //Post post = new Post();
                //post.setPost(etPost.getText().toString());
                //post.setUsername(etUsername.getText().toString());

                //postDatabaseReference.child(key).setValue(post);

                ScoreFirebase cscore = new ScoreFirebase();
                cscore.setScore(etPost.getText().toString());
                cscore.setName(etUsername.getText().toString());

                postDatabaseReference.child(key).setValue(cscore);
            }
        });
    }
}
