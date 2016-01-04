package sjtu.se.Ubma;

import android.util.Log;

import java.io.*;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by qwordy on 12/6/15.
 * ActiveTimeData
 */

public class ActiveTimeData {

	private int tail;	// tail position

	private int tailDay;

	private int[][] times = new int[7][24];	// in ms

	private double[] ansTimes = new double[24];

	/**
	 * Construct an instance from storage or construct a new instance.
	 */
	public ActiveTimeData() {
		File file = Environment.activeTimeFile;
		if (file.exists()) {
			try {
				DataInputStream input = new DataInputStream(
						new BufferedInputStream(new FileInputStream(file)));
				tail = input.readInt();
				tailDay = input.readInt();
				for (int i = 0; i < 7; i++)
					for (int j = 0; j < 24; j++)
						times[i][j] = input.readInt();
				input.close();
			} catch (Exception e) {
				Log.d("Meet", "new ActiveTimeData fail");
				e.printStackTrace();
			}
		} else {
			tailDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
			writeInstance();
		}
	}

	/**
	 * Write the instance to storage.
	 * @return 0 if sucess.
	 */
	public int writeInstance() {
		try {
			File file = Environment.activeTimeFile;
			DataOutputStream output = new DataOutputStream(
					new BufferedOutputStream(new FileOutputStream(file)));
			output.writeInt(tail);
			output.writeInt(tailDay);
			for (int i = 0; i < 7; i++)
				for (int j = 0; j < 24; j++)
					output.writeInt(times[i][j]);
			output.close();
			return 0;
		} catch (Exception e) {
			Log.d("Meet", "writeInstance fail");
			e.printStackTrace();
			return 1;
		}
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
			if (tail == 7) tail = 0;
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

	/**
	 * @return Average active time in minite
	 */
	public double[] averageActiveTime() {
		int i, j, hour;

		Arrays.fill(ansTimes, 0);

		for (i = 0; i < 7; i++)
			for (j = 0; j < 24; j++)
				ansTimes[j] += times[i][j];

		Calendar calendar = Calendar.getInstance();
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		for (i = 0; i <= hour; i++)
			ansTimes[i] = ansTimes[i] / 7.0 / 60000;
		for (i = hour + 1; i < 24; i++)
			ansTimes[i] = ansTimes[i] / 6.0 / 60000;

		Log.d("Meet", Arrays.toString(ansTimes));
		return ansTimes;
	}

	/**
	 * @param dayBefore Number of days before today (0 <= day <= 6).
	 * @return Active time on the day
	 */
	public double[] dayActiveTime(int dayBefore) {
		int i, p;
		p = tail - dayBefore;
		if (p < 0) p += 7;
		for (i = 0; i < 24; i++)
			ansTimes[i] = times[p][i] / 60000.0;
		return ansTimes;
	}
}