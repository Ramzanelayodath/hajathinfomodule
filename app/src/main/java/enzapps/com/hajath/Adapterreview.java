package enzapps.com.hajath;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapterreview  extends ArrayAdapter {
    private Context context;
    private ArrayList<Modelreview> reviews;
    ArrayList<Modelreview> arrayList=null;
    Adapterreview adapter=null;

    public Adapterreview(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);

        this.context = context;
        reviews = objects;
    }
    private class ViewHolder{

        TextView name,review,date,rating;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_row_review, null);
            holder = new ViewHolder();

            holder.date=(TextView)convertView.findViewById(R.id.txt_date);
            holder.name=(TextView)convertView.findViewById(R.id.txt_username);
            holder.rating=(TextView)convertView.findViewById(R.id.txt_rating);
            holder.review=(TextView)convertView.findViewById(R.id.txt_review);
            convertView.setTag(holder);
        } else {
            holder = (Adapterreview.ViewHolder) convertView.getTag();
        }
        Modelreview modelreview=reviews.get(position);
        holder.review.setText(modelreview.getText());
        holder.name.setText(modelreview.getName());
        holder.date.setText(modelreview.getDate());
        float rating=Float.parseFloat(modelreview.getRating());
        if (rating<2.5)
        {
            holder.rating.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.closed_background));
            holder.rating.setText(modelreview.getRating()+" ★");
        }
        else {
            holder.rating.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.open_background));
            holder.rating.setText(modelreview.getRating()+" ★");
        }
        return convertView;
    }


}
