package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    private static final String data_url = "http://192.168.100.3/NotesApp/read.php";


    RecyclerView recyclerView;
    NotesAdapter notesAdapter;
    List<Notes> notesList;
    FloatingActionButton addNoteBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNoteBtn = findViewById(R.id.add_note_btn);
        addNoteBtn.setOnClickListener((v)->startActivity(new Intent(MainActivity.this,NoteDetailsActivity.class)));

        recyclerView = findViewById(R.id.list_data);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notesList=new ArrayList<>();
        LoadAllNotes();


        addNoteBtn = findViewById(R.id.add_note_btn);
        addNoteBtn.setOnClickListener((v)->startActivity(new Intent(MainActivity.this,NoteDetailsActivity.class)));
    }

    private void LoadAllNotes() {
        JsonArrayRequest request =new JsonArrayRequest(data_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                for (int i=0;i<array.length();i++){
                    try {
                        JSONObject object=array.getJSONObject(i);
                        String title=object.getString("title").trim();
                        String contant=object.getString("content").trim();
                        String ndate=object.getString("note_date").trim();

                        Notes user= new Notes();
                        user.setTitle(title);
                        user.setContent(contant);
                        user.setNotedate(ndate);
                        notesList.add(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                notesAdapter=new NotesAdapter(MainActivity.this,notesList);
                recyclerView.setAdapter(notesAdapter);

            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        requestQueue.add((request));

    }
}