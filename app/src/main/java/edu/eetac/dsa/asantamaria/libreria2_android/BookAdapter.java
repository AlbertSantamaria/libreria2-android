package edu.eetac.dsa.asantamaria.libreria2_android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {
	ArrayList<Book> bookList;
	LayoutInflater vi;
	int Resource;
	ViewHolder holder;

	public BookAdapter(Context context, int resource, ArrayList<Book> objects) {
		super(context, resource, objects);
		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Resource = resource;
		bookList = objects;
	}
 
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// convert view = design
		View v = convertView;
		if (v == null) {
			holder = new ViewHolder();
			v = vi.inflate(Resource, null);
			//holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
			holder.tvTitle = (TextView) v.findViewById(R.id.tvTitle);
			holder.tvAuthor = (TextView) v.findViewById(R.id.tvAuthor);
			holder.tvPublisher = (TextView) v.findViewById(R.id.tvPublisher);
			holder.tvEditionNumber = (TextView) v.findViewById(R.id.tvEditionNumber);
			v.setTag(holder);

		} else {
			holder = (ViewHolder) v.getTag();
		}
		//holder.imageview.setImageResource(R.drawable.ic_launcher);
		//new DownloadImageTask(holder.imageview).execute(bookList.get(position).getImage());
		holder.tvTitle.setText(bookList.get(position).getTitle());
		holder.tvAuthor.setText(bookList.get(position).getAuthor());
		holder.tvPublisher.setText(bookList.get(position).getPublisher());
		holder.tvEditionNumber.setText(bookList.get(position).getEdition()+" Edicion");

		return v;

	}

	static class ViewHolder {
		public ImageView imageview;
		public TextView tvTitle;
		public TextView tvAuthor;
		public TextView tvPublisher;
		public TextView tvEditionNumber;


	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}

	}
}