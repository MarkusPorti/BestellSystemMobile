package de.portugall.bestellsystem.android.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalTime;
import java.util.Objects;

@Entity
public class Verkauf {

	@PrimaryKey
	private long id;

	@ColumnInfo
	private LocalTime uhrzeit;

	@ColumnInfo
	private boolean abholbereit;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalTime getUhrzeit() {
		return uhrzeit;
	}

	public void setUhrzeit(LocalTime uhrzeit) {
		this.uhrzeit = uhrzeit;
	}

	public boolean isAbholbereit() {
		return abholbereit;
	}

	public void setAbholbereit(boolean abholbereit) {
		this.abholbereit = abholbereit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Verkauf verkauf = (Verkauf) o;
		return id == verkauf.id && uhrzeit.equals(verkauf.uhrzeit) && abholbereit == verkauf.abholbereit;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, uhrzeit, abholbereit);
	}
}
