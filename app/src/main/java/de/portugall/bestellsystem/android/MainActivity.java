package de.portugall.bestellsystem.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import de.portugall.bestellsystem.android.data.VerkaufRepository;
import de.portugall.bestellsystem.android.data.VerkaufWithPositionen;
import de.portugall.bestellsystem.android.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";
	private Snackbar snackbarVerkaufEntfernt;
	private VerkaufWithPositionen lastDeletedVerkauf;
	private VerkaufRepository verkaufRepo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		verkaufRepo = new VerkaufRepository(this);

		CoordinatorLayout rootLayout = findViewById(R.id.main_coordinator_layout);
		snackbarVerkaufEntfernt = Snackbar.make(rootLayout, R.string.hinweis_verkauf_entfernt, BaseTransientBottomBar.LENGTH_LONG);
		snackbarVerkaufEntfernt.setAction(R.string.undo, this::onUndoDeleteVerkauf);

		RecyclerView recyclerView = findViewById(R.id.verkaufRecyclerView);
		VerkaufCardListAdapter adapter = new VerkaufCardListAdapter(this::onVerkaufFertig);
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		VerkaufViewModel verkaufViewModel = new ViewModelProvider(this).get(VerkaufViewModel.class);
		verkaufViewModel.getAllVerkaufList().observe(this, list -> {
			// TODO #4 filter Liste nach Einstellungen und gebe sie zum anzeigen an den Adapter
			adapter.submitList(list);
		});

		//		Intent discoverableIntent =
		//				new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		//		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		//		startActivity(discoverableIntent);

		Intent intent = new Intent(this, BluetoothServerService.class);
		startService(intent);
	}

	private void onUndoDeleteVerkauf(View view) {
		verkaufRepo.insert(lastDeletedVerkauf);
	}

	private void onVerkaufFertig(VerkaufWithPositionen verkauf) {
		verkaufRepo.delete(verkauf);
		this.lastDeletedVerkauf = verkauf;
		snackbarVerkaufEntfernt.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.menu_action_settings) {
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}