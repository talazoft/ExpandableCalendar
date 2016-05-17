package com.amtiss.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amtiss.calendar.listeners.OnExpDateClickListener;
import com.amtiss.calendar.listeners.OnMonthScrollListener;
import com.amtiss.calendar.views.ExpCalendarView;
import com.amtiss.calendar.views.WeekColumnView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Rizka Dwitama on 4/29/16.
 */
public class ExpandableCalendarView extends LinearLayout {

    private boolean isExpanded = true;

    private TextView selectedDate;
    private ExpCalendarView calendarView;
    private WeekColumnView weekColumn;
    private ImageView toggleExpand;

    private SwipeListener swipeListener;
    private DateSelectedListener dateSelectedListener;
    private ExpandListener expandListener;

    public ExpandableCalendarView(Context context, AttributeSet attr) {
        super(context, attr);
        init(context, attr, 0);
    }

    public ExpandableCalendarView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        init(context, attr, defStyle);
    }

    public ExpandableCalendarView setSwipeListener(SwipeListener listener){
        swipeListener = listener;
        return this;
    }

    public ExpandableCalendarView setOnDateSelectedListener(DateSelectedListener listener){
        dateSelectedListener = listener;
        return this;
    }

    public ExpandableCalendarView setOnExpandListener(ExpandListener listener){
        expandListener = listener;
        return this;
    }

    private void init(Context context, AttributeSet attrs, int defStyle){
        inflate(context, R.layout.expandable_calendar_view, this);

        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ExpandableCalendarView, defStyle, 0);

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MMMM");
        selectedDate = (TextView) findViewById(R.id.selectedDate);
        Calendar calendar = Calendar.getInstance();
        selectedDate.setText(calendar.get(Calendar.YEAR)+" "+calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
        selectedDate.setBackgroundColor(a.getColor(R.styleable.ExpandableCalendarView_infoBackgroundColor, Color.WHITE));
        selectedDate.setTextColor(a.getColor(R.styleable.ExpandableCalendarView_infoTextColor, Color.LTGRAY));

        weekColumn = (WeekColumnView)findViewById(R.id.weekColumn);
        weekColumn.setBackroundColor(a.getColor(R.styleable.ExpandableCalendarView_weekBackgroundColor, Color.WHITE));
        weekColumn.setTextColor(a.getColor(R.styleable.ExpandableCalendarView_weekTextColor, Color.LTGRAY));

        calendarView = (ExpCalendarView)findViewById(R.id.calendarView);
        calendarView.setOnMonthScrollListener(new OnMonthScrollListener() {
            @Override
            public void onMonthChange(Date date) {
                selectedDate.setText(dateFormat.format(date.getTime()));

                if(swipeListener != null){
                    swipeListener.onPageSwipe(date);
                }
            }

            @Override
            public void onMonthScroll(float positionOffset) {
                if(swipeListener != null){
                    swipeListener.onPageScrolled(positionOffset);
                }
            }
        });
        calendarView.setOnDateClickListener(new OnExpDateClickListener() {
            @Override
            public void onDateClicked(Date date) {
                selectedDate.setText(dateFormat.format(date.getTime()));
                if(dateSelectedListener != null){
                    dateSelectedListener.onDateSelected(date);
                }
            }
        });
        calendarView.setBackgroundColor(a.getColor(R.styleable.ExpandableCalendarView_calendarBackgroundColor, Color.WHITE));

        toggleExpand = (ImageView)findViewById(R.id.toggleExpand);
        toggleExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    CellConfig.Month2WeekPos = CellConfig.middlePosition ;
                    CellConfig.ifMonth = false ;
                    toggleExpand.setImageResource(R.drawable.ic_action_down);
                    calendarView.shrink();
                } else {
                    CellConfig.Week2MonthPos = CellConfig.middlePosition ;
                    CellConfig.ifMonth = true ;
                    toggleExpand.setImageResource(R.drawable.ic_action_up);
                    calendarView.expand();
                }
                isExpanded = !isExpanded;

                if(expandListener != null){
                    expandListener.onCalendarExpanded(isExpanded);
                }
            }
        });
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }

    public interface SwipeListener{
        void onPageScrolled(float positionOffset);
        void onPageSwipe(Date date);
    }

    public interface DateSelectedListener{
        void onDateSelected(Date date);
    }

    public interface ExpandListener{
        void onCalendarExpanded(boolean isExpanded);
    }

    public void markDate(int year, int month, int day){
        calendarView.markDate(year, month, day);
    }

    public void markDate(Date date){
        if(date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DATE);
            calendarView.markDate(year, month, day);
        }
    }
}
