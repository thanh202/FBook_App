package com.example.fbook_app.HomeActivity.LibraryFragment.DocSach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.example.fbook_app.Model.Response.LibraryResponse;
import com.example.fbook_app.R;

public class ChapterFragment extends Fragment {
    public static ChapterFragment newInstance() {
        ChapterFragment fragment = new ChapterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    private NestedScrollView scrollView;
    private TextView tvContent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_chapter, container, false);
        setData(mView);
        return mView;
    }
    private void setData(View mView) {

        scrollView = (NestedScrollView) mView.findViewById(R.id.scrollView);
        tvContent = (TextView) mView.findViewById(R.id.tv_content);
        scrollView.smoothScrollTo(0, 0);

    }
}
