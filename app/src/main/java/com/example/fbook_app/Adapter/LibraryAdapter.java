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
import com.example.fbook_app.Model.Response.LibraryResponse;
import com.example.fbook_app.Model.Response.ListFavouriteResponse;
import com.example.fbook_app.R;

import java.util.ArrayList;
import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {
    private final Context context;
    private OnReadClickListener onItemClickListener;
    private List<LibraryResponse.Result> bookList = new ArrayList<>();

    public LibraryAdapter(Context mContext) {
        context = mContext;
    }

    public void setListBook(List<LibraryResponse.Result> list) {
        this.bookList = list;
        notifyDataSetChanged();
    }
    public void setOnReadClickListener(OnReadClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_library, parent, false);
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
        private ImageView imgViewItemImgBook;
        private TextView tvItemNameBook;
        private TextView btnDocNgay;

        public ViewHolder(View itemView) {
            super(itemView);
            imgViewItemImgBook = (ImageView) itemView.findViewById(R.id.imgView_item_imgBook);
            tvItemNameBook = (TextView) itemView.findViewById(R.id.tv_item_nameBook);
            btnDocNgay = (TextView) itemView.findViewById(R.id.btn_docNgay);
        }

        public void onBind(int position) {
            LibraryResponse.Result book = bookList.get(position);
            tvItemNameBook.setText(book.getBookName());
            String imgBook = RetrofitClient.BASE_URL + book.getImageBook();
            Glide.with(context).load(imgBook)
                    .into(imgViewItemImgBook);
            btnDocNgay.setOnClickListener(v -> {
                onItemClickListener.onItemClick(book);
            });
        }
    }
    public interface OnReadClickListener {
        void onItemClick(LibraryResponse.Result book);
    }

}
