package com.amtiss.calendar.listeners;

import android.view.View;

import com.amtiss.calendar.utils.CurrentCalendar;
import com.amtiss.calendar.views.DefaultCellView;
import com.amtiss.calendar.vo.DateData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Bigflower on 2015/12/10.
 * <p>
 * 分别要对上次的和这次的处理
 * 而今日和其他日也有区别 所以有两个判断
 * 1.对上次的点击判断
 * 2.对这次的点击判断
 */
public abstract class OnExpDateClickListener extends OnDateClickListener {

    private View lastClickedView;
    private DateData lastClickedDate = CurrentCalendar.getCurrentDateData();

    @Override
    public void onDateClick(View view, DateData date) {

        if(view instanceof DefaultCellView) {

            // 判断上次的点击
            if (lastClickedView != null) {
                // 节约！
                if (lastClickedView == view)
                    return;
                if (lastClickedDate.equals(CurrentCalendar.getCurrentDateData())) {
                    ((DefaultCellView) lastClickedView).setDateToday();
                } else {
                    ((DefaultCellView) lastClickedView).setDateNormal();
                }
            }
            // 判断这次的点击
            ((DefaultCellView) view).setDateChoose();
            lastClickedView = view;
            lastClickedDate = date;

            SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
            String strDate = date.getMonthString()+"-"+date.getDayString()+"-"+date.getYear();
            try {
                Date d = format.parse(strDate);
                onDateClicked(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void onDateClicked(Date date);
}
