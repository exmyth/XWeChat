package com.exmyth.wechat.handler;

import com.exmyth.wechat.handler.LooperThread.Message;

public class Client {
	public static void main(String[] args) {
		LooperThread looper = new LooperThread();
		looper.start();
		for (int i = 0; i < 10; i++) {
			Message message = new LooperThread.Message();
			message.what = i;
			message.data = "data"+i;
			looper.sendMessage(message);
		}
	}
}
