package com.exmyth.wechat.slidingview.view;

import com.exmyth.wechat.slidingview.activity.MainActivity.MessageItem;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class ListViewCompat extends ListView {

    private static final String TAG = "ListViewCompat";

    private SlideView mFocusedItemView;

//	private int x;

//	private int y;

    public ListViewCompat(Context context) {
        super(context);
    }

    public ListViewCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewCompat(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void shrinkListItem(int position) {
        View item = getChildAt(position);

        if (item != null) {
            try {
                ((SlideView) item).shrink();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN: {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int position = pointToPosition(x, y);
            Log.e(TAG, "postion=" + position);
            if (position != INVALID_POSITION) {
                MessageItem data = (MessageItem) getItemAtPosition(position);
                mFocusedItemView = data.slideView;
                Log.e(TAG, "FocusedItemView=" + mFocusedItemView);
            }
        }
        default:
            break;
        }

        if (mFocusedItemView != null) {
            mFocusedItemView.onRequireTouchEvent(event);
        }
        return super.onTouchEvent(event);
//        return handlerTouchResult(event);
    }

    /*
    private boolean handlerTouchResult(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				return super.onTouchEvent(event);
			}
			case MotionEvent.ACTION_MOVE: {
				return super.onTouchEvent(event);
			}
        	case MotionEvent.ACTION_UP: {
	        	float x2 = event.getX(); 
	            float y2 = event.getY(); 
	            if (Math.abs(x - x2) < 10) { 
	                return true; 
	            } 
	            if(Math.abs(x - x2) >60){  // 真正的onTouch事件 
	            	return false;
	            }
	        }
        	default:break;
        }
		return super.onTouchEvent(event);// 距离较小，当作click事件来处理 
	}
    */

}
