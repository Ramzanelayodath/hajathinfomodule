package enzapps.com.hajath;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Review extends Fragment {

    View v;
    Nonscrolllistview list;
    RequestQueue requestQueue;
    ArrayList<Modelreview>arrayList=null;
    Adapterreview adapterreview=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.fragment_review, container, false);
        list=(Nonscrolllistview)v.findViewById(R.id.lst_review);
        getReview("1");
        return  v;
    }

    public void getReview(final String userId)
    {
        final ArrayList<Modelreview> model_reviews=new ArrayList<Modelreview>();
        requestQueue= Volley.newRequestQueue(getActivity());
        String url="http://app.hajatonline.com/api/service/v1/reviews";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object=new JSONObject(response);
                    JSONArray resultArray=object.getJSONArray("Result");
                    for (int i = 0; i <resultArray.length() ; i++) {
                        JSONObject object1=resultArray.getJSONObject(i);
                        String text=object1.getString("text");
                        String date= object1.getString("date");
                        String rating=object1.getString("rating");
                        String name=object1.getString("rev_name");
                        model_reviews.add(new Modelreview(name,text,date,rating));
                        arrayList=model_reviews;
                        adapterreview=new Adapterreview(getActivity(),R.layout.list_row_review,arrayList);
                        list.setAdapter(adapterreview);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Try again...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("OutletId", userId);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("API_KEY", "A93reRTUJHsCuQSHR+L3GxqOJyDmQpCgps102ciuabc=");
                return params;
            }

        };
        requestQueue.add(request);
    }


}
