/*
 * @Title DBHelper.java
 * @Copyright Copyright 2010-2015 Yann Software Co,.Ltd All Rights Reserved.
 * @Description��
 * @author Yann
 * @date 2015-8-8 ����10:48:31
 * @version 1.0
 */
package com.exmyth.wechat.download.db;

import android.R.integer;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/** 
 * ��ݿ������
 * @author Yann
 * @date 2015-8-8 ����10:48:31
 */
public class DBHelper extends SQLiteOpenHelper
{
	private static final String DB_NAME = "download.db";
	private static final int VERSION = 1;
	private static final String SQL_CREATE = "create table thread_info(_id integer primary key autoincrement," +
			"thread_id integer, url text, start integer, end integer, finished integer)";
	private static final String SQL_DROP = "drop table if exists thread_info";
	
	/** 
	 *@param context
	 *@param name
	 *@param factory
	 *@param version
	 */
	public DBHelper(Context context)
	{
		super(context, DB_NAME, null, VERSION);
	}

	/**
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(SQL_CREATE);
	}

	/**
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL(SQL_DROP);
		db.execSQL(SQL_CREATE);
	}

}
