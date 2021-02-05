package de.portugall.bestellsystem.android;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import de.portugall.bestellsystem.android.data.VerkaufPosition;
import de.portugall.bestellsystem.android.data.VerkaufWithPositionen;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class VerkaufCardListAdapter extends ListAdapter<VerkaufWithPositionen, VerkaufCardListAdapter.VerkaufViewHolder> {

	protected VerkaufCardListAdapter() {
		super(new DiffUtil.ItemCallback<VerkaufWithPositionen>() {
			@Override
			public boolean areItemsTheSame(@NonNull VerkaufWithPositionen oldItem, @NonNull VerkaufWithPositionen newItem) {
				return oldItem.verkauf.getId() == newItem.verkauf.getId();
			}

			@Override
			public boolean areContentsTheSame(@NonNull VerkaufWithPositionen oldItem, @NonNull VerkaufWithPositionen newItem) {
				return oldItem.equals(newItem);
			}
		});
	}

	@NonNull
	@Override
	public VerkaufCardListAdapter.VerkaufViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return VerkaufViewHolder.create(parent);
	}

	@Override
	public void onBindViewHolder(@NonNull VerkaufCardListAdapter.VerkaufViewHolder holder, int position) {
		holder.bindTo(getCurrentList().get(position));
	}

	public static class VerkaufViewHolder extends RecyclerView.ViewHolder {
		private final TextView textUhrzeit;
		private final TextView textVergangeneZeit;
		private final LinearLayout listLayoutArtikel;
		private final MaterialButton buttonFertig;
		private final MaterialButton buttonAbholbereit;
		private VerkaufWithPositionen boundVerkauf;

		public VerkaufViewHolder(@NonNull View view) {
			super(view);
			textUhrzeit = view.findViewById(R.id.textUhrzeit);
			textVergangeneZeit = view.findViewById(R.id.textVergangeneZeit);
			listLayoutArtikel = view.findViewById(R.id.listLayoutArtikel);
			buttonFertig = view.findViewById(R.id.buttonFertig);
			buttonAbholbereit = view.findViewById(R.id.buttonAbholbereit);
			buttonAbholbereit.setOnClickListener(view1 -> {
				if (buttonAbholbereit.isChecked()) {
					buttonAbholbereit.setIcon(view.getResources()
												  .getDrawable(R.drawable.ic_baseline_check_24, view.getContext()
																									.getTheme()));
				} else {
					buttonAbholbereit.setIcon(null);
				}
			});
		}

		static VerkaufViewHolder create(@NonNull ViewGroup parent) {
			View view = LayoutInflater.from(parent.getContext())
									  .inflate(R.layout.verkauf_card, parent, false);
			return new VerkaufViewHolder(view);
		}

		public void bindTo(VerkaufWithPositionen verkaufWithPositionen) {
			this.boundVerkauf = verkaufWithPositionen;
			textUhrzeit.setText(verkaufWithPositionen.verkauf.getUhrzeit().toString());
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					Duration duration = Duration.between(verkaufWithPositionen.verkauf.getUhrzeit(), LocalTime.now());
					int minutes = (int) duration.toMinutes();
					String verbleibendeZeit = itemView.getResources()
													  .getQuantityString(R.plurals.vergangene_zeit, minutes, minutes);
					new Handler(Looper.getMainLooper()).post(() -> {
						textVergangeneZeit.setText(verbleibendeZeit);
					});
				}
			}, 0, 10000);
			for (VerkaufPosition verkaufPosition : verkaufWithPositionen.positionen) {
				ArtikelItemView positionView = new ArtikelItemView(itemView.getContext(), verkaufPosition);
				listLayoutArtikel.addView(positionView);
			}
		}
	}

}
