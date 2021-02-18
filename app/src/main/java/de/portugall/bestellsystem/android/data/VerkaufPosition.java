package de.portugall.bestellsystem.android.data;

import androidx.room.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Objects;

@JsonAutoDetect(
		fieldVisibility = JsonAutoDetect.Visibility.NONE,
		setterVisibility = JsonAutoDetect.Visibility.NONE,
		getterVisibility = JsonAutoDetect.Visibility.NONE,
		isGetterVisibility = JsonAutoDetect.Visibility.NONE,
		creatorVisibility = JsonAutoDetect.Visibility.NONE
)
@Entity(foreignKeys = {
		@ForeignKey(entity = Verkauf.class, parentColumns = "id", childColumns = "verkaufId")
}, indices = {
		@Index("id"),
		@Index("verkaufId")
})
public class VerkaufPosition {

	@PrimaryKey
	private long id;

	@ColumnInfo
	private long verkaufId;

	@ColumnInfo
	private int menge;

	@ColumnInfo
	private String artikel;

	@Ignore
	private boolean show = false;

	public long getId() {
		return id;
	}

	@JsonSetter("id")
	public void setId(long id) {
		this.id = id;
	}

	public int getMenge() {
		return menge;
	}

	@JsonSetter("menge")
	public void setMenge(int menge) {
		this.menge = menge;
	}

	public String getArtikel() {
		return artikel;
	}

	@JsonSetter("artikel")
	public void setArtikel(String artikel) {
		this.artikel = artikel;
	}

	public long getVerkaufId() {
		return verkaufId;
	}

	public void setVerkaufId(long verkaufId) {
		this.verkaufId = verkaufId;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		VerkaufPosition that = (VerkaufPosition) o;
		return id == that.id && verkaufId == that.verkaufId && menge == that.menge && artikel.equals(that.artikel);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, verkaufId, menge, artikel);
	}
}
