package canvas.xplorer.com.doublebufferingdrawing.activities.views;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import canvas.xplorer.com.doublebufferingdrawing.activities.ImplDoublingBufferDrawing;

public class ViewMultiTouchSensorCanvas extends View {

    private static final int MAX_POINTS_TOUCH_SCREEN = 3;
    private float pointsX [] = new float[MAX_POINTS_TOUCH_SCREEN];
    private float pointsY [] = new float[MAX_POINTS_TOUCH_SCREEN];
    private float pointsLastX [] = new float[MAX_POINTS_TOUCH_SCREEN];
    private float pointsLastY [] = new float[MAX_POINTS_TOUCH_SCREEN];
    private boolean isEnableToDrawing [] = new boolean[MAX_POINTS_TOUCH_SCREEN];
    private boolean isEnableToDrawingLast [] = new boolean[MAX_POINTS_TOUCH_SCREEN];

    private ImplDoublingBufferDrawing doublingBufferDrawing;
    private Paint mPencilDrawingPath;
    private Path path [] = new Path[MAX_POINTS_TOUCH_SCREEN];

    private int mWidth; // largura da tela
    private int mHeight;   // altura da tela
    private int pointsTouchedOnScreen; // numero de pontos tocados na tela
    private int backgroundColor;

    public ViewMultiTouchSensorCanvas(Context context) {
        super(context);
    }

    public ViewMultiTouchSensorCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewMultiTouchSensorCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
