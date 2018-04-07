package enzapps.com.hajath;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Info extends Fragment {
    RequestQueue requestQueue;
    View v;
    TextView txt_status,txt_address,txt_contact,txt_pamodes,txt_categories,txt_workingdays,txt_minorder,
            txt_dltime,txt_dlfee,txt_orderingmode,txt_features;
    String name,latitude,longitude,day;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       v= inflater.inflate(R.layout.fragment_info, container, false);
       txt_address=(TextView)v.findViewById(R.id.txt_address);
       txt_status=(TextView)v.findViewById(R.id.txt_status);
       txt_contact=(TextView)v.findViewById(R.id.txt_contact);
       txt_pamodes=(TextView)v.findViewById(R.id.txt_pamodes);
       txt_categories=(TextView)v.findViewById(R.id.txt_categories);
       txt_minorder=(TextView)v.findViewById(R.id.txt_minorder);
       txt_dltime=(TextView)v.findViewById(R.id.txt_dltime);
       txt_dlfee=(TextView)v.findViewById(R.id.txt_dlfee);
       txt_features=(TextView)v.findViewById(R.id.txt_features);
       txt_orderingmode=(TextView)v.findViewById(R.id.txt_orderingmode);
       txt_workingdays=(TextView)v.findViewById(R.id.txt_workingdays);
       txt_address.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               gotoMap();
           }
       });

        day=gettodayDay();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String currentTime =mdformat.format(calendar.getTime());
        getoutletInfo("1",currentTime,day);

       return v;
    }
    public void getoutletInfo(final String outletId, String time, final String day) {
        requestQueue = Volley.newRequestQueue(getActivity());
        String url = "http://app.hajatonline.com/api//service/v1/outletinfo";
        final ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject result=jsonObject.getJSONObject("Result");
                    System.out.println(result);
                    name=result.getString("desc");
                    latitude=result.getString("lat");
                    longitude=result.getString("long");
                    txt_address.setText(result.getString("address"));
                    txt_address.setPaintFlags(txt_address.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    txt_status.setText(result.getString("status_name"));
                    txt_contact.setText(result.getString("cell"));
                    txt_minorder.setText(result.getString("min_ord_amt"));
                    txt_dltime.setText(result.getString("dlvry_tm"));
                    txt_dlfee.setText(result.getString("dlvry_fee"));
                    JSONArray paymodes_array=result.getJSONArray("paymodes");
                    for (int i = 0; i <paymodes_array.length() ; i++) {

                        txt_pamodes.append(paymodes_array.getString(i));
                    }
                    JSONArray categories_array=result.getJSONArray("categories");
                    for (int i = 0; i <categories_array.length() ; i++) {
                        if (categories_array.length()==1)
                        {
                            txt_categories.setText(categories_array.getString(i));
                        }
                        else {
                            txt_categories.append(categories_array.getString(i)+"  ");
                        }
                    }
                    JSONObject timings_object=result.getJSONObject("timings");
                    JSONArray days_array=timings_object.getJSONArray("days");
                    for (int i = 0; i <days_array.length() ; i++) {
                        JSONObject days_object=days_array.getJSONObject(i);
                        if (days_array.length()==1)
                        {
                            txt_workingdays.setText(days_object.getString("name").toLowerCase());
                        }
                        else {
                            txt_workingdays.append(days_object.getString("name").toLowerCase()+"  ");
                        }
                        if (days_object.getString("name").equals(day))
                        {
                            System.out.println("ok");
                            JSONObject day_object=days_array.getJSONObject(i);
                            JSONArray timings_array=day_object.getJSONArray("timings");
                            for (int j = 0; j <timings_array.length() ; j++) {
                                JSONObject todaydate_object=timings_array.getJSONObject(i);


                            }


                        }

                    }
                    JSONArray ordermodes_array=result.getJSONArray("ordermodes");
                    for (int i = 0; i <ordermodes_array.length() ; i++) {
                        txt_orderingmode.append(ordermodes_array.getString(i)+"  ");

                    }
                    JSONArray features_array=result.getJSONArray("features");
                    for (int i = 0; i <features_array.length() ; i++) {
                        txt_features.append(features_array.getString(i)+"  ");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                System.out.println(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ServiceId", "1002");
                params.put("OutletId", outletId);
                params.put("FetchItemNames","true");
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
    public void gotoMap(){
        try {
            double lat = Double.parseDouble(latitude);
            double longi = Double.parseDouble(longitude);
            String label = name;
            String uriBegin = "geo:" + lat + "," + longi;
            String query = lat + "," + longi + "(" + label + ")";
            String encodedQuery = Uri.encode(query);
            String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
            Uri uri = Uri.parse(uriString);
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            try{
                startActivity(intent);}catch (ActivityNotFoundException e){}
        }catch (NumberFormatException e){}

    }
    public String gettodayDay()
    {
        Date now = new Date();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of the week abbreviated
        String day=simpleDateformat.format(now);
        return  day;
        /*Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
        return String.valueOf(dayOfWeek);*/
    }


}
