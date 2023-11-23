package com.example.fbook_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fbook_app.ApiNetwork.RetrofitClient;
import com.example.fbook_app.Model.Response.BookResponse;
import com.example.fbook_app.R;

import java.util.ArrayList;
import java.util.List;

public class TopBookAdapter extends RecyclerView.Adapter<TopBookAdapter.ViewHolder> {
    private final Context context;
    private OnItemClickListener onItemClickListener;
    private List<BookResponse.Result> bookList = new ArrayList<>();

    public TopBookAdapter(Context mContext) {
        context = mContext;
    }

    public void setListBook(List<BookResponse.Result> list) {
        this.bookList = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_top_book, parent, false);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgViewItemImgTopBook;
        private TextView tvItemNameTopBook;
        private LinearLayout cvSelectItem;

        public ViewHolder(View itemView) {
            super(itemView);
            imgViewItemImgTopBook = (ImageView) itemView.findViewById(R.id.imgView_item_imgTopBook);
            tvItemNameTopBook = (TextView) itemView.findViewById(R.id.tv_item_nameTopBook);
            cvSelectItem = itemView.findViewById(R.id.cv_select_item_top_book);
        }

        public void onBind(int position) {
            BookResponse.Result book = bookList.get(position);
            tvItemNameTopBook.setText(book.getBookName());
            String imgBook = RetrofitClient.BASE_URL+book.getImageBook();
            Glide.with(context).load(imgBook)
                    .into(imgViewItemImgTopBook);
            cvSelectItem.setOnClickListener(v -> {
                onItemClickListener.onItemClick(book);
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(BookResponse.Result book);
    }
}
