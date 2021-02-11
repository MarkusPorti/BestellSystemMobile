package de.portugall.bestellsystem.android.data;

import androidx.room.TypeConverter;

import java.time.LocalTime;

class LocalTimeConverter {

	@TypeConverter
	public static LocalTime toTime(String timeString) {
		if (timeString == null) {
			return null;
		} else {
			return LocalTime.parse(timeString);
		}
	}

	@TypeConverter
	public static String toTimeString(LocalTime time) {
		if (time == null) {
			return null;
		} else {
			return time.toString();
		}
	}

}
