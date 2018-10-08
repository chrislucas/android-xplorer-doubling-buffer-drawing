package canvas.xplorer.com.doublebufferingdrawing.activities.events;

import android.view.MotionEvent;
import android.widget.Toast;

import canvas.xplorer.com.doublebufferingdrawing.activities.views.SurfaceViewSingleTouchSensorCanvasDesigner;

public class SingleTouchSensorDetectTap implements IDetectTap {


    private SurfaceViewSingleTouchSensorCanvasDesigner singleTouch;

    public SingleTouchSensorDetectTap(SurfaceViewSingleTouchSensorCanvasDesigner singleTouch) {
        this.singleTouch = singleTouch;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        int action = e.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int action1 = e1.getActionMasked();
        switch (action1) {
            case MotionEvent.ACTION_UP:
                break;
        }

        int action2 = e2.getAction();
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        int action = e.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_UP:
                break;
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        int action = e.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        int action = e.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_UP:
                break;
        }
        Toast.makeText(singleTouch.getContext(), e.getAction(), Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        int action = e.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        int action = e.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public boolean onContextClick(MotionEvent e) {
        int action = e.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
