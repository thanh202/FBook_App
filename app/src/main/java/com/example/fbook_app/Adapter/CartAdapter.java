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
import com.example.fbook_app.Model.Cart;
import com.example.fbook_app.R;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private final Context context;
    private BookAdapter.OnItemClickListener onItemClickListener;
    private List<Cart> cartList = new ArrayList<>();

    private List<Book> bookList = new ArrayList<>();


    public CartAdapter(Context context) {
        this.context = context;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(BookAdapter.OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_cart, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgViewItemImgCart;
        private TextView tvItemNameNewCart;
        private TextView tvItemDescriptionCart;
        private TextView tvItemPriceCart;
        private LinearLayout rlItemSelect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewItemImgCart = (ImageView) itemView.findViewById(R.id.imgView_item_imgcart);
            tvItemNameNewCart = (TextView) itemView.findViewById(R.id.tv_item_namecart);
            tvItemDescriptionCart = (TextView) itemView.findViewById(R.id.tv_item_descriptioncart);
            tvItemPriceCart = (TextView) itemView.findViewById(R.id.tv_item_pricecart);

            rlItemSelect = itemView.findViewById(R.id.rl_item_selectCart);

        }
        public void onBind(int position) {
            Cart book = cartList.get(position);
            tvItemNameNewCart.setText(book.getBookName());
            tvItemDescriptionCart.setText(book.getDescription());
            tvItemPriceCart.setText(book.getPriceBook());
            Glide.with(context).load(book.getImageBook())
                    .into(imgViewItemImgCart);
//            rlItemSelect.setOnClickListener(v -> {
//                onItemClickListener.onItemClick(book);
//            });
        }

    }
}
