package com.maryamq.imageclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

public class Utils {
	
	public static boolean isNetworkAvailable(Context context) {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}
	
	public static void loadPhoto(ImageView ivImage, Context context, String url){
		RequestCreator picassoRequest = Picasso.with(context).load(url);
		picassoRequest.transform(Utils.getImageScaleTransformer(ivImage.getMeasuredWidth(), ivImage.getMeasuredHeight()));
		picassoRequest.into(ivImage);
	}
	
	public static void loadPhotoForWidth(ImageView ivImage, Context context, String url){
		RequestCreator picassoRequest = Picasso.with(context).load(url);
		picassoRequest.transform(Utils.getImageScaleWidthTransformer(ivImage.getWidth()));
		picassoRequest.into(ivImage);
		
	}
	
	public static Transformation getImageScaleWidthTransformer(final int targetWidth) {
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
