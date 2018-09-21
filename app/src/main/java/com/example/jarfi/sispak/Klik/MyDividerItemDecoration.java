package com.example.jarfi.sispak.Klik;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

public class MyDividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    public  static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public  static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable drawable;
    private int mOrientation;
    private Context context;
    private int margin;

    public MyDividerItemDecoration(Context context, int orientation, int margin){
        this.context = context;
        this.margin= margin;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        drawable = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }
    public void setOrientation(int orientation){
        if(orientation !=HORIZONTAL_LIST && orientation != VERTICAL_LIST){
            throw new IllegalArgumentException("invalid orientation");

        }
        mOrientation = orientation;
    }

    public void OnDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state){
        if(mOrientation == VERTICAL_LIST){
            drawVertical(c, parent);

        }else {
            drawHorizontal(c,parent);
        }
    }
    public void drawVertical(Canvas c, RecyclerView parent){
        final  int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++){
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + drawable.getIntrinsicHeight();
            drawable.setBounds(left + dpToPx(margin), top, right - dpToPx(margin), bottom);
            drawable.draw(c);
        }
    }
    public void drawHorizontal(Canvas c, RecyclerView parent){
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++){
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + drawable.getIntrinsicHeight();
            drawable.setBounds(left,top + dpToPx(margin), right, bottom - dpToPx(margin));
            drawable.draw(c);
        }
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state){
        if(mOrientation == VERTICAL_LIST){
            outRect.set(0, 0, 0, drawable.getIntrinsicHeight());
        }else{
            outRect.set(0,0,drawable.getIntrinsicWidth(),0);
        }
    }
    private int dpToPx(int dp){
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
