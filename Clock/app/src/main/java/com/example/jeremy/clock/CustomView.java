package com.example.jeremy.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * Created by Jeremy on 7/7/2017.
 */

public class CustomView extends ConstraintLayout {

    public int digit = 0;

    ImageView[] imageViews ;

    private void init(Context context) {
        View view = inflate(context,R.layout.custom_digit_layout,(ViewGroup)this);
        imageViews = new ImageView[7];
        imageViews[0] = (ImageView)view.findViewById(R.id.imageView1);
        imageViews[1] = (ImageView)view.findViewById(R.id.imageView2);
        imageViews[2] = (ImageView)view.findViewById(R.id.imageView3);
        imageViews[3] = (ImageView)view.findViewById(R.id.imageView4);
        imageViews[4] = (ImageView)view.findViewById(R.id.imageView5);
        imageViews[5] = (ImageView)view.findViewById(R.id.imageView6);
        imageViews[6] = (ImageView)view.findViewById(R.id.imageView7);
        for (int i = 0; i <7; i++) {
            imageViews[i].setBackgroundColor(Clock.dig);
        }
//        if (digit == 1){
//            imageViews[0].setBackgroundColor(Clock.back);
//            imageViews[1].setBackgroundColor(Clock.back);
//            imageViews[3].setBackgroundColor(Clock.back);
//            imageViews[4].setBackgroundColor(Clock.back);
//            imageViews[6].setBackgroundColor(Clock.back);
//        }
//        if (digit == 2){
//            imageViews[1].setBackgroundColor(Clock.back);
//            imageViews[5].setBackgroundColor(Clock.back);
//        }
//        if (digit == 3){
//            imageViews[1].setBackgroundColor(Clock.back);
//            imageViews[4].setBackgroundColor(Clock.back);
//        }
//        if (digit == 4){
//            imageViews[0].setBackgroundColor(Clock.back);
//            imageViews[4].setBackgroundColor(Clock.back);
//            imageViews[6].setBackgroundColor(Clock.back);
//        }
//        if (digit == 5){
//            imageViews[2].setBackgroundColor(Clock.back);
//            imageViews[4].setBackgroundColor(Clock.back);
//        }
//        if (digit == 6){
//            imageViews[2].setBackgroundColor(Clock.back);
//        }
//        if (digit == 7){
//            imageViews[1].setBackgroundColor(Clock.back);
//            imageViews[3].setBackgroundColor(Clock.back);
//            imageViews[4].setBackgroundColor(Clock.back);
//            imageViews[6].setBackgroundColor(Clock.back);
//        }
//        if (digit == 9){
//            imageViews[4].setBackgroundColor(Clock.back);
//        }
//        if (digit == 0){
//            imageViews[3].setBackgroundColor(Clock.back);
//        }
    }
    public CustomView(Context context){
        super(context);
        init(context);

    }

    public void customDigit(){
        for (int i = 0; i <7; i++) {
            imageViews[i].setBackgroundColor(Clock.dig);
            imageViews[i].setVisibility(VISIBLE);
        }
        if (digit == 1){
            imageViews[0].setVisibility(INVISIBLE);
            imageViews[1].setVisibility(INVISIBLE);
            imageViews[3].setVisibility(INVISIBLE);
            imageViews[4].setVisibility(INVISIBLE);
            imageViews[6].setVisibility(INVISIBLE);
        }
        if (digit == 2){
            imageViews[1].setVisibility(INVISIBLE);
            imageViews[5].setVisibility(INVISIBLE);
        }
        if (digit == 3){
            imageViews[1].setVisibility(INVISIBLE);
            imageViews[4].setVisibility(INVISIBLE);
        }
        if (digit == 4){
            imageViews[0].setVisibility(INVISIBLE);
            imageViews[4].setVisibility(INVISIBLE);
            imageViews[6].setVisibility(INVISIBLE);
        }
        if (digit == 5){
            imageViews[2].setVisibility(INVISIBLE);
            imageViews[4].setVisibility(INVISIBLE);
        }
        if (digit == 6){
            imageViews[2].setVisibility(INVISIBLE);
        }
        if (digit == 7){
            imageViews[1].setVisibility(INVISIBLE);
            imageViews[3].setVisibility(INVISIBLE);
            imageViews[4].setVisibility(INVISIBLE);
            imageViews[6].setVisibility(INVISIBLE);
        }
        if (digit == 9){
            imageViews[4].setVisibility(INVISIBLE);
        }
        if (digit == 0){
            imageViews[3].setVisibility(INVISIBLE);
        }
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


//    public CustomView(Context context) {
//
//        super(context);
//        View view = inflate(context,R.layout.custom_digit_layout,this);
//        imageViewOne = (ImageView)view.findViewById(R.id.imageView4);
//    }

    public void makeDigit(int digit){

    }
}
