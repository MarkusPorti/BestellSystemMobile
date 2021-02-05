package de.portugall.bestellsystem.android;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.portugall.bestellsystem.android.data.Verkauf;
import de.portugall.bestellsystem.android.data.VerkaufPosition;
import de.portugall.bestellsystem.android.data.VerkaufViewModel;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";
	private VerkaufCardListAdapter adapter;
	private VerkaufViewModel verkaufViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		RecyclerView recyclerView = findViewById(R.id.verkaufRecyclerView);
		adapter = new VerkaufCardListAdapter();
		recyclerView.setAdapter(adapter);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		verkaufViewModel = new ViewModelProvider(this).get(VerkaufViewModel.class);
		verkaufViewModel.getAllVerkaufList().observe(this, adapter::submitList);

		Intent discoverableIntent =
				new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		startActivity(discoverableIntent);

		Intent intent = new Intent(this, BluetoothServerService.class);
		startService(intent);

//		Verkauf verkauf = new Verkauf();
//		verkauf.setUhrzeit("18:40");
//		VerkaufPosition pos1 = new VerkaufPosition();
//		pos1.setMenge(2);
//		pos1.setArtikel("Erbsensuppe");
//		VerkaufPosition pos2 = new VerkaufPosition();
//		pos2.setMenge(1);
//		pos2.setArtikel("Brötchen");
//		verkauf.setPositionen(Arrays.asList(pos1, pos2));
//		adapter.addItem(verkauf);
	}

}