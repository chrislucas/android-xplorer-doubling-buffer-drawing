package canvas.xplorer.com.doublebufferingdrawing.activities.views;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;

public class SurfaceViewMultiTouchSensorCanvasDesginer extends SurfaceView {

    public SurfaceViewMultiTouchSensorCanvasDesginer(Context context) {
        super(context);
    }

    public SurfaceViewMultiTouchSensorCanvasDesginer(Context context, @Nullable AttributeSet attrs) {
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
