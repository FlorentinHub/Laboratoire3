package com.example.laboratoire3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModifierCleActivity extends AppCompatActivity {

    private EditText editTextCle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_cle);

        editTextCle = findViewById(R.id.editTextCle);
        Button buttonValider = findViewById(R.id.buttonValider);

        buttonValider.setOnClickListener(v -> {
            String cleText = editTextCle.getText().toString();
            try {
                int nouvelleCle = Integer.parseInt(cleText);
                // Vérifie que la clé est comprise entre 0 et 25
                if (nouvelleCle >= 0 && nouvelleCle <= 25) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("nouvelleCle", nouvelleCle);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "La clé doit être comprise entre 0 et 25", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Veuillez entrer un nombre valide", Toast.LENGTH_SHORT).show();
            }
        });
    }
}