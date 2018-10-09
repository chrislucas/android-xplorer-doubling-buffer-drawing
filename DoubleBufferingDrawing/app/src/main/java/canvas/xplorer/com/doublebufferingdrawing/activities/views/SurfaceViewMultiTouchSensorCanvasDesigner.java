package canvas.xplorer.com.doublebufferingdrawing.activities.views;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class SurfaceViewMultiTouchSensorCanvasDesigner extends SurfaceView {

    public SurfaceViewMultiTouchSensorCanvasDesigner(Context context) {
        super(context);
    }

    public SurfaceViewMultiTouchSensorCanvasDesigner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }
}
