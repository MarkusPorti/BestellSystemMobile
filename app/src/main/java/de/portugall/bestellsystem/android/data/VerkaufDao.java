package de.portugall.bestellsystem.android.data;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import io.reactivex.Single;

import java.util.List;

@Dao
public abstract class VerkaufDao {

	@Transaction
	@Query("SELECT * FROM verkauf")
	public abstract LiveData<List<VerkaufWithPositionen>> getAll();

	@Query("SELECT * FROM verkauf WHERE id = :id")
	public abstract Single<Verkauf> findById(int id);

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

}
