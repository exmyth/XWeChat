package com.exmyth.wechat.vdh.fragment;

import com.exmyth.wechat.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * 当前类注释:第一个Fragment
 * 项目名：FastDev4Android
 * 包名：com.exmyth.wechat.vdh.fragment
 * 作者：江清清 on 15/11/16 13:45
 * 
 * 
 * 公司：江苏中天科技软件技术有限公司
 */
public class OneFragment  extends Fragment {
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mView==null){
            mView=inflater.inflate(R.layout.vdh_one_frag_layout,container,false);
        }
        return mView;
    }
}
