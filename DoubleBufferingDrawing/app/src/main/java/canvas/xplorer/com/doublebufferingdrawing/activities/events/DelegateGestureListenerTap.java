package canvas.xplorer.com.doublebufferingdrawing.activities.events;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class DelegateGestureListenerTap extends GestureDetector.SimpleOnGestureListener {

    private IDetectTap detectTap;

    public DelegateGestureListenerTap(IDetectTap detectTap) {
        this.detectTap = detectTap;
    }

    public void setDetectTap(IDetectTap detectTap) {
        this.detectTap = detectTap;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return super.onSingleTapUp(e);
    }

    @Override
    public void onLongPress(MotionEvent e) {
        detectTap.onLongPress(e);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return detectTap.onScroll(e1, e2, distanceX, distanceY);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return detectTap.onFling(e1, e2, velocityX, velocityY);
    }

    @Override
    public void onShowPress(MotionEvent e) {
        detectTap.onShowPress(e);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return detectTap.onDown(e);
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return detectTap.onDoubleTap(e);
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return detectTap.onDoubleTapEvent(e);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return detectTap.onSingleTapConfirmed(e);
    }

    @Override
    public boolean onContextClick(MotionEvent e) {
        return detectTap.onContextClick(e);
    }
}
