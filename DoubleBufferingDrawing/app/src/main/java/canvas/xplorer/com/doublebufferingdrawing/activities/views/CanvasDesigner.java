package canvas.xplorer.com.doublebufferingdrawing.activities.views;

import android.content.Context;
import android.graphics.Paint;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import canvas.xplorer.com.doublebufferingdrawing.activities.ImplDoublingBufferDrawing;

public class CanvasDesigner extends View {
    

    private Paint mPaint;

    private int mWidth, mHeight;

    private ImplDoublingBufferDrawing implDoublingBufferDrawing;

    public CanvasDesigner(Context context) {
        super(context);
        initializePaint();
    }

    public CanvasDesigner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializePaint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        this.mHeight = MeasureSpec.getSize(heightMeasureSpec);
        initializeDoublingBufferDrawer();
    }

    private void initializePaint() {
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15.0f);
        /**
         * Paint.Join enum que indica como segmentos de linhas sao juntados
         * quando desenhamos um Path. Paint.Join.BEVEL indica que o tratamento
         * sera juntar as segmentos usando postas retas.
         * */
        mPaint.setStrokeJoin(Paint.Join.BEVEL);

        /**
         * Paint.Cap e um enum que especifca como as extremidades de um segmento
         * de linha serao desenhados
         *
         * */
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
    }

    private void initializeDoublingBufferDrawer() {
        implDoublingBufferDrawing = new ImplDoublingBufferDrawing(mPaint, mWidth, mHeight);
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
