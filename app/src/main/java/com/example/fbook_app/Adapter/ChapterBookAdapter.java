package com.example.fbook_app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbook_app.Model.Response.BookResponse;
import com.example.fbook_app.R;

import java.util.ArrayList;
import java.util.List;

public class ChapterBookAdapter extends RecyclerView.Adapter<ChapterBookAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private final Context context;
    private Boolean isSelected = false;
    private List<Integer> bookList = new ArrayList<>();
    public ChapterBookAdapter(Context mContext){
        context = mContext;
    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.onItemClickListener = itemClickListener;
    }
    public void setListChapterBook(BookResponse.Result book){
        int chapter = Integer.parseInt(book.getChapter());
        for (int i = 1;i <= chapter;i++){
            this.bookList.add(i);
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_chapter_book,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvItemChapter;
        private CardView cvSelectItem;
        public ViewHolder(View itemView) {
            super(itemView);
            tvItemChapter = (TextView) itemView.findViewById(R.id.tv_item_chapter);
            cvSelectItem = itemView.findViewById(R.id.cv_select_item);
        }
        @SuppressLint("ResourceAsColor")
        public void onBind(int position){
            String chapter = String.valueOf(bookList.get(position));
                tvItemChapter.setText(chapter);
            cvSelectItem.setOnClickListener(v -> {
//                if (isSelected){
//                    cvSelectItem.setCardBackgroundColor(Color.GRAY);
//                }else{
//                    cvSelectItem.setCardBackgroundColor(Color.YELLOW);
//                }
//                isSelected = !isSelected;
                onItemClickListener.onItemClick();
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick();
    }
}
