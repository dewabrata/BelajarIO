package com.juaracoding.belajario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    Button btnInsert;
    Button btnBaca;
    EditText txtKalimat;
    RecyclerView lstData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btnInsert);
        btnBaca = findViewById(R.id.btnBaca);
        txtKalimat = findViewById(R.id.txtKalimat);
        lstData = findViewById(R.id.lstData);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtKalimat.getText()!=null && !txtKalimat.getText().toString().equalsIgnoreCase("")) {

                    tulis(txtKalimat.getText().toString());
                    txtKalimat.setText("");
                }else{
                    Toast.makeText(MainActivity.this, "Data harus diisi ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baca();
            }
        });

    }


     /**
      Ini adalah method untuk menulis ke dalam file txt

      @param  kalimat  berisikan text yang akan ditulis


      **/




    public void tulis(String kalimat){
        String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/data_belajar_io.txt";
        try {
            Writer wr = new FileWriter(fileName,true);
            wr.write(kalimat + "\n" );
            wr.flush();
            wr.close();
        }catch (IOException e){
            Log.e("BELAJARIO",e.getMessage().toString());
            Toast.makeText(MainActivity.this, "Data " +kalimat +" gagal di insert ", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(MainActivity.this, "Data " +kalimat +" berhasil di insert ", Toast.LENGTH_SHORT).show();

    }

    public void baca(){
        String data ="";
        String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/data_belajar_io.txt";

        try {
            Scanner scanner = new Scanner(new File(fileName));
            Scanner valueScanner = null;



            while (scanner.hasNextLine()) {
                valueScanner = new Scanner(scanner.nextLine());


                while (valueScanner.hasNext()) {
                     data = data + valueScanner.next();


                }

            }

            scanner.close();
        }catch (IOException e){

        }
        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();

    }
}
