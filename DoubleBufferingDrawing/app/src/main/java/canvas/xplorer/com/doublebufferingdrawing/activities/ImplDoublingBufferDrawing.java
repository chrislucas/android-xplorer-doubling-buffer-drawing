package canvas.xplorer.com.doublebufferingdrawing.activities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class ImplDoublingBufferDrawing {

    private Bitmap mBitmapCache;

    private Canvas mCanvasCache;

    private Paint mPaint;

    private int mWidth, mHeight;

    private final RectF mRectCanvas;

    public ImplDoublingBufferDrawing(Paint mPaint, int mWidth, int mHeight) {
        this.mPaint = mPaint;
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        this.mRectCanvas = new RectF(0,0, mWidth, mHeight);
        this.mBitmapCache = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
    }

    public Bitmap getmBitmapCache() {
        return mBitmapCache;
    }

    public Canvas getmCanvasCache() {
        return mCanvasCache;
    }

    public Paint getmPaint() {
        return mPaint;
    }

    public int getmWidth() {
        return mWidth;
    }

    public int getmHeight() {
        return mHeight;
    }

    public RectF getmRectCanvas() {
        return mRectCanvas;
    }
}
