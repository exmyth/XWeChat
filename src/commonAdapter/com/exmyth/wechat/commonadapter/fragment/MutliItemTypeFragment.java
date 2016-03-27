package com.exmyth.wechat.commonadapter.fragment;

import java.util.ArrayList;

import com.exmyth.wechat.R;
import com.exmyth.wechat.commonadapter.adapter.ChatAdapter;
import com.exmyth.wechat.commonadapter.model.ChatMessage;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;

/**
 * Created by zhy on 15/9/4.
 */
public class MutliItemTypeFragment extends ListFragment
{
    private ArrayList<ChatMessage> mDatas = new ArrayList<ChatMessage>();


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initDatas();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        getListView().setDivider(null);


        setListAdapter(new ChatAdapter(getActivity(),mDatas));
    }

    private void initDatas()
    {

        ChatMessage msg = null;
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you 1",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you 1",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you 2",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you 2",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you 3",
                null, false);
        mDatas.add(msg);

        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you 3",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you 4",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you 4",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you 5",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you 5",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you 6",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you 6",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you 7",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you 7",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you 8",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you 8",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you 9",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you 9",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you 10",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you 10",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you 11",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you 11",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you 12",
                null, false);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.renma, "renma", "where are you 12",
                null, true);
        mDatas.add(msg);
        msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you 13",
                null, false);
        mDatas.add(msg);

    }
}
