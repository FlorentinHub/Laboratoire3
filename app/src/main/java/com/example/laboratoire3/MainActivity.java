package com.example.laboratoire3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menucryptage, menu);
        return true;
        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==R.id.menu_open){
            Toast.makeText(this, "Ouverture de fichier", Toast.LENGTH_SHORT).show();
        }else if(itemId==R.id.menu_save){
            Toast.makeText(this, "Option de sauvegarde", Toast.LENGTH_SHORT).show();
        }
        else if(itemId==R.id.menu_key){
            Toast.makeText(this, "clee de Cryptage", Toast.LENGTH_SHORT).show();
        }
        else if(itemId==R.id.menu_quit){
            Toast.makeText(this, "Quitter application", Toast.LENGTH_SHORT).show();
        }
            return super.onOptionsItemSelected(item);
        }
    }