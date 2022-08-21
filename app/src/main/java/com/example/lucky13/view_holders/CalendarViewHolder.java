package com.example.lucky13.view_holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lucky13.R;
import com.example.lucky13.adapter.CalendarAdapter;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public final TextView dayOfMonth;
    private final CalendarAdapter.OnItemListener onItemListener;

    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener) {
        super(itemView);

        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        this.onItemListener = onItemListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
    }
}
