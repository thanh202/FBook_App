package com.example.fbook_app.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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
import com.example.fbook_app.Model.Response.BookResponse;
import com.example.fbook_app.Model.Response.ChapterResponse;
import com.example.fbook_app.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private final Context context;
    private List<ChapterResponse.Result> chapterList = new ArrayList<>();
    private int selectedPosition = RecyclerView.NO_POSITION;
    private boolean isFirstItemSelected = true;

    public ChapterAdapter(Context mContext) {
        context = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    public void setListChapter(List<ChapterResponse.Result> list) {
        this.chapterList = list;
        Log.e("zzzzz", "setListChapter: " + list.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }
    public void setSelectedPosition(int position) {
        selectedPosition = position;
        isFirstItemSelected = false;
        notifyDataSetChanged(); // Cập nhật lại RecyclerView để thay đổi màu chữ của phần tử được chọn
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llItemChapter;
        private TextView tvTitleChapter;

        public ViewHolder(View itemView) {
            super(itemView);
            llItemChapter = (LinearLayout) itemView.findViewById(R.id.ll_item_chapter);
            tvTitleChapter = (TextView) itemView.findViewById(R.id.tv_title_chapter);
        }

        public void onBind(int position) {
            ChapterResponse.Result chapterResponse = chapterList.get(position);
            tvTitleChapter.setText(chapterResponse.getTitle());
            onItemClickListener.onGetPositionChapter(position);
            llItemChapter.setOnClickListener(v -> {
                int position1 = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Lưu vị trí phần tử được chọn và thông báo thay đổi
                    setSelectedPosition(position1);
                }
                onItemClickListener.onItemClick(chapterResponse.getContent());
            });
            if (position == selectedPosition || (isFirstItemSelected && position == 0)) {
                tvTitleChapter.setTextColor(Color.RED); // Hoặc màu bạn muốn đặt
            } else {
                tvTitleChapter.setTextColor(Color.BLACK); // Hoặc màu chữ mặc định
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String content);
        void onGetPositionChapter(int position);
    }
}
