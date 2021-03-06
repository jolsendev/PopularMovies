package com.example.jamie.popularmovies.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by jamie on 1/9/17.
 */

//This was taken from a stack overflow thread
public class CustomListView extends ListView
{

    boolean expanded = false;

    public CustomListView(Context context)
    {
        super(context);
    }

    public CustomListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CustomListView(Context context, AttributeSet attrs,int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public boolean isExpanded()
    {
        return expanded;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (isExpanded())
        {
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);

            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();
        }
        else
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setExpanded(boolean expanded)
    {
        this.expanded = expanded;
    }
}
