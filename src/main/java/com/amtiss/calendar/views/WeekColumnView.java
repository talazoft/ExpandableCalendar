package com.amtiss.calendar.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amtiss.calendar.R;
import com.amtiss.calendar.utils.ExpCalendarUtil;


/**
 * Created by Bigflower on 2015/12/8.
 */
public class WeekColumnView extends LinearLayout {

    private int backgroundColor = Color.rgb(105, 75, 125);
    private int startendTextColor = Color.rgb(188, 150, 211);
    private int midTextColor = Color.WHITE;

    public WeekColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public WeekColumnView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        initParams(context, attrs, defStyle);
        initLayout();
        initViews();
    }

    private void initParams(Context context, AttributeSet attrs, int defStyle) {

        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.WeekColumnView, defStyle, 0);

        backgroundColor = a.getColor(R.styleable.WeekColumnView_backgroundColor, Color.WHITE);
        startendTextColor = a.getColor(R.styleable.WeekColumnView_textColor, Color.LTGRAY);
        midTextColor = a.getColor(R.styleable.WeekColumnView_textColor, Color.LTGRAY);
    }

    public void setBackroundColor(int color){
        backgroundColor = color;
    }

    public void setTextColor(int color){
        startendTextColor = color;
        midTextColor = color;
    }

    private void initLayout() {
        this.setOrientation(HORIZONTAL);
        this.setBackgroundColor(backgroundColor);
    }


    private TextView[] textView = new TextView[7];

    private void initViews() {
        LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);

        textView[0] = new TextView(getContext());
        textView[0].setText(ExpCalendarUtil.number2Week(7));
        textView[0].setTextColor(startendTextColor);
        textView[0].setGravity(Gravity.CENTER);
        this.addView(textView[0], lp);

        for (int i = 1; i < 6; i++) {
            textView[i] = new TextView(getContext());
            textView[i].setGravity(Gravity.CENTER);
            textConfig(textView[i], ExpCalendarUtil.number2Week(i), midTextColor);
            this.addView(textView[i], lp);
        }

        textView[6] = new TextView(getContext());
        textView[6].setText(ExpCalendarUtil.number2Week(6));
        textView[6].setTextColor(startendTextColor);
        textView[6].setGravity(Gravity.CENTER);
        this.addView(textView[6], lp);
    }

    /**
     * is this nessesary ?
     *
     * @param textview
     * @param text
     * @param textColor
     */
    private void textConfig(TextView textview, String text, int textColor) {
        textview.setText(text);
        textview.setTextColor(textColor);
    }

    // TODO  public text, textColor, height, backgroundColor

}
