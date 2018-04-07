package enzapps.com.hajath;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ss.com.bannerslider.views.BannerSlider;

public class screen2 extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    RequestQueue requestQueue;
    ImageView logo;
    TextView compnyname,status;
    BannerSlider coverphotos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        coverphotos=(BannerSlider)findViewById(R.id.banner);
        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("MENU"));
        tabLayout.addTab(tabLayout.newTab().setText("REVIEW"));
        tabLayout.addTab(tabLayout.newTab().setText("INFO"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(Color.parseColor("#9E9E9E"),Color.BLACK);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);
        //Creating our pager adapter
        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
        logo=(ImageView)findViewById(R.id.img_logo);
        compnyname=(TextView)findViewById(R.id.txt_compnyname);
        status=(TextView)findViewById(R.id.txt_status);
        getoutletInfo("1");
    }
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    public void getoutletInfo(final String outletId) {
        requestQueue = Volley.newRequestQueue(this);
        String url = "http://app.hajatonline.com/api/service/v1/outletinfo";
        final ProgressDialog dialog=new ProgressDialog(this);
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
                    compnyname.setText(result.getString("desc"));
                    Picasso.get().load("http://hajatonline.com/resources/"+result.getString("logo")).into(logo);
                    String working_status=result.getString("status_name");
                    if (working_status.equals("Open"))
                    {

                        status.setText("OPEN");
                        status.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.open_background));
                    }
                    else {
                        status.setText("CLOSED");
                        status.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.closed_background));
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


}
