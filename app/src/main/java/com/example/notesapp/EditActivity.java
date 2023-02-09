package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

public class EditActivity extends AppCompatActivity {
    EditText titleEdit,contentEditText;
    ImageButton Edit_note_btn;
    String title, content, oldtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        titleEdit = findViewById(R.id.Edit_title_text);
        contentEditText = findViewById(R.id.Edit_content_text);
        Edit_note_btn = findViewById(R.id.Edit_note_btn);
        title = getIntent().getStringExtra("title");
        Notes notes = new Notes();
        oldtitle = notes.getTitle();
        content = getIntent().getStringExtra("content");
        titleEdit.setText(title);
        contentEditText.setText(content);

        Edit_note_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               updateNote(title,content, oldtitle);
            }
        });

    }
    private void updateNote( final String title, final String content, final String oldtitle){
        String url= "http://192.168.100.3/NotesApp/update.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response", ""+ error);

            }
        }){
            protected HashMap<String,String> getParams() throws AuthFailureError{
                HashMap<String, String> map = new HashMap<>();
                map.put("title",title);
                map.put("content",content);
                map.put("oldtitle",oldtitle);
                return map;
            }

        };
        requestQueue.add(stringRequest);
    }

}