package de.portugall.bestellsystem.android;

import de.portugall.bestellsystem.android.data.Verkauf;
import de.portugall.bestellsystem.android.data.VerkaufPosition;
import de.portugall.bestellsystem.android.data.VerkaufWithPositionen;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Json local unit test, which will execute on the development machine (host).
 */
public class JsonUnitTest {

	/**
	 * Testet das Erstellen eines Verkaufs aus einem JSON-String.
	 * Testet die Equals-Methoden von Verkauf und Verkaufposition.
	 */
	@Test
	public void verkaufFromJson() {
		String json = "{\"id\":116,\"uhrzeit\":\"10:40:28\",\"positionen\":"
				+ "[{\"id\":187,\"menge\":2,\"artikel\":\"Erbsensuppe\"},"
				+ "{\"id\":188,\"menge\":1,\"artikel\":\"Brötchen\"},"
				+ "{\"id\":189,\"menge\":2,\"artikel\":\"Siedewurst\"}]}";

		VerkaufWithPositionen verkaufFromJson = VerkaufWithPositionen.fromJson(json);
		assertNotNull(verkaufFromJson);
		assertEquals(116, verkaufFromJson.verkauf.getId());
		assertEquals(LocalTime.of(10, 40, 28), verkaufFromJson.verkauf.getUhrzeit());
		assertEquals(187, verkaufFromJson.positionen.get(0).getId());
		assertEquals(116, verkaufFromJson.positionen.get(0).getVerkaufId());
		assertEquals(2, verkaufFromJson.positionen.get(0).getMenge());
		assertEquals("Erbsensuppe", verkaufFromJson.positionen.get(0).getArtikel());
		assertEquals(188, verkaufFromJson.positionen.get(1).getId());
		assertEquals(116, verkaufFromJson.positionen.get(1).getVerkaufId());
		assertEquals(1, verkaufFromJson.positionen.get(1).getMenge());
		assertEquals("Brötchen", verkaufFromJson.positionen.get(1).getArtikel());
		assertEquals(189, verkaufFromJson.positionen.get(2).getId());
		assertEquals(116, verkaufFromJson.positionen.get(2).getVerkaufId());
		assertEquals(2, verkaufFromJson.positionen.get(2).getMenge());
		assertEquals("Siedewurst", verkaufFromJson.positionen.get(2).getArtikel());

		Verkauf verkauf = new Verkauf();
		verkauf.setId(116);
		verkauf.setUhrzeit(LocalTime.of(10, 40, 28));
		VerkaufPosition pos1 = new VerkaufPosition();
		pos1.setId(187);
		pos1.setVerkaufId(116);
		pos1.setMenge(2);
		pos1.setArtikel("Erbsensuppe");
		VerkaufPosition pos2 = new VerkaufPosition();
		pos2.setId(188);
		pos2.setVerkaufId(116);
		pos2.setMenge(1);
		pos2.setArtikel("Brötchen");
		VerkaufPosition pos3 = new VerkaufPosition();
		pos3.setId(189);
		pos3.setVerkaufId(116);
		pos3.setMenge(2);
		pos3.setArtikel("Siedewurst");
		VerkaufWithPositionen verkaufWithPositionen = new VerkaufWithPositionen();
		verkaufWithPositionen.verkauf = verkauf;
		verkaufWithPositionen.positionen = Arrays.asList(pos1, pos2, pos3);

		assertEquals(verkaufWithPositionen, verkaufFromJson);
	}
}