package canvas.xplorer.com.doublebufferingdrawing.activities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

public class ImplDoublingBufferDrawing {

    private Bitmap mBitmapCache;

    private Canvas mCanvasCache;

    private int mWidth, mHeight;

    private final RectF mRectCanvas;

    private Matrix identity;

    public ImplDoublingBufferDrawing(int mWidth, int mHeight) {
        this.mWidth         = mWidth;
        this.mHeight        = mHeight;
        this.mRectCanvas    = new RectF(0,0, mWidth, mHeight);
        this.mBitmapCache   = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        this.mCanvasCache   = new Canvas();
        this.mCanvasCache.setBitmap(mBitmapCache);
        this.identity       = new Matrix();
    }

    public Bitmap getBitmapCache() {
        return mBitmapCache;
    }

    public Canvas getCanvasCache() {
        return mCanvasCache;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int mWidth) { this.mWidth = mWidth; }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int mHeight) { this.mHeight = mHeight; }

    public RectF getRectCanvas() { return mRectCanvas; }

    public Matrix getIdentity() { return identity; }
}
