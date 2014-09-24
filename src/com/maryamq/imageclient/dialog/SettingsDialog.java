package com.maryamq.imageclient.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.maryamq.imageclient.R;
import com.maryamq.imageclient.R.array;
import com.maryamq.imageclient.R.id;
import com.maryamq.imageclient.R.layout;
import com.maryamq.imageclient.model.UrlMetaData;

public class SettingsDialog extends DialogFragment {

	private UrlMetaData settings;

	public interface SettingsDialogListener {
		void onFinishSettingsDialog(UrlMetaData urlData);
	}

	public SettingsDialog(UrlMetaData settings) {
		this.settings = settings;
	}

	private Spinner setSpinnerList(View view, int spinnerId, int arrayResource,
			String selection) {
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		Spinner spinner = (Spinner) view.findViewById(spinnerId);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				view.getContext(), arrayResource,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		if (selection != null) {
			for (int i = 0; i < adapter.getCount(); i++) {
				if (adapter.getItem(i).equals(selection)) {
					spinner.setSelection(i);
					break;
				}
			}
		}

		return spinner;
	}

	private String getSpinnerValue(View view, int spinnerId) {
		Spinner spinner = (Spinner) view.findViewById(spinnerId);
		return spinner.getSelectedItem().toString();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_settings, container);
		getDialog().setTitle("Settings");
		setSpinnerList(view, R.id.spImageSize, R.array.settings_image_sizes,
				settings.imageSize);
		setSpinnerList(view, R.id.spImageType, R.array.settings_image_types,
				settings.imageType);
		setSpinnerList(view, R.id.spImageColors, R.array.settings_image_colors,
				settings.colorFilter);
		EditText etSite = (EditText) view.findViewById(R.id.etSite);
		etSite.setText(settings.siteFilter);
		ImageButton btnCancel = (ImageButton) view.findViewById(R.id.btnCancel);
		ImageButton btnSave = (ImageButton) view.findViewById(R.id.btnSave);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDialog().dismiss();
			}
		});
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				SettingsDialogListener listener = (SettingsDialogListener) getActivity();
				View v = SettingsDialog.this.getView();
				settings.colorFilter = getSpinnerValue(v, R.id.spImageColors);
				settings.imageSize = getSpinnerValue(v, R.id.spImageSize);
				settings.imageType = getSpinnerValue(v, R.id.spImageType);
				settings.siteFilter = ((EditText) v.findViewById(R.id.etSite))
						.getText().toString();
				listener.onFinishSettingsDialog(settings);
				dismiss();
			}
		});
		return view;
	}

}