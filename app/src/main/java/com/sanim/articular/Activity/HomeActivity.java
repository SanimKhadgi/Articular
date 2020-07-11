package com.sanim.articular.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sanim.articular.Adapter.ReadArticulAdapter;
import com.sanim.articular.Interface.IArticulLoadDone;
import com.sanim.articular.Model.Article;
import com.sanim.articular.R;
import com.sanim.articular.common.Common;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class HomeActivity extends AppCompatActivity implements IArticulLoadDone {

    private RecyclerView recycler_articul_list;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CircleImageView ProfilePicture;

    AlertDialog alertDialog;
    DatabaseReference articul;
    IArticulLoadDone articalListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ProfilePicture=findViewById(R.id.profile_picture);

        ProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        articul= FirebaseDatabase.getInstance().getReference("Article");
        articalListener=this;

        recycler_articul_list=findViewById(R.id.recycler_articleList);
        recycler_articul_list.setHasFixedSize(true);
        recycler_articul_list.setLayoutManager(new GridLayoutManager(this,1));

        swipeRefreshLayout=findViewById(R.id.swipe_up_refresh);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadArticul();
            }
        });
    }

    private void loadArticul() {
        alertDialog=new SpotsDialog.Builder().setContext(this)
                .setCancelable(false).setMessage("Please wait...").build();

        if(!swipeRefreshLayout.isRefreshing())
            alertDialog.show();

        articul.addListenerForSingleValueEvent(new ValueEventListener() {
            List<Article> article_load=new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot articleSnapshot:dataSnapshot.getChildren()){
                    Article article=articleSnapshot.getValue(Article.class);
                    article_load.add(article);
                }
                articalListener.onArticulLoadDoneListener(article_load);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onArticulLoadDoneListener(List<Article> articleList) {
        Common.articleList=articleList;

        recycler_articul_list.setAdapter(new ReadArticulAdapter(getBaseContext(),articleList));

        if(!swipeRefreshLayout.isRefreshing())
            alertDialog.dismiss();
    }
}