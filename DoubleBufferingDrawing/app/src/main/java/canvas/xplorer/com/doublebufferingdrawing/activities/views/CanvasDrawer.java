package canvas.xplorer.com.doublebufferingdrawing.activities.views;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CanvasDrawer extends View {
    public CanvasDrawer(Context context) {
        super(context);
    }

    public CanvasDrawer(Context context, @Nullable AttributeSet attrs) {
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

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
