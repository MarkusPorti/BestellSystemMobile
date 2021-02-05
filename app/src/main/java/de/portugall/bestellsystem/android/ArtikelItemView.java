package de.portugall.bestellsystem.android;

import android.content.Context;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import de.portugall.bestellsystem.android.data.VerkaufPosition;

public class ArtikelItemView extends ConstraintLayout {

	private final TextView textMenge;
	private final TextView textArtikel;
	private VerkaufPosition verkaufPosition;

	public ArtikelItemView(Context context, VerkaufPosition position) {
		super(context);
		inflate(context, R.layout.artikel_item, this);

		this.verkaufPosition = position;
		textMenge = findViewById(R.id.textMenge);
		textArtikel = findViewById(R.id.textArtikel);

		textMenge.setText(getResources().getString(R.string.artikel_menge, position.getMenge()));
		textArtikel.setText(position.getArtikel());
	}

}
