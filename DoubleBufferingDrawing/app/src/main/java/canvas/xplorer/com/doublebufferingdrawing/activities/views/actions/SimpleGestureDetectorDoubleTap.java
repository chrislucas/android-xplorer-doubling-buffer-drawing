package canvas.xplorer.com.doublebufferingdrawing.activities.views.actions;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by r028367 on 12/01/2018.
 */

public class SimpleGestureDetectorDoubleTap extends GestureDetector.SimpleOnGestureListener {

    public interface OnDoubleTapListener {
        boolean onDoubleTap(MotionEvent event);
        boolean onDoubleTapEvent(MotionEvent event);
        boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);
        boolean onSingleTapUp(MotionEvent e);
        boolean onSingleTapConfirmed(MotionEvent e);
    }

    private OnDoubleTapListener onDoubleTapListener;

    public SimpleGestureDetectorDoubleTap(OnDoubleTapListener onDoubleTapListener) {
        this.onDoubleTapListener = onDoubleTapListener;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return onDoubleTapListener.onSingleTapConfirmed(e);;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return onDoubleTapListener.onDoubleTap(e);
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return onDoubleTapListener.onDoubleTapEvent();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return onDoubleTapListener.onFling(e1, e2, velocityX, velocityY);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return onDoubleTapListener.onSingleTapUp(e);
    }
}
