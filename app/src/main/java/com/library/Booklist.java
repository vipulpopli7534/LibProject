package com.library;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.library.databinding.ActivityBooklistBinding;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static android.R.id.list;

public class Booklist extends AppCompatActivity {

    ArrayList<String> books;
    LinkedHashMap<String, List<String>> booksMap;
    ListView listView;
    SearchView search;
    Intent intent;
    DataBaseHelper dataBaseHelper;
    BookListAdapter bookListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklist);
        dataBaseHelper = DataBaseHelper.getInstance(getApplicationContext());
        dataBaseHelper.createDatabase();
        booksMap = dataBaseHelper.fetchAll();
        books = new ArrayList<>();
        for (LinkedHashMap.Entry<String, List<String>> e : booksMap.entrySet()) {
            books.addAll(e.getValue());
        }
        listView = (ListView) findViewById(R.id.listview3);
        bookListAdapter= new BookListAdapter(books);
        listView.setAdapter(bookListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long id) {
                TextView textView = (TextView) arg1.findViewById(R.id.bookname);
                Intent intent = new Intent(Booklist.this, BookDisplay.class);
                intent.putExtra("bookname", textView.getText());
                startActivity(intent);
            }
        });
        search = (SearchView) findViewById(R.id.search);
        search.setActivated(true);
        search.setQueryHint("Type your keyword here");
        search.onActionViewExpanded();
        search.setIconified(false);
        search.clearFocus();

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                bookListAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
