package sjtu.se.Ubma;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Created by qwordy on 12/3/15.
 * LineChartView
 */

public class LineChartView extends View {

	private Paint paint;

	/**
	 * 0 for today, 1 for yesterday, ..., 6 for 6 days ago.
	 * -1 for average.
	 */
	public int whichDay = 0;

	public boolean timeValueVisible = false;

	private final int textColor = Color.GRAY;

	private final int chartColor = 0xff64b5f6;

	private final int backgroundColor = 0xfffafafa;

	public LineChartView(Context context) {
		super(context);
		init();
	}

	public LineChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		//Log.d("Meet", String.valueOf(getWidth()));
	}

	private void init() {
		paint = new Paint();
		paint.setTextSize(Utility.dp2px(14));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int height, width, i;

		Log.d("Meet", "draw");
		super.onDraw(canvas);
		canvas.drawColor(backgroundColor);
		height = getHeight();
		width = getWidth();
		//canvas.drawRect(0, 0, 200, 300, paint);

		// Draw axis
		paint.setColor(chartColor);
		float pad, x0, y0, x1, y1, dashLen;
		pad = width / 8;
		x0 = pad;
		y0 = pad / 3;
		x1 = width - pad / 3;
		y1 = height - pad;
		canvas.drawLine(x0, y0, x0, y1, paint);
		canvas.drawLine(x0, y1, x1, y1, paint);
		dashLen = pad / 10;
		for (i = 0; i <= 24; i++) {
			canvas.drawLine(
					x0 - dashLen,
					(float) (y0 + (y1 - y0) * (i / 24.0)),
					x0,
					(float) (y0 + (y1 - y0) * (i / 24.0)),
					paint);
		}
		for (i = 0; i <= 6; i++) {
			canvas.drawLine(
					(float) (x0 + (x1 - x0) * (i / 6.0)),
					y1,
					(float) (x0 + (x1 - x0) * (i / 6.0)),
					y1 + dashLen,
					paint);
		}

		// Text baseline bias
		Paint.FontMetrics fontMetrics = paint.getFontMetrics();
		float bias = -(fontMetrics.bottom + fontMetrics.top) / 2;

		// Draw text
		paint.setColor(textColor);
		paint.setTextAlign(Paint.Align.CENTER);
		for (i = 0; i <= 24; i++)
			canvas.drawText(
					String.valueOf(i),
					pad / 2,
					(float)(y0 + (y1 - y0) * (i / 24.0)) + bias,
					paint);
		canvas.drawText("h",
				pad / 2,
				(float)(y0 + (y1 - y0) * (25 / 24.0)) + bias,
				paint);
		canvas.drawText("60 min",
				(x0 + x1) / 2,
				height - pad / 2 + bias,
				paint);

		// Prepare data
		//double[] data = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		//		15, 21, 5, 2, 0, 0, 13, 15, 14, 0, 0, 0};
		double[] data = prepareData();

		// Draw line chart
		for (i = 0; i < 24; i++) {
			float xEnd = (float) (x0 + (x1 - x0) * (data[i] / 60));
			float yBegin = (float) (y0 + (y1 - y0) * (i / 24.0));
			float yEnd = (float) (y0 + (y1 - y0) * ((i + 1) / 24.0));
			paint.setColor(chartColor);
			canvas.drawRect(
					x0 + 1,
					yBegin + 1,
					xEnd,
					yEnd - 1,
					paint);
			// Draw value optionally
			if (timeValueVisible) {
				paint.setTextAlign(Paint.Align.LEFT);
				paint.setColor(textColor);
				canvas.drawText(String.format("%.2f", data[i]),
						xEnd + 5,
						(yBegin + yEnd) / 2 + bias,
						paint);
			}
		}
	}

	private double[] prepareData() {
		ActiveTimeData activeTimeData = Environment.getActiveTimeData();
		if (activeTimeData == null)
			activeTimeData = new ActiveTimeData();
		if (whichDay >= 0 && whichDay <= 6)
			return activeTimeData.dayActiveTime(whichDay);
		else
			return activeTimeData.averageActiveTime();
	}
}