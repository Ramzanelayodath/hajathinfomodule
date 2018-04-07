package enzapps.com.hajath;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RatingBar;

import com.android.volley.RequestQueue;

public class addreviewactivity extends AppCompatActivity {
    EditText edt_review;
    RatingBar ratingBar;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreviewactivity);
        edt_review=(EditText)findViewById(R.id.edt_review);
        ratingBar=(RatingBar)findViewById(R.id.rating);
       // String rate = String.valueOf(rating.getRating());
    }
}
