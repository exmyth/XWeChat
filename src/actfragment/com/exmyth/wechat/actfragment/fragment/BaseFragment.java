package com.exmyth.wechat.actfragment.fragment;

import com.exmyth.wechat.actfragment.activity.BaseActivity;
import com.exmyth.wechat.actfragment.model.Functions;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;


/**

 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity mBaseActivity;

    /**
     * 函数的集合
     */
    protected Functions mFunctions;

    /**
     * activity调用此方法进行设置Functions
     * @param functions
     */
    public void setFunctions(Functions functions){
        this.mFunctions = functions;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof BaseActivity){
            mBaseActivity = (BaseActivity)activity;
            mBaseActivity.setFunctionsForFragment(getId());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
