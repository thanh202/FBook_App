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
import com.example.fbook_app.Model.Book;
import com.example.fbook_app.R;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private final Context context;
    private List<Book> bookList = new ArrayList<>();
    public BookAdapter(Context mContext){
        context = mContext;
    }
    public void setListBook(List<Book> list){
        this.bookList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_book,parent,false);
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



        public ViewHolder(View itemView) {
            super(itemView);
            imgViewItemImgBook = (ImageView) itemView.findViewById(R.id.imgView_item_imgBook);
            tvItemNameBook = (TextView) itemView.findViewById(R.id.tv_item_nameBook);
            tvItemDescription = (TextView) itemView.findViewById(R.id.tv_item_description);
            tvItemPriceBook = (TextView) itemView.findViewById(R.id.tv_item_priceBook);
        }
        public void onBind(int position){
                Book book = bookList.get(position);
                tvItemNameBook.setText(book.getBookName());
                tvItemDescription.setText(book.getDescription());
                tvItemPriceBook.setText(book.getPriceBook());
            Glide.with(context).load(book.getImageBook())
                    .into(imgViewItemImgBook);
        }
    }
}
