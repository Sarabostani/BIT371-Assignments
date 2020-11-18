package com.example.sqliteexample;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EventCursorAdapater extends CursorAdapter {
    public EventCursorAdapater(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView titleText = view.findViewById(R.id.event_title_text);
        TextView dateText = view.findViewById(R.id.event_date_text);
        ImageView img = view.findViewById(R.id.event_img_holder);

        titleText.setText(cursor.getString(cursor.getColumnIndex(DBHelper.TITLE_COL)));
        dateText.setText(cursor.getString(cursor.getColumnIndex(DBHelper.DATE_COL)));
        img.setImageResource(cursor.getInt(cursor.getColumnIndex(DBHelper.IMAGE_ID_COL)));
    }
}
