package de.portugall.bestellsystem.android.data;

import android.content.Context;
import androidx.lifecycle.LiveData;

import java.util.List;

public class VerkaufRepository {

	private final VerkaufDao verkaufDao;

	public VerkaufRepository(Context context) {
		verkaufDao = AppDatabase.getInstance(context).verkaufDao();
	}

	public LiveData<List<VerkaufWithPositionen>> getAll() {
		return verkaufDao.getAll();
	}

	public void insert(VerkaufWithPositionen verkauf) {
		AppDatabase.databaseExecutor.execute(() -> {
			verkaufDao.insert(verkauf);
		});
	}

	public void delete(VerkaufWithPositionen verkauf) {
		AppDatabase.databaseExecutor.execute(() -> {
			verkaufDao.delete(verkauf);
		});
	}

	public void update(VerkaufWithPositionen verkauf) {
		AppDatabase.databaseExecutor.execute(() -> {
			verkaufDao.update(verkauf);
		});
	}
}
