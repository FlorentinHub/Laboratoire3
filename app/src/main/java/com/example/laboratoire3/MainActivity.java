package com.example.laboratoire3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private EditText editTextMessage, editTextMessageCrypte;
    private int shiftKey = 0; //Clé de décalage à 0 - Test
    public String nomFichier="monCryptage.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences fichier = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editeur = fichier.edit();

//        editeur.putBoolean()

        editTextMessage = findViewById(R.id.editTextMessage);
        editTextMessageCrypte = findViewById(R.id.editTextMessageCrypte);

        Button buttonEncrypt = findViewById(R.id.btn_Crypter);
        Button buttonDecrypt = findViewById(R.id.btn_Decrypter);

        buttonEncrypt.setOnClickListener(v -> {
            String message = editTextMessage.getText().toString();
            String encryptedMessage = encryptMessage(message, shiftKey);
            Toast.makeText(this, "Message Crypte", Toast.LENGTH_SHORT).show();
            editTextMessageCrypte.setText(encryptedMessage);
        });

        buttonDecrypt.setOnClickListener(v -> {
            String message = editTextMessage.getText().toString();
            String decryptedMessage = decryptMessage(message, shiftKey);
            Toast.makeText(this, "Message Decrypte", Toast.LENGTH_SHORT).show();

            editTextMessageCrypte.setText(decryptedMessage);
        });
    }
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menucryptage, menu);
        return true;
        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_open) {
            Toast.makeText(this, "Ouverture de fichier", Toast.LENGTH_SHORT).show();
            String content = ouvrirFichier(nomFichier);
                editTextMessage.setText(content);
        } else if (itemId == R.id.menu_save) {
            String contenu = editTextMessage.getText().toString();

            Log.i("editTextMessage.getText().toString();","editTextMessage.getText().toString()"+contenu);
            sauvegarderFichier(nomFichier, contenu);
        }
        else if(itemId==R.id.menu_key){
            Toast.makeText(this, "clee de Cryptage", Toast.LENGTH_SHORT).show();
                Intent keyIntent = new Intent(this, ModifierCleActivity.class);
                startActivityForResult(keyIntent, 1); // Utilisation de startActivityForResult pour attendre la réponse
        }
        else if(itemId==R.id.menu_quit){
            Toast.makeText(this, "Quitter application", Toast.LENGTH_SHORT).show();
            finish();
            System.exit(0);
        }
            return super.onOptionsItemSelected(item);
        }

    // Méthode pour ouvrir un fichier
    private String ouvrirFichier(String nomFichier) {
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, nomFichier);
        byte[] content = new byte[(int)readFrom.length()];
        try {
            FileInputStream stream = new FileInputStream(readFrom);
            stream.read(content);
            return new String(content);
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

        //Methode pour sauvegarder le fichier
        private void sauvegarderFichier(String nomFichier, String contenu) {
            File path = getApplicationContext().getFilesDir();
            File file = new File(path, nomFichier);
            // Supprimer le fichier s'il existe
            if (file.exists()) {
                file.delete();
            }
            try {
                FileOutputStream writer = new FileOutputStream(file);
                writer.write(contenu.getBytes());
                writer.close();
                Toast.makeText(this, "Sauvegarde effectuée dans " + nomFichier, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("Erreur", "mon Erreur:" + e);
                throw new RuntimeException(e);
            }
        }


    // Méthode pour crypter le message
    private String encryptMessage(String message, int shiftKey) {
        Log.v("test","message: "+message);
        Log.v("test","shiftKey: "+shiftKey);
        StringBuilder messageEncrypte = new StringBuilder();
        for(int i = 0; i<message.length(); i++) {
            char charActuel = message.charAt(i);

            if(Character.isLetter(charActuel)){
                //Si Majuscule
                char base = Character.isUpperCase(charActuel) ? 'A':'a';
                char charEncrypte = (char) ((charActuel - base + shiftKey) % 26 + base);
                messageEncrypte.append(charEncrypte);
            }else{
                messageEncrypte.append(charActuel);
            }
        }
        return messageEncrypte.toString();
    }

    // Méthode pour décrypter le message
    private String decryptMessage(String message, int shiftKey) {
        //Inverser la clee et l'encrypter
        int cleeInversee = 26 - shiftKey;
        return encryptMessage(message, cleeInversee);
    }
    public void fermerClavier() { //StackOverFlow.
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data); // Appel à la méthode parent
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int nouvelleCle = data.getIntExtra("nouvelleCle", 0);
                // Mettez à jour la clé de décalage
                shiftKey = nouvelleCle;
            }
        }
    }
}
