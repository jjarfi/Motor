package com.example.jarfi.sispak.Klik;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecylerTouchListener implements RecyclerView.OnItemTouchListener {

    private ClickListener clickListener;
    private GestureDetector gestureDetector;

    public RecylerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener1){
        this.clickListener = clickListener1;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener1 != null) {
                    clickListener1.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }
    @Override
    public boolean onInterceptTouchEvent (RecyclerView rv, MotionEvent e){
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)){

        }
        return false;
    }
    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e){

    }
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept){

    }
    public interface ClickListener{
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }
}
