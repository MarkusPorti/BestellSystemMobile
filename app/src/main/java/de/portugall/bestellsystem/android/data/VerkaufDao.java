package de.portugall.bestellsystem.android.data;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
abstract class VerkaufDao {

	@Transaction
	@Query("SELECT * FROM verkauf")
	public abstract LiveData<List<VerkaufWithPositionen>> getAll();

	@Transaction
	public void insert(VerkaufWithPositionen verkauf) {
		insertVerkauf(verkauf.verkauf);
		verkauf.positionen.forEach(verkaufPosition -> verkaufPosition.setVerkaufId(verkauf.verkauf.getId()));
		insertPositionen(verkauf.positionen);
	}

	@Insert
	abstract void insertVerkauf(Verkauf verkauf);

	@Insert
	abstract void insertPositionen(List<VerkaufPosition> positionen);

	@Transaction
	public void delete(VerkaufWithPositionen verkauf) {
		deletePositionen(verkauf.positionen);
		deleteVerkauf(verkauf.verkauf);
	}

	@Delete
	abstract void deleteVerkauf(Verkauf verkauf);

	@Delete
	abstract void deletePositionen(List<VerkaufPosition> positionen);

	@Transaction
	public void update(VerkaufWithPositionen verkauf) {
		updatePositionen(verkauf.positionen);
		updateVerkauf(verkauf.verkauf);
	}

	@Update
	abstract void updateVerkauf(Verkauf verkauf);

	@Update
	abstract void updatePositionen(List<VerkaufPosition> positionen);
}
