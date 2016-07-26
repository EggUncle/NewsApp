package uncle.egg.newsapp.module;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import uncle.egg.newsapp.R;


public class ChangeColorIconWithText extends View
{

	private int color = 0xFF45C01A;
	private Bitmap iconBitmap;
	private String text = "text";
	private int textSize = (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());

	private Canvas canvas;
	private Bitmap bitmap;
	private Paint paint;

	private float alpha;

	private Rect iconRect;
	private Rect textBound;
	private Paint textPaint;

	public ChangeColorIconWithText(Context context)
	{
		this(context, null);
	}

	public ChangeColorIconWithText(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	/**
	 * 获取自定义属性的值
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public ChangeColorIconWithText(Context context, AttributeSet attrs,
								   int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.ChangeColorIconWithText);

		int n = a.getIndexCount();

		for (int i = 0; i < n; i++)
		{
			int attr = a.getIndex(i);
			switch (attr)
			{
			case R.styleable.ChangeColorIconWithText_my_icon:
				BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(attr);
				iconBitmap = drawable.getBitmap();
				break;
			case R.styleable.ChangeColorIconWithText_my_color:
				color = a.getColor(attr, 0xFF45C01A);
				break;
			case R.styleable.ChangeColorIconWithText_text:
				text = a.getString(attr);
				break;
			case R.styleable.ChangeColorIconWithText_text_size:
				textSize = (int) a.getDimension(attr, TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
								getResources().getDisplayMetrics()));
				break;
			}

		}

		a.recycle();

		textBound = new Rect();
		textPaint = new Paint();
		textPaint.setTextSize(textSize);
		textPaint.setColor(0Xff555555);
		textPaint.getTextBounds(text, 0, text.length(), textBound);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft()
				- getPaddingRight(), getMeasuredHeight() - getPaddingTop()
				- getPaddingBottom() - textBound.height());

		int left = getMeasuredWidth() / 2 - iconWidth / 2;
		int top = getMeasuredHeight() / 2 - (textBound.height() + iconWidth)
				/ 2;
		iconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		canvas.drawBitmap(iconBitmap, null, iconRect, null);

		int cAlpha = (int) Math.ceil(255 * alpha);

		// 内存去准备mBitmap , setAlpha , 纯色 ，xfermode ， 图标
		setupTargetBitmap(cAlpha);
		// 1、绘制原文本 ； 2、绘制变色的文本
		drawSourceText(canvas, cAlpha);
		drawTargetText(canvas, cAlpha);
		
		canvas.drawBitmap(bitmap, 0, 0, null);

	}

	/**
	 * 绘制变色的文本
	 * 
	 * @param canvas
	 * @param alpha
	 */
	private void drawTargetText(Canvas canvas, int alpha)
	{
		textPaint.setColor(color);
		textPaint.setAlpha(alpha);
		int x = getMeasuredWidth() / 2 - textBound.width() / 2;
		int y = iconRect.bottom + textBound.height();
		canvas.drawText(text, x, y, textPaint);

	}

	/**
	 * 绘制原文本
	 * 
	 * @param canvas
	 * @param alpha
	 */
	private void drawSourceText(Canvas canvas, int alpha)
	{
		textPaint.setColor(0xff333333);
		textPaint.setAlpha(255 - alpha);
		int x = getMeasuredWidth() / 2 - textBound.width() / 2;
		int y = iconRect.bottom + textBound.height();
		canvas.drawText(text, x, y, textPaint);

	}

	/**
	 * 在内存中绘制可变色的Icon
	 */
	private void setupTargetBitmap(int alpha)
	{
		bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
				Config.ARGB_8888);
		canvas = new Canvas(bitmap);
		paint = new Paint();
		paint.setColor(color);
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setAlpha(alpha);
		canvas.drawRect(iconRect, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		paint.setAlpha(255);
		canvas.drawBitmap(iconBitmap, null, iconRect, paint);
	}

	private static final String INSTANCE_STATUS = "instance_status";
	private static final String STATUS_ALPHA = "status_alpha";

	@Override
	protected Parcelable onSaveInstanceState()
	{
		Bundle bundle = new Bundle();
		bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
		bundle.putFloat(STATUS_ALPHA, alpha);
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state)
	{
		if (state instanceof Bundle)
		{
			Bundle bundle = (Bundle) state;
			alpha = bundle.getFloat(STATUS_ALPHA);
			super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
			return;
		}
		super.onRestoreInstanceState(state);
	}

	public void setIconAlpha(float alpha)
	{
		this.alpha = alpha;
		invalidateView();
	}

	/**
	 * 重绘
	 */
	private void invalidateView()
	{
		if (Looper.getMainLooper() == Looper.myLooper())
		{
			invalidate();
		} else
		{
			postInvalidate();
		}
	}

}
