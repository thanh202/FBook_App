package com.example.fbook_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.Model.Response.CategoryResponse;
import com.example.fbook_app.Model.Response.SearchResponse;
import com.example.fbook_app.R;

import java.util.ArrayList;
import java.util.List;

public class TheLoaiAdapter extends RecyclerView.Adapter<TheLoaiAdapter.ViewHolder> {
    private final Context context;
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }
    private List<CategoryResponse.Result> categoryList = new ArrayList<>();
    public TheLoaiAdapter(Context mContext) {
        context = mContext;
    }

    public void setCategoryList(List<CategoryResponse.Result> list) {
        this.categoryList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_the_loai, parent, false);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgViewItemImgTheLoai;
        private TextView tvItemNameTheLoai;
        private LinearLayout rlItemSelect;



        public ViewHolder(View itemView) {
            super(itemView);
            imgViewItemImgTheLoai = (ImageView) itemView.findViewById(R.id.imgView_item_imgTheLoai);
            tvItemNameTheLoai = (TextView) itemView.findViewById(R.id.tv_item_nameTheLoai);
            rlItemSelect = itemView.findViewById(R.id.rl_item_selectTheLoai);

        }

        public void onBind(int position) {
            CategoryResponse.Result category = categoryList.get(position);
            tvItemNameTheLoai.setText(category.getCatName());
            String imgCat = RetrofitClient.BASE_URL+category.getImgCat();
            Glide.with(context).load(imgCat)
                    .into(imgViewItemImgTheLoai);
            rlItemSelect.setOnClickListener(v -> {
                onItemClickListener.onItemClick(category.getCatName());
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(String nameCategory);
    }
}
