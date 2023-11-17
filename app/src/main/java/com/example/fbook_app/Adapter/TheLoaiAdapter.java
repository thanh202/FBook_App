package com.example.fbook_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbook_app.Model.Category;
import com.example.fbook_app.R;

import java.util.ArrayList;
import java.util.List;

public class TheLoaiAdapter extends RecyclerView.Adapter<TheLoaiAdapter.ViewHolder> {
    private final Context context;
    private List<Category> categoryList = new ArrayList<>();
    public TheLoaiAdapter(Context mContext){
        context = mContext;
    }
    public void setListBook(List<Category> categoryList){
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_the_loai,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgViewItemImgTheLoai;
        private TextView tvItemNameTheLoai;



        public ViewHolder(View itemView) {
            super(itemView);
            imgViewItemImgTheLoai = (ImageView) itemView.findViewById(R.id.imgView_item_imgTheLoai);
            tvItemNameTheLoai = (TextView) itemView.findViewById(R.id.tv_item_nameTheLoai);
        }
        public void onBind(int position){
                Category category = categoryList.get(position);
            tvItemNameTheLoai.setText(category.getName());
            Glide.with(context).load(category.getImg())
                    .into(imgViewItemImgTheLoai);
        }
    }
}
