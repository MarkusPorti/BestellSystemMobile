package de.portugall.bestellsystem.android;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import de.portugall.bestellsystem.android.data.VerkaufPosition;
import de.portugall.bestellsystem.android.data.VerkaufRepository;
import de.portugall.bestellsystem.android.data.VerkaufWithPositionen;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class VerkaufCardListAdapter extends ListAdapter<VerkaufWithPositionen, VerkaufCardListAdapter.VerkaufViewHolder> {

	private final Consumer<VerkaufWithPositionen> onDeleteCallback;
	private final VerkaufRepository repo;

	protected VerkaufCardListAdapter(Consumer<VerkaufWithPositionen> onDeleteCallback) {
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
		this.onDeleteCallback = onDeleteCallback;
		repo = new VerkaufRepository(null);
	}

	@NonNull
	@Override
	public VerkaufCardListAdapter.VerkaufViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new VerkaufViewHolder(parent);
	}

	@Override
	public void onBindViewHolder(@NonNull VerkaufCardListAdapter.VerkaufViewHolder holder, int position) {
		holder.bindTo(getCurrentList().get(position));
	}

	public class VerkaufViewHolder extends RecyclerView.ViewHolder {
		private final SharedPreferences preferences;
		private final TextView textVergangeneZeit;
		private final LinearLayout listLayoutArtikel;
		private final MaterialButton buttonFertig;
		private final MaterialButton buttonAbholbereit;
		private final Drawable drawable;
		private VerkaufWithPositionen boundVerkauf;

		public VerkaufViewHolder(@NonNull ViewGroup parent) {
			super(LayoutInflater.from(parent.getContext()).inflate(R.layout.verkauf_card, parent, false));
			preferences = PreferenceManager.getDefaultSharedPreferences(parent.getContext());

			textVergangeneZeit = itemView.findViewById(R.id.textVergangeneZeit);
			listLayoutArtikel = itemView.findViewById(R.id.listLayoutArtikel);
			buttonFertig = itemView.findViewById(R.id.buttonFertig);
			buttonFertig.setOnClickListener(eventView -> VerkaufCardListAdapter.this.onDeleteCallback.accept(boundVerkauf));
			buttonAbholbereit = itemView.findViewById(R.id.buttonAbholbereit);
			drawable = itemView.getResources()
							   .getDrawable(R.drawable.ic_baseline_check_24, itemView.getContext().getTheme());
			buttonAbholbereit.setOnClickListener(eventView -> {
				buttonAbholbereit.setIcon(buttonAbholbereit.isChecked() ? drawable : null);
				boundVerkauf.verkauf.setAbholbereit(buttonAbholbereit.isChecked());
				repo.update(boundVerkauf);
			});
		}

		public void bindTo(VerkaufWithPositionen verkaufWithPositionen) {
			this.boundVerkauf = verkaufWithPositionen;
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

			listLayoutArtikel.removeAllViews();
			for (VerkaufPosition verkaufPosition : verkaufWithPositionen.positionen) {
				if (verkaufPosition.isShow()) {
					ArtikelItemView positionView = new ArtikelItemView(itemView.getContext(), verkaufPosition);
					listLayoutArtikel.addView(positionView);
				}
			}

			buttonAbholbereit.setChecked(verkaufWithPositionen.verkauf.isAbholbereit());
			buttonAbholbereit.setIcon(buttonAbholbereit.isChecked() ? drawable : null);
		}
	}

}
