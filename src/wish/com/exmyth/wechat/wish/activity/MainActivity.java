package com.exmyth.wechat.wish.activity;

import com.exmyth.wechat.R;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ImageView mPhoto;
	private EditText mWord;
	private IWXAPI api;
	private Button mShare;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wish_activity_main);
		initView();
		initWXAPI();
	}

	private void initWXAPI() {
		//通过WXAPIFactory工厂，获取IWXAPI的实例
		api = WXAPIFactory.createWXAPI(this, "wx616d9340be545484");
		api.registerApp("wx616d9340be545484");
	}

	private void initView() {
		mPhoto = (ImageView)findViewById(R.id.photo);
		mPhoto.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK,null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, 100);
			}
		});
		
		mWord = (EditText)findViewById(R.id.word);
		mWord.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/wish.ttf"));
		
		mShare = (Button)findViewById(R.id.share);
		mShare.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				wechatShare();
//				mPhoto.setImageBitmap(generateWish());
				mShare.setVisibility(View.VISIBLE);
			}
		});
	}
	
	private void wechatShare() {
		// 初始化一个WXTextObject对象
//		WXTextObject textObj = new WXTextObject();
//		textObj.text = text;

		WXWebpageObject webpkg = new WXWebpageObject();
		
		// 用WXTextObject对象初始化一个WXMediaMessage对象
		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = new WXImageObject(generateWish());
//		msg.mediaObject = textObj;
		// 发送文本类型的消息时，title字段不起作用
		// msg.title = "Will be ig  nored";
//		msg.description = text;

		// 构造一个Req
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		
		// 调用api接口发送数据到微信
		api.sendReq(req);
	}
	
	private Bitmap generateWish() {
		mShare.setVisibility(View.GONE);
		View view = getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		return view.getDrawingCache();
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK&&requestCode == 100){
			if(data !=null){
				mPhoto.setImageURI(data.getData());
			}
		}
	}
}
