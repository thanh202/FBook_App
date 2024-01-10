package com.example.fbook_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fbook_app.Model.Response.DanhGiaResponse;
import com.example.fbook_app.R;

import java.util.ArrayList;
import java.util.List;

public class DanhGiaAdapter extends RecyclerView.Adapter<DanhGiaAdapter.ViewHolder> {
    private final Context context;
    private List<DanhGiaResponse.Result> danhgiaList = new ArrayList<>();

    public DanhGiaAdapter(Context mContext) {
        context = mContext;
    }

    public void setDanhgiaList(List<DanhGiaResponse.Result> danhgiaList) {
        this.danhgiaList = danhgiaList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danhgia, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return danhgiaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNameUser;
        private TextView tvComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNameUser = itemView.findViewById(R.id.nameUser_danhgia);
            tvComment = itemView.findViewById(R.id.noidung_danhgia);
        }

        public void onBind(int position) {
            DanhGiaResponse.Result danhgia = danhgiaList.get(position);
            tvComment.setText(danhgia.getComment());
            tvNameUser.setText(danhgia.getUserName());
        }
    }
}
