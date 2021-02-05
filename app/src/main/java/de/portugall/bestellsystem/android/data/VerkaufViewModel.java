package de.portugall.bestellsystem.android.data;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class VerkaufViewModel extends AndroidViewModel {

	private final VerkaufDao dao;
	private final LiveData<List<VerkaufWithPositionen>> allVerkaufList;

	public VerkaufViewModel(@NonNull Application application) {
		super(application);
		AppDatabase db = AppDatabase.getInstance(application);
		dao = db.verkaufDao();
		allVerkaufList = dao.getAll();
	}

	public LiveData<List<VerkaufWithPositionen>> getAllVerkaufList() {
		return allVerkaufList;
	}

	public void insert(VerkaufWithPositionen verkauf) {
		AppDatabase.databaseExecutor.execute(() -> dao.insert(verkauf));
	}

}
