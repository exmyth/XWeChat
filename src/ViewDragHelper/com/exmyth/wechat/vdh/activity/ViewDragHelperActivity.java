package com.exmyth.wechat.vdh.activity;
import com.exmyth.wechat.R;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewDragHelperActivity extends BaseActivity {
    private LinearLayout top_bar_linear_back;
    private TextView top_bar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vdh_view_grag_helper_layout);
        top_bar_linear_back=(LinearLayout)this.findViewById(R.id.top_bar_linear_back);
        top_bar_linear_back.setOnClickListener(new CustomOnClickListener());
        top_bar_title=(TextView)findViewById(R.id.top_bar_title);
        top_bar_title.setText("ViewGragHelper使用详解");
    }
    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
           ViewDragHelperActivity.this.finish();
        }
    }
}