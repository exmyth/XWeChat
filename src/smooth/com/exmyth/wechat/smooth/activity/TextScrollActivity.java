package com.exmyth.wechat.smooth.activity;
import java.util.ArrayList;
import java.util.List;

import com.exmyth.wechat.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.AbsoluteLayout;
import android.widget.TextView;  
  
public class TextScrollActivity extends Activity {  
    TextView tv;  
    String L = "TextScrollActivity";  
    List<String> welcomeWords = new ArrayList<String>();  
    int curIndex = 0;  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
  
        welcomeWords  
                .add("        您的下榻使我们倍感荣幸。我谨代表北京饭店全体员工向您表示诚挚的欢迎。始建于1900年的北京饭店是一座历史悠久的豪华饭店。拥有豪华典雅的客房；口味独特的佳肴；会议会展设施及娱乐健身设施。我们将竭诚为您提供满意而舒适的服务。希望您在北京饭店下榻愉快。\n 您的下榻使我们倍感荣幸。我谨代表北京饭店全体员工向您表示诚挚的欢迎。始建于1900年的北京饭店是一座历史悠久的豪华饭店。拥有豪华典雅的客房；口味独特的佳肴；会议会展设施及娱乐健身设施。我们将竭诚为您提供满意而舒适的服务。希望您在北京饭店下榻愉快。");  
        welcomeWords  
                .add("  It is an honor for you to stay at the Beijing Hotel. On behalf of the staff at the Beijing Hotel, I sincerely welcome you.Built in 1900, Beijing Hotel is a luxury hotel with a long history. We have elegant guestrooms, exquisite cuisine, convenient facilities and entertainment facilities. It is our pleasure to offer you the best services.Have a nice stay!");  
  
        setContentView(R.layout.textscroll);  
  
        tv = (TextView) findViewById(R.id.tScroll);  
          
        /** 
         * 动态设置坐标及宽和高，也可以忽略，在配置文件中设置 
         */  
        AbsoluteLayout.LayoutParams lp = (AbsoluteLayout.LayoutParams) tv  
                .getLayoutParams();  
        lp.x = 300;  
        lp.y = 300;  
        lp.width = 500;  
        lp.height = 170;  
          
        tv.setTextSize(16);  
        tv.setTextColor(Color.WHITE);  
        tv.setGravity(Gravity.LEFT);  
  
        tv.setText(welcomeWords.get(curIndex));  
  
        h.postDelayed(r, 3000);  
    }  
  
    Handler h = new Handler();   
    int i = 0;  
    Runnable r = new Runnable() {  
  
        @Override  
        public void run() {  
            int height = tv.getHeight();  
            int scrollY = tv.getScrollY();  
            int lineHeight = tv.getLineHeight();  
            int lineCount = tv.getLineCount();//总行数  
            /** 
             * textView不可见内容的高度，可以理解为偏移位移 
             */  
            int maxY = (tv.getLineCount() * tv.getLineHeight()  
                    + tv.getPaddingTop() + tv.getPaddingBottom())  
                    - tv.getHeight();  
              
            Log.e("=maxY=", maxY+"");  
            Log.e("=height=", height+"");  
            Log.e("=lineCount=", tv.getLineCount()+"");  
              
            double viewCount = Math.floor(height / lineHeight);//可见区域最大显示多少行  
            if (lineCount > viewCount) {//总行数大于可见区域显示的行数时则滚动  
  
                if (scrollY >= maxY) {  
                    tv.scrollBy(0, -maxY);  
                } else {  
                    tv.scrollBy(0, lineHeight);  
                }  
                h.postDelayed(this, 3000);  
            }  
  
        }  
  
    };  
  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
  
        switch (keyCode) {  
        case KeyEvent.KEYCODE_DPAD_UP:  
              
            break;  
        case KeyEvent.KEYCODE_DPAD_DOWN:  
              
            break;  
        case KeyEvent.KEYCODE_DPAD_RIGHT:  
              
            handle();  
            break;  
        case KeyEvent.KEYCODE_DPAD_LEFT:  
              
            handle();  
            break;  
        case KeyEvent.KEYCODE_DPAD_CENTER:  
            handle();  
            break;  
        case KeyEvent.KEYCODE_ENTER:  
            handle();  
            break;  
        case KeyEvent.KEYCODE_BACK:  
            finish();  
              
            break;  
        default:  
              
        }  
        return super.onKeyDown(keyCode, event);  
    }  
  
    public void handle() {  
  
        h.removeCallbacks(r);  
  
      
  
        curIndex = (curIndex + 1) % 2;  
  
        tv.setText(welcomeWords.get(curIndex));  
  
        h.postDelayed(r, 3000);  
  
    }  
  
    @Override  
    public void onDestroy() {  
        super.onDestroy();  
        h.removeCallbacks(r);  
    }  
  
}  