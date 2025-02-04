package de.portugall.bestellsystem.android.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Verkauf.class, VerkaufPosition.class}, version = 1, exportSchema = false)
@TypeConverters({LocalTimeConverter.class})
abstract class AppDatabase extends RoomDatabase {

	private static AppDatabase INSTANCE;
	static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);

	public static AppDatabase getInstance(final Context context) {
		if (null == INSTANCE) {
			synchronized (AppDatabase.class) {
				INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "database")
							   .createFromAsset("database")
							   .build();
			}
		}
		return INSTANCE;
	}

	public abstract VerkaufDao verkaufDao();

}
