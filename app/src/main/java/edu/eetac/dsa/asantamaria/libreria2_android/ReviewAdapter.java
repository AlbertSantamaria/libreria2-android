package edu.eetac.dsa.asantamaria.libreria2_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sito on 12/12/14.
 */
public  class ReviewAdapter extends ArrayAdapter<Review> {
        ArrayList<Review> reviewList;
        LayoutInflater vi;
        int Resource;
        ViewHolder holder;

        public ReviewAdapter(Context context, int resource, ArrayList<Review> objects) {
            super(context, resource, objects);
            vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Resource = resource;
            reviewList = objects;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // convert view = design
            View v = convertView;
            if (v == null) {
                holder = new ViewHolder();
                v = vi.inflate(Resource, null);


                holder.tvReviewid = (TextView) v.findViewById(R.id.tvReviewid);
                holder.tvReviewContent = (TextView) v.findViewById(R.id.tvReviewContent);
                holder.tvReviewUsername = (TextView) v.findViewById(R.id.tvReviewUsername);

                v.setTag(holder);

            } else {
                holder = (ViewHolder) v.getTag();
            }

            System.out.println(reviewList.get(position).getReviewid());
            System.out.println(reviewList.get(position).getContent());

            holder.tvReviewid.setText("Reseña numero "+reviewList.get(position).getReviewid());
            holder.tvReviewContent.setText("Contenido: "+reviewList.get(position).getContent());
            holder.tvReviewUsername.setText("Escrito por: "+reviewList.get(position).getUsername());

            return v;

        }

        static class ViewHolder {
            public TextView tvReviewid;
            public TextView tvReviewContent;
            public TextView tvReviewUsername;


        }


    }
