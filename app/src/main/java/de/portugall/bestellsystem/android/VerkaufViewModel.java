package de.portugall.bestellsystem.android;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import de.portugall.bestellsystem.android.data.AppDatabase;
import de.portugall.bestellsystem.android.data.VerkaufRepository;
import de.portugall.bestellsystem.android.data.VerkaufWithPositionen;

import java.util.List;

public class VerkaufViewModel extends AndroidViewModel {

	private final VerkaufRepository repo;
	private final LiveData<List<VerkaufWithPositionen>> allVerkaufList;

	public VerkaufViewModel(@NonNull Application application) {
		super(application);
		repo = new VerkaufRepository(application);
		allVerkaufList = repo.getAll();
	}

	public LiveData<List<VerkaufWithPositionen>> getAllVerkaufList() {
		return allVerkaufList;
	}

	public void insert(VerkaufWithPositionen verkauf) {
		repo.insert(verkauf);
	}

}
