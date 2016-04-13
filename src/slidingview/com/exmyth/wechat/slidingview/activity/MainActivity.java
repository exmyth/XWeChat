package com.exmyth.wechat.slidingview.activity;

import java.util.ArrayList;
import java.util.List;

import com.exmyth.wechat.R;
import com.exmyth.wechat.slidingview.view.ListViewCompat;
import com.exmyth.wechat.slidingview.view.SlideView;
import com.exmyth.wechat.slidingview.view.SlideView.OnSlideListener;
import com.exmyth.wechat.slidingview.view.XRelativeLayout;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,OnItemClickListener{

    private static final String TAG = "MainActivity";

    private ListViewCompat mListView;

    private List<MessageItem> mMessageItems = new ArrayList<MainActivity.MessageItem>();

    private SlideView mLastSlideViewWithStatusOn;
    
    private XRelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_view_activity_main);

        initView();
        
    }

    private void initView() {
    	
        mListView = (ListViewCompat) findViewById(R.id.list);
        mRelativeLayout = (XRelativeLayout) findViewById(R.id.wrapper);

        for (int i = 0; i < 20; i++) {
            MessageItem item = new MessageItem();
            if (i % 20 == 0) {
                item.iconRes = R.drawable.wechat_icon;
                item.title = "腾讯新闻";
                item.msg = "青岛爆炸满月：大量鱼虾死亡";
                item.time = "晚上18:18";
            } else {
                item.iconRes = R.drawable.wechat_icon;
                item.title = "微信团队";
                item.msg = "欢迎你使用微信";
                item.time = "12月18日";
            }
            mMessageItems.add(item);
        }
        
        mListView.setAdapter(new SlideAdapter());
        mListView.setOnItemClickListener(this);
        
        mRelativeLayout.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
			        case MotionEvent.ACTION_DOWN: {
			        	mRelativeLayout.setIntercept(false);
						if (mLastSlideViewWithStatusOn != null) {
			                mLastSlideViewWithStatusOn.shrink();
			            }
						break;
		        	}
			        default:break;
		        }
				return true;
			}
		});
    }

    private class SlideAdapter extends BaseAdapter implements OnSlideListener{

        private LayoutInflater mInflater;

        SlideAdapter() {
            super();
            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return mMessageItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mMessageItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            SlideView slideView = (SlideView) convertView;
            
            if (slideView == null) {
                View itemView = mInflater.inflate(R.layout.sliding_view_list_item, null);

                slideView = new SlideView(MainActivity.this);
                slideView.setContentView(itemView);

                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(this);
                slideView.setTag(holder);
            } else {
                holder = (ViewHolder) slideView.getTag();
            }
            
            //回退的效果
            MessageItem item = mMessageItems.get(position);
            item.slideView = slideView;
            item.slideView.shrink();
            
            holder.icon.setImageResource(item.iconRes);
            holder.title.setText(item.title);
            holder.msg.setText(item.msg);
            holder.time.setText(item.time);
            holder.deleteHolder.setOnClickListener(MainActivity.this);

            return slideView;
        }

        @Override
        public void onSlide(View view, int status) {
            if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
                mLastSlideViewWithStatusOn.shrink();
            }

            if (status == SLIDE_STATUS_ON) {
                mLastSlideViewWithStatusOn = (SlideView) view;
                mRelativeLayout.setIntercept(true);
            }
            else{
            	mRelativeLayout.setIntercept(false);
            }
        }

    }

    public class MessageItem {
        public int iconRes;
        public String title;
        public String msg;
        public String time;
        public SlideView slideView;
    }

    private static class ViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView msg;
        public TextView time;
        public ViewGroup deleteHolder;

        ViewHolder(View view) {
            icon = (ImageView) view.findViewById(R.id.icon);
            title = (TextView) view.findViewById(R.id.title);
            msg = (TextView) view.findViewById(R.id.msg);
            time = (TextView) view.findViewById(R.id.time);
            deleteHolder = (ViewGroup)view.findViewById(R.id.holder);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        Log.e(TAG, "onItemClick position=" + position);
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.holder) {
            Log.e(TAG, "onClick v=" + v);
            Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
            
        }
    }


}