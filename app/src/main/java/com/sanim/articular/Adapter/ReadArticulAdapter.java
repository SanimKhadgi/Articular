package com.sanim.articular.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanim.articular.Activity.ReadArticulActivity;
import com.sanim.articular.Interface.IRecyclerItemClickListener;
import com.sanim.articular.Model.Article;
import com.sanim.articular.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import ss.com.bannerslider.ImageLoadingService;

public class ReadArticulAdapter extends RecyclerView.Adapter<ReadArticulAdapter.MyViewHolder> {

    private Context context;
    private List<Article> articleList;
    private LayoutInflater inflater;

    public ReadArticulAdapter(Context context,List<Article> articleList){
        this.context=context;
        this.articleList=articleList;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       Picasso.get().load(articleList.get(position).Image1).into(holder.imageArticle);
       holder.textArticleName.setText(articleList.get(position).Name);

       holder.setRecyclerItemClickListener(new IRecyclerItemClickListener() {
           @Override
           public void onClick(View view, int position) {
               Article article=articleList.get(position);
               Intent intent=new Intent(context, ReadArticulActivity.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               intent.putExtra("id",article.id.toString());
               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
       private ImageView imageArticle;
       private TextView textArticleName;
       private IRecyclerItemClickListener recyclerItemClickListener;

       public MyViewHolder(@NonNull View itemView){
           super(itemView);

           imageArticle=itemView.findViewById(R.id.image_list);
           textArticleName=itemView.findViewById(R.id.text_list_name);

           itemView.setOnClickListener(this);
       }

        @Override
        public void onClick(View view) {
            recyclerItemClickListener.onClick(view,getAdapterPosition());
        }

        public void setRecyclerItemClickListener(IRecyclerItemClickListener recyclerItemClickListener){
           this.recyclerItemClickListener=recyclerItemClickListener;
        }
    }
}
