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
import com.example.fbook_app.Model.Book;
import com.example.fbook_app.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteBookAdapter extends RecyclerView.Adapter<FavoriteBookAdapter.ViewHolder> {
    private final Context context;
    private OnItemClickListener onItemClickListener;
    private List<Book> bookList = new ArrayList<>();
    public FavoriteBookAdapter(Context mContext){
        context = mContext;
    }
    public void setListBook(List<Book> list){
        this.bookList = list;
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_favorite_book,parent,false);
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
        private ImageView imgViewItemImgBook;
        private TextView tvItemNameBook;
        private TextView tvItemDescription;
        private TextView tvItemPriceBook;

        private LinearLayout llSelectItem;

        public ViewHolder(View itemView) {
            super(itemView);
            imgViewItemImgBook = (ImageView) itemView.findViewById(R.id.imgView_item_imgBook);
            tvItemNameBook = (TextView) itemView.findViewById(R.id.tv_item_nameBook);
            tvItemDescription = (TextView) itemView.findViewById(R.id.tv_item_description);
            tvItemPriceBook = (TextView) itemView.findViewById(R.id.tv_item_priceBook);
            llSelectItem = itemView.findViewById(R.id.ll_select_item_favorite_book);
        }
        public void onBind(int position){
                Book book = bookList.get(position);
                tvItemNameBook.setText(book.getBookName());
                tvItemDescription.setText(book.getDescription());
                tvItemPriceBook.setText(book.getPriceBook());
            Glide.with(context).load(book.getImageBook())
                    .into(imgViewItemImgBook);
            llSelectItem.setOnClickListener(v -> {
                onItemClickListener.onItemClick(book);
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Book book);
    }
}
