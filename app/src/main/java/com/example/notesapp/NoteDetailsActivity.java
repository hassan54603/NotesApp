package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

public class NoteDetailsActivity extends AppCompatActivity {

    EditText titleEdit,contentEditText;
    ImageButton saveNoteBtn;
    FloatingActionButton Home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        Home = findViewById(R.id.home);
        Home.setOnClickListener((v)->startActivity(new Intent(NoteDetailsActivity.this,MainActivity.class)));

        titleEdit = findViewById(R.id.notes_title_text);
        contentEditText = findViewById(R.id.notes_content_text);
        saveNoteBtn = findViewById(R.id.save_note_btn);

        saveNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEdit.getText().toString();
                String content = contentEditText.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://192.168.100.3/NotesApp/create.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("yes")){
                                    Toast.makeText(NoteDetailsActivity.this, "Data saved", Toast.LENGTH_SHORT).show();
                                }
                                else if(response.equals("no")) {
                                    Toast.makeText(NoteDetailsActivity.this, "Data not saved", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NoteDetailsActivity.this, "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("title", title);
                        paramV.put("content", content);
                        return paramV;
                    }
                };
                queue.add(stringRequest);

            }
        });
    }
}