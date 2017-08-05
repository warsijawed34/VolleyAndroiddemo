package volleyandroiddemo.com.volleyandroiddemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class SimpleRequest extends AppCompatActivity implements View.OnClickListener {
    private Button btnfetch, btnRQueue;
    private TextView tvDisplay;
    private ImageView ivDisplay;
    private String url_simple = "http://www.google.com";
    private String url_simple1 = "https://api.androidhive.info/volley/string_response.html";
    private String url_image = "https://s-media-cache-ak0.pinimg.com/736x/35/04/c6/3504c6e12e58d5571eb88830f7bc4f7c--deepika-padukone-hair-dipika-padukone.jpg";
    private RequestQueue requestQueue;
    private String TAG = SimpleRequest.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_request);

        tvDisplay = (TextView) findViewById(R.id.tvDisplay);
        btnfetch = (Button) findViewById(R.id.btnFetch);
        ivDisplay = (ImageView) findViewById(R.id.ivDisplay);
        btnRQueue = (Button) findViewById(R.id.btnRqueue);
        btnfetch.setOnClickListener(this);
        btnRQueue.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFetch:
                requestImages();
                break;
            case R.id.btnRqueue:
                Intent intent = new Intent(SimpleRequest.this, ImageRequestActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    // simple request from google.com
    private void simpleRequest() {
        requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_simple, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tvDisplay.setText("Response is: " + response.substring(0, 500));
                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvDisplay.setText("That didn't work!");
                requestQueue.stop();
            }
        });

        requestQueue.add(stringRequest);

    }

    //using RequestQueue, network, and cache
    private void setRequestQueue() {
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url_simple, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tvDisplay.setText("Response is: " + response.substring(0, 500));
                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvDisplay.setText("That didn't work!");
                requestQueue.stop();
            }
        });
        requestQueue.add(stringRequest1);

    }

    //using singleton class here..
    private void singletonClassMethod() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_simple, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tvDisplay.setText("Response is: " + response.substring(0, 500));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvDisplay.setText("That didn't work!");
            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    //fetching image_url here...
    private void requestImages() {

        ImageRequest imageRequest = new ImageRequest(url_image, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ivDisplay.setImageBitmap(response);
            }
        }, 0, 0, null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SimpleRequest.this, "Error", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(imageRequest);
    }
}
