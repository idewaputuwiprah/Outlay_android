package com.example.outlay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.outlay.controller.DatabaseCtrl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RegistrasiForm extends AppCompatActivity {

    DatabaseCtrl databaseCtrl;
    EditText nama, password, conf_password, pekerjaan;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        databaseCtrl = new DatabaseCtrl(this);
        Cursor user = databaseCtrl.getUser();
        if (user.getCount() == 0){
//            Toast.makeText(this, "you haven't register yet", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_registrasi_form);
            boundObject();
        }
        else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void boundObject() {
        nama = findViewById(R.id.nama_user);
        password = findViewById(R.id.pass);
        conf_password = findViewById(R.id.confirm_pass);
        submit = findViewById(R.id.submit_regis);
        pekerjaan = findViewById(R.id.occupation);
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        //Always use a SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        //Create array for salt
        byte[] salt = new byte[16];
        //Get a random salt
        sr.nextBytes(salt);
        //return salt
        return salt;
    }

    private static String getSecurePassword(String passwordToHash, byte[] salt)
    {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(salt);
            //Get the hash's bytes
            byte[] bytes = md.digest(passwordToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public void onSubmitRegis(View view) throws NoSuchAlgorithmException {
        String pass = password.getText().toString().trim();
        String conf_pass = conf_password.getText().toString().trim();
        if (pass.equals(conf_pass)){
            Intent intent = new Intent(this, MainActivity.class);
            byte[] salt = getSalt();
            String hashpass = getSecurePassword(pass, salt);
            databaseCtrl.insertUserCtrl(nama.getText().toString().trim(), pekerjaan.getText().toString().trim(),hashpass, salt);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Your password does not match", Toast.LENGTH_SHORT).show();
        }
    }
}
