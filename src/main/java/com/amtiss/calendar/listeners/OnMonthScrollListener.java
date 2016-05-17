package com.amtiss.calendar.listeners;

import android.support.v4.view.ViewPager;

import com.amtiss.calendar.CellConfig;
import com.amtiss.calendar.utils.ExpCalendarUtil;
import com.amtiss.calendar.vo.DateData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Bigflower on 2015/12/8.
 *
 * add a onMonthScroll . the aim is for cool effect
 */
public abstract class OnMonthScrollListener implements ViewPager.OnPageChangeListener {

    private Date date;

    public Date getDate(){
        return date;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        onMonthScroll(positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
        CellConfig.middlePosition = position;
        DateData date;
        if (CellConfig.ifMonth)
            date = ExpCalendarUtil.position2Month(position);
        else
            date = ExpCalendarUtil.position2Week(position);

        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        String strDate = date.getMonthString()+"-"+date.getDayString()+"-"+date.getYear();
        try {
            Date d = format.parse(strDate);
            this.date = d;
            onMonthChange(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public abstract void onMonthChange(Date date);

    public abstract void onMonthScroll(float positionOffset);
}
