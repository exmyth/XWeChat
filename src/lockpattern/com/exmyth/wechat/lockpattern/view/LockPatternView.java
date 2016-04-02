package com.exmyth.wechat.lockpattern.view;

import java.util.ArrayList;
import java.util.List;

import com.exmyth.wechat.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LockPatternView extends View {

	private static final int POINT_SIZE = 5;

	private Point[][] points = new Point[3][3];

	private Matrix matrix = new Matrix();

	private float width, height, offstartY, moveX, moveY;;

	private Bitmap bitmap_pressed, bitmap_normal, bitmap_error, bitmap_line,
			bitmap_line_error;

	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	private List<Point> pointList = new ArrayList<LockPatternView.Point>();

	private OnPatternChangeLister onPatternChangeLister;

	/**
	 * 构造函数
	 */
	public LockPatternView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public LockPatternView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LockPatternView(Context context) {
		super(context);
	}

	/*********************************************************
	 * 绘制9宫格
	 * movePoint代表鼠标在移动，但是不是9宫格里面的点
	 * isInit是否初始化过9个点
	 * isSelect 点位是否被选中状态
	 * isFinish 是否绘制完毕
	 */
	private boolean isInit, isSelect, isFinish, movePoint;

	@Override
	protected void onDraw(Canvas canvas) {
		// 第一次没有初始化就进行初始化，一旦初始化就不在初始化工作了，isInit的意思是---默认没有初始化过
		if (!isInit) {
			// 初始化9个点
			initPoints();
		}
		// 绘制9个点
		points2Canvas(canvas);

		if (pointList.size() > 0) {
			Point a = pointList.get(0);
			// 绘制九宫格坐标点
			for (int i = 0; i < pointList.size(); i++) {
				Point b = pointList.get(i);
				line2Canvas(canvas, a, b);
				a = b;
			}
			// 绘制鼠标坐标点
			if (movePoint) {
				line2Canvas(canvas, a, new Point(moveX, moveY));
			}
		}
	}

	/**
	 * 初始化9个点位 获取点位的3种状态 线的2种状态 以及9点的坐标位置 以及初始化密码操作 isInit=
	 * true设置状态--下次不必初始化话工作了
	 */
	private void initPoints() {

		// 获取布局宽高
		width = getWidth();
		height = getHeight();

		// 横屏和竖屏

		offstartY = (height - width) / 2;

		// 图片资源
		bitmap_normal = BitmapFactory.decodeResource(getResources(),
				R.drawable.btn_circle_normal);
		bitmap_pressed = BitmapFactory.decodeResource(getResources(),
				R.drawable.btn_circle_pressed);
		bitmap_error = BitmapFactory.decodeResource(getResources(),
				R.drawable.btn_circle_selected);
		bitmap_line = BitmapFactory.decodeResource(getResources(),
				R.drawable.ddd);
		bitmap_line_error = BitmapFactory.decodeResource(getResources(),
				R.drawable.qqq);

		points[0][0] = new Point(width / 4, offstartY + width / 4);
		points[0][1] = new Point(width / 2, offstartY + width / 4);
		points[0][2] = new Point(width / 4 * 3, offstartY + width / 4);

		points[1][0] = new Point(width / 4, offstartY + width / 4 * 2);
		points[1][1] = new Point(width / 2, offstartY + width / 4 * 2);
		points[1][2] = new Point(width / 4 * 3, offstartY + width / 4 * 2);

		points[2][0] = new Point(width / 4, offstartY + width / 4 * 3);
		points[2][1] = new Point(width / 2, offstartY + width / 4 * 3);
		points[2][2] = new Point(width / 4 * 3, offstartY + width / 4 * 3);

		// 设置密码1--9
		int index = 1;
		for (Point[] points : this.points) {
			for (Point point : points) {
				point.index = index;
				index++;
			}
		}
		// 初始化完成
		isInit = true;
	}

	/**
	 * 将9个点绘制到画布 循环遍历9个点位， 根据3种不同的状态绘制3种不同的9个点位
	 */
	private void points2Canvas(Canvas canvas) {
		// 循环遍历9个点位
		for (int i = 0; i < points.length; i++) {
			// 循环遍历每行的3个点位
			for (int j = 0; j < points[i].length; j++) {
				// 获取依次的某个点位
				Point point = points[i][j];
				if (point.state == Point.STATE_PRESSED) {
					// (Bitmap bitmap, float left, float top, Paint paint)
					canvas.drawBitmap(bitmap_pressed,
							point.x - bitmap_normal.getWidth() / 2, point.y
									- bitmap_normal.getHeight() / 2, paint);
				} else if (point.state == Point.STATE_ERROR) {
					canvas.drawBitmap(bitmap_error,
							point.x - bitmap_normal.getWidth() / 2, point.y
									- bitmap_normal.getHeight() / 2, paint);
				} else {
					canvas.drawBitmap(bitmap_normal,
							point.x - bitmap_normal.getWidth() / 2, point.y
									- bitmap_normal.getHeight() / 2, paint);
				}
			}
		}
	}

	/**
	 * 画线
	 */
	public void line2Canvas(Canvas canvas, Point a, Point b) {
		// 线的长度--2点之间的距离
		float linelength = (float) Point.distance(a, b);
		// 获取2点之间的角度
		float degress = getDegrees(a, b);
		//根据a点进行旋转
		canvas.rotate(degress, a.x, a.y);

		if (a.state == Point.STATE_PRESSED) {
			// xy方向上的缩放比例
			matrix.setScale(linelength / bitmap_line.getWidth(), 1);
			matrix.postTranslate(a.x - bitmap_line.getWidth() / 2, a.y
					- bitmap_line.getHeight() / 2);
			canvas.drawBitmap(bitmap_line, matrix, paint);
		} else {
			matrix.setScale(linelength / bitmap_line.getWidth(), 1);
			matrix.postTranslate(a.x - bitmap_line.getWidth() / 2, a.y
					- bitmap_line.getHeight() / 2);
			canvas.drawBitmap(bitmap_line_error, matrix, paint);
		}
		//画线完毕回归角度
		canvas.rotate(-degress, a.x, a.y);
	}

	// 获取角度
	public float getDegrees(Point pointA, Point pointB) {
		return (float) Math.toDegrees(Math.atan2(pointB.y - pointA.y, pointB.x
				- pointA.x));
	}

	/****************************************************************************
	 * onTouch事件处理
	 */

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		moveX = event.getX();
		moveY = event.getY();

		movePoint = false;
		isFinish = false;

		Point point = null;

		switch (event.getAction()) {
		//只要按下操作，就代表重新绘制界面
		case MotionEvent.ACTION_DOWN:
			if (onPatternChangeLister != null) {
				onPatternChangeLister.onPatterStart(true);
			}
			// 每次按下，都需要清空之前的集合
			resetPoint();
			// 检测是不是在九宫格内
			point = chechSelectPoint();
			if (point != null) {
				//如果按下的位置在9宫格内，就改成状态为true
				isSelect = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (isSelect) {
				// 检测是不是在九宫格内
				point = chechSelectPoint();
				if (point == null) {
					movePoint = true;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			//绘制完毕，点位状态改为未选中
			isFinish = true;
			isSelect = false;
			break;

		}
		// 如果没有绘制完毕，如果九宫格处于选中状态
		if (!isFinish && isSelect && point != null) {
			// 交叉点
			if (crossPoint(point)) {
				movePoint = true;
			} else {// 新点
				point.state = Point.STATE_PRESSED;
				pointList.add(point);
			}
		}

		// 绘制结束
		if (isFinish) {
			// 绘制不成立
			if (pointList.size() == 1) {
				// resetPoint();
				errorPoint();
			} else if (pointList.size() < POINT_SIZE && pointList.size() > 0) {// 绘制错误
				errorPoint();
				if (onPatternChangeLister != null) {
					onPatternChangeLister.onPatternChange(null);
				}
			} else {
				if (onPatternChangeLister != null) {
					String pass = "";
					for (int i = 0; i < pointList.size(); i++) {
						pass = pass + pointList.get(i).index;
					}
					if (!TextUtils.isEmpty(pass)) {
						onPatternChangeLister.onPatternChange(pass);
					}
				}
			}
		}

		postInvalidate();
		return true;
	}

	/**
	 * 重新绘制
	 */
	public void resetPoint() {
		for (int i = 0; i < pointList.size(); i++) {
			Point point = pointList.get(i);
			point.state = Point.STATE_NORMAL;
		}
		pointList.clear();
	}

	/**
	 * 检查是否选中
	 */
	private Point chechSelectPoint() {
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points[i].length; j++) {
				Point point = points[i][j];
				if (Point.with(point.x, point.y, bitmap_normal.getWidth() / 2,
						moveX, moveY)) {
					return point;
				}
			}
		}

		return null;
	}

	/**
	 * 交叉点
	 */
	private boolean crossPoint(Point point) {
		if (pointList.contains(point)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 绘制错误
	 */
	public void errorPoint() {
		for (Point point : pointList) {
			point.state = Point.STATE_ERROR;
		}
	}

	/***********************************************************************
	 * 自定义的点
	 */
	public static class Point {
		// 正常
		public static int STATE_NORMAL = 0;
		// 选中
		public static int STATE_PRESSED = 1;
		// 错误
		public static int STATE_ERROR = 2;
		public float x, y;
		public int index = 0, state = 0;

		public Point() {
		};

		public Point(float x, float y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * 两点之间的距离
		 */
		public static double distance(Point a, Point b) {
			return Math.sqrt(Math.abs(a.x - b.x) * Math.abs(a.x - b.x)
					+ Math.abs(a.y - b.y) * Math.abs(a.y - b.y));
		}

		/**
		 */
		public static boolean with(float paintX, float pointY, float r,
				float moveX, float moveY) {
			return Math.sqrt((paintX - moveX) * (paintX - moveX)
					+ (pointY - moveY) * (pointY - moveY)) < r;
		}
	}

	/**
	 * 图案监听器
	 */
	public static interface OnPatternChangeLister {
		void onPatternChange(String passwordStr);

		void onPatterStart(boolean isStart);
	}

	/**
	 * 设置图案监听器
	 */
	public void setOnPatternChangeLister(OnPatternChangeLister changeLister) {
		if (changeLister != null) {
			this.onPatternChangeLister = changeLister;
		}
	}
}
