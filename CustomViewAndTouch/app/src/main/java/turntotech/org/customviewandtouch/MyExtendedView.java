package turntotech.org.customviewandtouch;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyExtendedView extends View {

    static int touchDoneCounter = 2;

    static String DEBUG_TAG = "CUSTOM_VIEW_INFO";

    float x=0, y=0;

    float xx=0, yy=0;

    //1.1 The constructor is called first
    public MyExtendedView(Context ctx, AttributeSet attrs) {

        super(ctx, attrs);

        //1.1.1 Set the background color to black
        this.setBackgroundColor(Color.BLACK);
    }

    //1.2 This method is called before the view is drawn first, on screen rotation and when forceredraw is called
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        Paint p = new Paint();
        p.setColor(Color.YELLOW);

        //1.2 draw the circle where the touch occurs. At start, x and y are zero so the circle is drawn on top right
        canvas.drawCircle(x, y, 75f, p);

        p.setColor(Color.BLUE);
        canvas.drawCircle(xx, yy, 75f, p);


    }

    //1.3 This is called when a touch is registered
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        final int action = event.getAction();

        //1.4 Here were are just logging the kind of event we got
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(DEBUG_TAG, "Action was DOWN");
                break;

            case (MotionEvent.ACTION_MOVE):
                Log.d(DEBUG_TAG, "Action was MOVE");
                break;

            case (MotionEvent.ACTION_UP):

                Log.d(DEBUG_TAG, "Action was UP");
                break;
            default:
                return super.onTouchEvent(event);
        }
        //1.5 at this point we re-draw the circle where the touch occurred
        redrawViewWithCircle(event);

        return true;
    }

    //1.6
    public void redrawViewWithCircle(MotionEvent event) {

        //1.6.1 Get index
        //int index = event.getActionIndex();

        //1.6.2 Get coordinates for circle center. Set the instance variables.
        this.x = event.getX(0);
        this.y = event.getY(0);

        if (event.getPointerCount() == 2) {
            this.xx = event.getX(1);
            this.yy = event.getY(1);
        }

        //1.6.3 Force the view to redraw. Comment this and see what happens.
        this.postInvalidate();

    }

}
