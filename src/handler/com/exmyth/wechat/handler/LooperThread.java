package com.exmyth.wechat.handler;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LooperThread {

	private volatile boolean mIsLooperQuit = false;

	private Thread mThread;

	public void start() {
		if (mThread != null) {
			return;
		}
		mIsLooperQuit = false;
		mThread = new Thread(mLooperRunnable);
		mThread.start();
	}

	public void stop() {
		if (mThread == null) {
			return;
		}

		mIsLooperQuit = true;

		mLock.lock();
		mCondition.signal();
		mLock.unlock();

		mMessageQueue.clear();
		mThread = null;
	}

	protected Runnable mLooperRunnable = new Runnable() {

		@Override
		public void run() {
			while (!mIsLooperQuit) {
				mLock.lock();
				Message message = null;

				try {
					while (!mIsLooperQuit && mMessageQueue.isEmpty()) {
						mCondition.await(); // 没有消息到来则休眠
					}
					message = mMessageQueue.poll();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					mLock.unlock();
				}

				handleMessage(message);
			}
		}
	};

	private Queue<Message> mMessageQueue = new LinkedList<Message>();

	public static class Message {
		int what;
		String data;
	}

	private Lock mLock = new ReentrantLock();
	private Condition mCondition = mLock.newCondition();

	// 发送消息，由外部其他模块调用，发送消息给线程
	public void sendMessage(Message message) {
		if (mThread == null) {
			return;
		}
		mLock.lock();
		mMessageQueue.add(message); // 添加消息到消息队列
		mCondition.signal(); // 通知线程循环，有消息来了，请立即处理
		mLock.unlock();
	}

	// 处理消息，由线程内部调用
	public void handleMessage(Message message) {
		// 这里可以通过一个Callback来回调监听者
		System.out.println(message.data);
	}
}