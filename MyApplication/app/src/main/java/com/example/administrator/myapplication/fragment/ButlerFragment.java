package com.example.administrator.myapplication.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.ChatListAdapter;
import com.example.administrator.myapplication.entity.ChatListData;

import java.util.ArrayList;
import java.util.List;


public class ButlerFragment extends Fragment implements View.OnClickListener {

    private ListView mChatListView;
    private Button btn_left, btn_right;
    private List<ChatListData> mList = new ArrayList<>();
    private ChatListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler,null);
        findView(view);
        return view;
    }

    //初始化View
    private void findView(View view) {
        mChatListView = view.findViewById(R.id.mChatListView);
        btn_left = view.findViewById(R.id.btn_left);
        btn_left.setOnClickListener(this);
        btn_right = view.findViewById(R.id.btn_right);
        btn_right.setOnClickListener(this);

        //设置适配器
        adapter = new ChatListAdapter(getActivity(),mList);
        mChatListView.setAdapter(adapter);
    }


    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_left:

                break;
            case R.id.btn_right:

                break;
        }
    }

    //添加左边文本
    private  void addLeftItem(String text){
        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_LEFT_TEXT);
    }

    //添加右边文本
    private  void addRightItem(String text){

    }

}
