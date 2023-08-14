package com.aplikasi.brand_audit;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class InputData extends AppCompatActivity {

    private TextInputLayout Output;
    private AutoCompleteTextView Pencarian;

    //Daftar Item Menggunakan Array
    private String[] listItem = {"Laptop", "Komputer", "Televisi", "Android", "Pensil", "Tas"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_data);

        Output = findViewById(R.id.pilih_jenis);
        Pencarian = findViewById(R.id.autoComplete_txt);

        //Membuat Listener untuk menangani kejadian pada AutoCompleteTextView
        Pencarian.addTextChangedListener((TextWatcher) this);
        //Membuat Adapter untuk mengatur bagaimana Item/Konten itu tampil
        Pencarian.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, listItem));
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //Method ini dipanggil sebelum edittext selesai diubah
//        Toast.makeText(getApplicationContext(),"Text Belum Diubah", Toast.LENGTH_SHORT).show();
    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //Method ini dipanggil saat text pada edittext sedang diubah
//        Output.getEditText().setText("Data Output : "+Pencarian.getText());
    }

    public void afterTextChanged(Editable editable) {
        //Method ini dipanggil setelah edittext selesai diubah
//        Toast.makeText(getApplicationContext(),"Text Selesai Diubah", Toast.LENGTH_SHORT).show();
    }
}
