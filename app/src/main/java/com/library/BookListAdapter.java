package com.library;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.library.databinding.ActivityBooklistBinding;
import com.library.databinding.ListChilditemBinding;

import java.util.ArrayList;
import java.util.List;


public class BookListAdapter extends BaseAdapter implements Filterable {

    List<String> books;
    List<String> booksFilterList;
    BookFilter bookFilter;
    private LayoutInflater inflater;
    ListChilditemBinding listChilditemBinding;
    public BookListAdapter(List<String> bookList) {
        books= bookList;
        booksFilterList= bookList;
    }


    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public String getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, final ViewGroup parent) {
        String text = books.get(position);

        if(view == null) {
            inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.search_item, null);
        }

        TextView textView = (TextView) view.findViewById(R.id.bookname);
        textView.setText(text);
        return view;
    }

    @Override
    public Filter getFilter() {
        if (bookFilter == null) {
            bookFilter = new BookFilter();
        }
        return bookFilter;
    }

    private class BookFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<String> filterList = new ArrayList<>();
                for (int i = 0; i < booksFilterList.size(); i++) {
                    if ((booksFilterList.get(i).toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(booksFilterList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = booksFilterList.size();
                results.values = booksFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            books = (List<String>) results.values;
            notifyDataSetChanged();
        }

    }

}
