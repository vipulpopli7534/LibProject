package com.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    LinkedHashMap<String, List<String>> booksMap;
    List<String> categories;
    Button allBooks;
    DataBaseHelper dataBaseHelper;
    ArrayList<String> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        allBooks = findViewById(R.id.button);
        dataBaseHelper = DataBaseHelper.getInstance(getApplicationContext());
        dataBaseHelper.createDatabase();
        categories = dataBaseHelper.getCategories();
        booksMap = dataBaseHelper.fetchAll();
        books = new ArrayList<>();
        for (LinkedHashMap.Entry<String, List<String>> e : booksMap.entrySet()) {
            books.addAll(e.getValue());
        }
        expandableListView = (ExpandableListView) findViewById(R.id.listView);
        expandableListAdapter = new ExpandableListAdapter(getApplicationContext(), categories, booksMap);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {
                String data= expandableListAdapter.getChild(groupPosition, childPosition).toString();
                Intent intent = new Intent(HomeActivity.this, BookDisplay.class);
                intent.putExtra("bookname", data);
                startActivity(intent);
                return true;  // i missed this
            }
        });
        allBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Booklist.class);
                startActivity(intent);
            }
        });
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousItem )
                    expandableListView.collapseGroup(previousItem );
                previousItem = groupPosition;
            }
        });
    }
}
