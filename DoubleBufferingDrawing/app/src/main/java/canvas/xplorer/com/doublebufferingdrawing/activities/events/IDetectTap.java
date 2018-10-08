package canvas.xplorer.com.doublebufferingdrawing.activities.events;

import android.view.MotionEvent;

public interface IDetectTap {
    boolean onSingleTapUp(MotionEvent e);
    void onLongPress(MotionEvent e);
    boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);
    boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);
    void onShowPress(MotionEvent e);
    boolean onDown(MotionEvent e);
    boolean onDoubleTap(MotionEvent e);
    boolean onDoubleTapEvent(MotionEvent e);
    boolean onSingleTapConfirmed(MotionEvent e);
    boolean onContextClick(MotionEvent e);
}
