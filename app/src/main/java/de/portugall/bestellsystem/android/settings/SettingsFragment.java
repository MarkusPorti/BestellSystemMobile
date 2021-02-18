package de.portugall.bestellsystem.android.settings;

import android.os.Bundle;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.PreferenceFragmentCompat;
import de.portugall.bestellsystem.android.R;

import java.util.Set;

public class SettingsFragment extends PreferenceFragmentCompat {

	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
		setPreferencesFromResource(R.xml.settings, rootKey);

		MultiSelectListPreference filterListPreference = findPreference("pref_list_filter");
		assert filterListPreference != null;
		setSummeryFromValues(filterListPreference, filterListPreference.getValues());
		filterListPreference.setOnPreferenceChangeListener((preference, newValue) -> {
			setSummeryFromValues(filterListPreference, (Set<String>) newValue);
			return true;
		});
	}

	private void setSummeryFromValues(MultiSelectListPreference preference, Set<String> values) {
		preference.setSummary(String.join(", ", values));
	}

}
