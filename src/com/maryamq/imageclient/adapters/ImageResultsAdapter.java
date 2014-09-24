package com.maryamq.imageclient.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maryamq.imageclient.R;
import com.maryamq.imageclient.model.ImageResult;
import com.maryamq.imageclient.utils.Utils;
import com.squareup.picasso.Picasso;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

	public ImageResultsAdapter(Context context,
			ArrayList<ImageResult> imageResults) {
		super(context, android.R.layout.simple_list_item_1, imageResults);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageResult result = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_image_result, parent, false);
		}
		ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
		TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
		ivImage.setImageResource(0);

		tvTitle.setText(Html.fromHtml(result.title));
		//Utils.loadPhotoForWidth(ivImage, getContext(), result.thumbUrl);
		Picasso.with(getContext()).load(result.thumbUrl).into(ivImage);
		return convertView;
	}
}
