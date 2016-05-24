package com.exmyth.wechat.lyric.activity;

import java.io.File;
import java.io.IOException;

import com.exmyth.wechat.R;
import com.exmyth.wechat.lyric.model.Lyric;
import com.exmyth.wechat.lyric.model.PlayListItem;
import com.exmyth.wechat.lyric.view.LyricView;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

public class LyricDemo extends Activity {
	private MediaPlayer mp;
	private LyricView lyricView;
	private String path = "/sdcard/MP3/a.mp3";
	public static Lyric mLyric;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lyric_activity_main);
		lyricView = (LyricView) findViewById(R.id.audio_lrc);
		mp = new MediaPlayer();
		mp.reset();
		try {
			mp.setDataSource(path);
			mp.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mp.start();

		PlayListItem pli = new PlayListItem("Because Of You",
				"/sdcard/MP3/a.mp3", 0L, true);
		mLyric = new Lyric(new File(
				"/sdcard/MP3/a.lrc"), pli);
		// System.out.println("mLyric" + mLyric.list);
		lyricView.setmLyric(mLyric);
		lyricView.setSentencelist(mLyric.list);
		lyricView.setNotCurrentPaintColor(Color.GREEN);
		lyricView.setCurrentPaintColor(Color.YELLOW);
		lyricView.setLrcTextSize(20);
		lyricView.setTexttypeface(Typeface.SERIF);
		lyricView.setBrackgroundcolor(Color.BLACK);
		lyricView.setTextHeight(40);
		new Thread(new UIUpdateThread()).start();

	}

	class UIUpdateThread implements Runnable {
		long time = 100; // 滚动速度

		public void run() {

			while (mp.isPlaying()) {
				lyricView.updateIndex(mp.getCurrentPosition());
				mHandler.post(mUpdateResults);
				try {
					Thread.sleep(time);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	Handler mHandler = new Handler();
	Runnable mUpdateResults = new Runnable() {
		public void run() {
			lyricView.invalidate(); // 更新视图
		}
	};
}