package com.library;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class BookDisplay extends AppCompatActivity {

    PDFView pd;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_display);
        pd=(PDFView) findViewById(R.id.pdfView);
        intent = getIntent();
        String bookname = intent.getStringExtra("bookname");
        String pdf = bookname+".pdf";
        pd.fromAsset(pdf).load();
    }
}
