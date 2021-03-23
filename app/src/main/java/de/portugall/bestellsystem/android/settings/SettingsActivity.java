package de.portugall.bestellsystem.android.settings;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import de.portugall.bestellsystem.android.R;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

	private Toolbar toolbar;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

		getSupportFragmentManager().beginTransaction()
								   .replace(R.id.settings_container, new SettingsFragment())
								   .commit();
	}

	@Override
	public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
		Bundle args = pref.getExtras();
		Fragment fragment = getSupportFragmentManager().getFragmentFactory()
													   .instantiate(getClassLoader(), pref.getFragment());
		fragment.setArguments(args);
		fragment.setTargetFragment(caller, 0);
		toolbar.setTitle(pref.getTitle());
		// Replace the Fragments
		getSupportFragmentManager().beginTransaction()
								   .replace(R.id.settings_container, fragment)
								   .addToBackStack(null)
								   .commit();
		return true;
	}

	/**
	 * Diese Methode behandelt den Home-Button der Toolbar.
	 * Sie kümmert sich darum, dass ggf. aufgerufene Setting-Fragmente zuerst geschlossen werden,
	 * anstatt direkt zurück zur MainActivity zu springen.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
				getSupportFragmentManager().popBackStack();
				toolbar.setTitle(item.getTitle());
				return true;
			} else {
				finishAfterTransition();
			}
		}
		return super.onOptionsItemSelected(item);
	}
}
