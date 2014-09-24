package com.maryamq.imageclient.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

public class Utils {

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null
				&& activeNetworkInfo.isConnectedOrConnecting();
	}

	public static void loadPhoto(ImageView ivImage, Context context, String url) {
		RequestCreator picassoRequest = Picasso.with(context).load(url);
		picassoRequest.transform(Utils.getImageScaleTransformer(
				ivImage.getMeasuredWidth(), ivImage.getMeasuredHeight()));
		picassoRequest.into(ivImage);
	}

	public static void loadPhotoForWidth(ImageView ivImage, Context context,
			String url) {
		RequestCreator picassoRequest = Picasso.with(context).load(url);
		picassoRequest.transform(Utils.getImageScaleWidthTransformer(ivImage
				.getWidth()));
		picassoRequest.into(ivImage);

	}

	public static Transformation getImageScaleWidthTransformer(
			final int targetWidth) {
		return new Transformation() {

			@Override
			public Bitmap transform(Bitmap b) {
				Bitmap result = BitmapScaler.scaleToFitWidth(b, targetWidth);
				if (result != b) {
					// Same bitmap is returned if sizes are the same
					b.recycle();
				}
				return result;
			}

			@Override
			public String key() {
				return "cropPosterTransformation200";
			}
		};
	}

	public static boolean onShareItem(View v, int imageViewId) {
		// Get access to bitmap image from view
		ImageView ivImage = (ImageView) v.findViewById(imageViewId);
		// Get access to the URI for the bitmap
		Uri bmpUri = getLocalBitmapUri(ivImage);
		if (bmpUri != null) {
			// Construct a ShareIntent with link to image
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
			shareIntent.setType("image/*");
			// Launch sharing dialog for image
			v.getContext().startActivity(
					Intent.createChooser(shareIntent, "Share Image"));
			return true;
		} else {
			return false;
		}
	}

	public static Uri getLocalBitmapUri(ImageView imageView) {
		// Extract Bitmap from ImageView drawable
		Drawable drawable = imageView.getDrawable();
		Bitmap bmp = null;
		if (drawable instanceof BitmapDrawable) {
			bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
		} else {
			return null;
		}
		// Store image to default external storage directory
		Uri bmpUri = null;
		try {
			File file = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
					"share_image_" + System.currentTimeMillis() + ".png");
			file.getParentFile().mkdirs();
			FileOutputStream out = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.close();
			bmpUri = Uri.fromFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bmpUri;
	}

	public static Transformation getImageScaleTransformer(
			final int targetWidth, final int targetHeight) {
		return new Transformation() {

			@Override
			public Bitmap transform(Bitmap b) {
				Bitmap result = BitmapScaler.scaleToFill(b, targetWidth,
						targetHeight);
				if (result != b) {
					// Same bitmap is returned if sizes are the same
					b.recycle();
				}
				return result;
			}

			@Override
			public String key() {
				return "cropPosterTransformation200";
			}
		};
	}

}
