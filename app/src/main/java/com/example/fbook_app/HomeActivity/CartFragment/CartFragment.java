package com.example.fbook_app.HomeActivity.CartFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fbook_app.Adapter.CartAdapter;
import com.example.fbook_app.Adapter.TheLoaiAdapter;
import com.example.fbook_app.Model.Book;
import com.example.fbook_app.Model.Cart;
import com.example.fbook_app.R;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {

    private CartAdapter adapter;
    private RecyclerView rclCart;

    private View mView;


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_cart, container, false);

        rclCart = mView.findViewById(R.id.recycler_cart);

        List<Book> list = new ArrayList<>();
        Book book1 = new Book(1, "Đấu phá thương khung P5", "DuckZang", "23-11-2003", "50.000 vnđ", "Già nam học viện nơi......", R.drawable.img_top_book, 10, "Tiểu Thuyết");
        Book book2 = new Book(1, "Đấu phá thương khung P5", "DuckZang", "23-11-2003", "50.000 vnđ", "Già nam học viện nơi......", R.drawable.img_top_book, 20, "Tiểu Thuyết");
        Book book3 = new Book(1, "Đấu phá thương khung P5", "DuckZang", "23-11-2003", "50.000 vnđ", "Già nam học viện nơi......", R.drawable.img_book, 13, "Tiểu Thuyết");
        Book book4 = new Book(1, "Đấu phá thương khung P5", "DuckZang", "23-11-2003", "50.000 vnđ", "Già nam học viện nơi......", R.drawable.img_book, 40, "Tiểu Thuyết");
        Book book5 = new Book(1, "Đấu phá thương khung P5", "DuckZang", "23-11-2003", "50.000 vnđ", "Già nam học viện nơi......", R.drawable.img_book, 10, "Tiểu Thuyết");

        List<Cart> listCart = new ArrayList<>();
        Cart cart1=new Cart(1,"Đấu phá thương khung","1 con mèo cộng 2 con mèo ...","20.0010vnđ",R.drawable.img_book);
        Cart cart2=new Cart(1,"Đấu phá thương khung","1 con mèo cộng 2 con mèo ...","20.0020vnđ",R.drawable.img_book);
        Cart cart3=new Cart(1,"Đấu phá thương khung","1 con mèo cộng 2 con mèo ...","20.0030vnđ",R.drawable.img_book);
        Cart cart4=new Cart(1,"Đấu phá thương khung","1 con mèo cộng 2 con mèo ...","20.0040vnđ",R.drawable.img_top_book);
        Cart cart5=new Cart(1,"Đấu phá thương khung","1 con mèo cộng 2 con mèo ...","20.0050vnđ",R.drawable.img_top_book);


        list.add(book1);
        list.add(book2);
        list.add(book3);
        list.add(book4);
        list.add(book5);

        listCart.add(cart1);
        listCart.add(cart2);
        listCart.add(cart3);
        listCart.add(cart4);
        listCart.add(cart5);

        adapter = new CartAdapter(getContext());
        adapter.setCartList(listCart);
        rclCart.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        rclCart.setAdapter(adapter);




        return  mView;
    }
}