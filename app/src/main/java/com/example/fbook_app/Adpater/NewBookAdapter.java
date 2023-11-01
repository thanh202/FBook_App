package com.example.fbook_app.Adpater;

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

public class NewBookAdapter extends RecyclerView.Adapter<NewBookAdapter.ViewHolder> {
    private final Context context;
    private List<Book> bookList = new ArrayList<>();
    public NewBookAdapter(Context mContext){
        context = mContext;
    }
    public void setListBook(List<Book> list){
        this.bookList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_new_book,parent,false);
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
        private ImageView imgViewItemImgNewBook;
        private TextView tvItemNameNewBook;
        private TextView tvItemDescriptionNewBook;
        private TextView tvItemPriceNewBook;
        private ImageView btnItemLike;
        private ImageView btnItemBuy;




        public ViewHolder(View itemView) {
            super(itemView);
            imgViewItemImgNewBook = (ImageView) itemView.findViewById(R.id.imgView_item_imgNewBook);
            tvItemNameNewBook = (TextView) itemView.findViewById(R.id.tv_item_nameNewBook);
            tvItemDescriptionNewBook = (TextView) itemView.findViewById(R.id.tv_item_descriptionNewBook);
            tvItemPriceNewBook = (TextView) itemView.findViewById(R.id.tv_item_priceNewBook);
//            btnItemLike = (ImageView) itemView.findViewById(R.id.btn_item_like);
//            btnItemBuy = (ImageView) itemView.findViewById(R.id.btn_item_buy);
        }
        public void onBind(int position){
                Book book = bookList.get(position);
                tvItemNameNewBook.setText(book.getBookName());
                tvItemDescriptionNewBook.setText(book.getDescription());
                tvItemPriceNewBook.setText(book.getPriceBook());
            Glide.with(context).load(book.getImageBook())
                    .into(imgViewItemImgNewBook);
        }
    }
}
