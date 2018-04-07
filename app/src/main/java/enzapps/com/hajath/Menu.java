package enzapps.com.hajath;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
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


public class Menu extends Fragment {
    RequestQueue requestQueue;
    private ExpandListAdapter ExpAdapter;
    private ExpandableListView ExpandList;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_menu, container, false);
        ExpandList = (ExpandableListView)v.findViewById(R.id.exp_list);

        loadMenu("1","301");
        return  v;
    }

    public void loadMenu(final String OutletId, final String OutletGroupId)
    {
        requestQueue= Volley.newRequestQueue(getActivity());
        final ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();
        String url="http://app.hajatonline.com/api//service/v1/products";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<MenuModel> list = new ArrayList<MenuModel>();
                ArrayList<Items> ch_list;
                dialog.dismiss();
                try {
                    ch_list=new ArrayList<Items>();
                    JSONObject object=new JSONObject(response);
                    JSONArray result_array=object.getJSONArray("Result");
                    for (int i = 0; i <result_array.length() ; i++) {
                        JSONObject object1=result_array.getJSONObject(i);
                        Items items=new Items(object1.getString("name"),object1.getString("price"),object1.getString("image"),object1.getString("id"));
                        ch_list.add(items);
                        MenuModel model=new MenuModel(object1.getString("name"),ch_list);
                        list.add(model);
                    }
                    ExpAdapter=new ExpandListAdapter(getActivity(),list);
                    ExpandList.setAdapter(ExpAdapter);




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ServiceId", "1002");
                params.put("OutletId",OutletId);
                params.put("OutletGroupId",OutletGroupId);
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
