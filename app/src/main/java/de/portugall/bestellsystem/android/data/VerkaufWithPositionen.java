package de.portugall.bestellsystem.android.data;

import android.util.Log;
import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@JsonAutoDetect(
		fieldVisibility = JsonAutoDetect.Visibility.NONE,
		setterVisibility = JsonAutoDetect.Visibility.NONE,
		getterVisibility = JsonAutoDetect.Visibility.NONE,
		isGetterVisibility = JsonAutoDetect.Visibility.NONE,
		creatorVisibility = JsonAutoDetect.Visibility.NONE
)
public class VerkaufWithPositionen {
	@Embedded
	public Verkauf verkauf = new Verkauf();

	@Relation(entity = VerkaufPosition.class,
			  parentColumn = "id",
			  entityColumn = "verkaufId")
	public List<VerkaufPosition> positionen;

	@JsonSetter("id")
	public void setId(long id) {
		this.verkauf.setId(id);
	}

	@JsonSetter("uhrzeit")
	public void setUhrzeit(String uhrzeit) {
		this.verkauf.setUhrzeit(LocalTime.parse(uhrzeit));
	}

	@JsonSetter("positionen")
	public void setPositionen(List<VerkaufPosition> positionen) {
		this.positionen = positionen;
		this.positionen.forEach(position -> position.setVerkaufId(this.verkauf.getId()));
	}

	@Ignore
	public static VerkaufWithPositionen fromJson(String json) {
		try {
			ObjectMapper om = new ObjectMapper();
			return om.readValue(json, VerkaufWithPositionen.class);
		} catch (JsonProcessingException e) {
			Log.e("VerkaufWithPositionen", "Fehler beim Konvertieren der Message in ein Verkauf-Objekt: ", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		VerkaufWithPositionen that = (VerkaufWithPositionen) o;
		return Objects.equals(verkauf, that.verkauf) && Objects.equals(positionen, that.positionen);
	}

	@Override
	public int hashCode() {
		return Objects.hash(verkauf, positionen);
	}
}
