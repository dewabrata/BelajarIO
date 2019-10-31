package com.juaracoding.belajario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.filepicker.controller.DialogSelectionListener;
import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.view.FilePickerDialog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ToDoListActivity extends AppCompatActivity {

    EditText txtTitle, txtNotes;
    CalendarView txtDate;
    Button btnBuka, btnSimpan;
    RecyclerView lstData;

    Date dummy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        txtTitle = findViewById(R.id.txtTitle);
        txtNotes = findViewById(R.id.txtNotes);
        txtDate = findViewById(R.id.calendarView);
        btnBuka = findViewById(R.id.btnBuka);
        btnSimpan = findViewById(R.id.btnSimpan);
        lstData = findViewById(R.id.lstData);

        dummy = new Date();


        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = null;

        final FilePickerDialog dialog = new FilePickerDialog(ToDoListActivity.this,properties);
        dialog.setTitle("Pilih file csv");

        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                //files is the array of the paths of files selected by the Application User.
                setList(files[0]);
            }
        });



        txtDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                dummy = new Date(calendarView.getDate());
            }
        });
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelTDL model = new ModelTDL();
                model.setTitle(txtTitle.getText().toString());
                model.setTanggal(dummy);
                model.setNotes(txtNotes.getText().toString());

                tulis(model);


            }
        });

        btnBuka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();

            }
        });
    }


    public void tulis(ModelTDL data) {
        String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/todolistjuara.txt";
        try {
            Writer wr = new FileWriter(fileName, true);
            wr.write(data.getTitle() + ";");
            wr.write(data.getTanggal().toString() + ";");
            wr.write(data.getNotes() + "\n");
            wr.flush();
            wr.close();
        } catch (IOException e) {
            Log.e("BELAJARIO", e.getMessage().toString());
            Toast.makeText(ToDoListActivity.this, "Data  gagal di insert ", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(ToDoListActivity.this, "Data berhasil di insert ", Toast.LENGTH_SHORT).show();

    }


    public ArrayList<ModelTDL> baca(String fileName) {
        ArrayList<ModelTDL> todoList = new ArrayList<>();

        try {


            Scanner scanner = new Scanner(new File(fileName));
            Scanner valueScanner = null;
            int index = 0;


            while (scanner.hasNextLine()) {
                valueScanner = new Scanner(scanner.nextLine());
                valueScanner.useDelimiter(";");
                ModelTDL todo = new ModelTDL();

                while (valueScanner.hasNext()) {
                    String data = valueScanner.next();
                    if (index == 0) {
                        todo.setTitle(data);
                    } else if (index == 1) {
                        todo.setTanggal(new Date());
                    } else if (index == 2) {
                        todo.setNotes(data);
                    }
                    index++;
                }
                index = 0;
                todoList.add(todo);
            }

            scanner.close();
        } catch (IOException e) {

        }

        return todoList;
    }


    public void setList(String fileName){
        ToDoListAdapter itemArrayAdapter = new ToDoListAdapter( baca(fileName));

        lstData.setLayoutManager(new LinearLayoutManager(this));
        lstData.setItemAnimator(new DefaultItemAnimator());
        lstData.setAdapter(itemArrayAdapter);
    }

}
