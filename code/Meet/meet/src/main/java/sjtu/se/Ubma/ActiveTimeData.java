package sjtu.se.Ubma;

import android.util.Log;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by qwordy on 12/6/15.
 * ActiveTimeData
 */

public class ActiveTimeData implements Serializable {
	private int size;	// actual size
	private int sampleSize;
	private int tail;	// tail position
	private int tailDay;
	private final int MAX = 30;
	private double[][] times = new double[MAX][24];	// in ms

	public ActiveTimeData() {
		size = 1;
		tail = 0;
	}

	public void setSampleSize(int sampleSize) {
		this.sampleSize = sampleSize;
	}

	public void addTime(Calendar begin, Calendar end) {
		int i, oldTail, day0, day1, hour0, hour1;
		final int MS_PER_HOUR = 60 * 60 * 1000;

		day0 = begin.get(Calendar.DAY_OF_MONTH);
		day1 = end.get(Calendar.DAY_OF_MONTH);
		hour0 = begin.get(Calendar.HOUR_OF_DAY);
		hour1 = end.get(Calendar.HOUR_OF_DAY);

		// Assume that day1 - day0 <= 1
		oldTail = tail;
		if (day1 != tailDay) {
			tailDay = day1;
			tail++;
			if (tail == MAX) tail = 0;
			Arrays.fill(times[tail], 0);
		}
		if (day0 == day1) {
			if (hour0 == hour1) {
				times[tail][hour0] += end.getTimeInMillis() - begin.getTimeInMillis();
			} else {
				times[tail][hour0] += MS_PER_HOUR - msInHour(begin);

				for (i = hour0 + 1; i < hour1; i++)
					times[tail][i] = MS_PER_HOUR;

				times[tail][hour1] += msInHour(end);
			}
		} else {
			times[oldTail][hour0] += MS_PER_HOUR - msInHour(begin);

			for (i = hour0 + 1; i < 24; i++)
				times[oldTail][i] = MS_PER_HOUR;
			for (i = 0; i < hour1; i++)
				times[tail][i] = MS_PER_HOUR;

			times[tail][hour1] += msInHour(end);
		}
	}

	private int msInHour(Calendar calendar) {
		return calendar.get(Calendar.MINUTE) * 60000 +
				calendar.get(Calendar.SECOND) * 1000 +
				calendar.get(Calendar.MILLISECOND);
	}

	// Return average active time in minite
	public double[] averageActiveTime() {
		int i, j, n, count, hour;
		double averageTimes[] = new double[24];

		n = Math.min(size, sampleSize);
		count = 0;
		i = tail;
		while (count < n) {
			for (j = 0; j < 24; j++)
				averageTimes[j] += times[i][j];
			count++;
			i--;
			if (i < 0) i = MAX - 1;
		}
		Calendar calendar = Calendar.getInstance();
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		for (i = 0; i < hour; i++)
			averageTimes[i] = averageTimes[i] / n / 60000;
		for (i = hour; i < 24; i++)
			averageTimes[i] = averageTimes[i] / (n - 1) / 60000;

		Log.d("Meet", Arrays.toString(averageTimes));
		return averageTimes;
	}

}