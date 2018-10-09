package canvas.xplorer.com.doublebufferingdrawing.activities.views.old;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import canvas.xplorer.com.doublebufferingdrawing.activities.ImplDoublingBufferDrawing;
import canvas.xplorer.com.doublebufferingdrawing.activities.views.actions.SimpleGestureDetectorDoubleTap;

public class SingleTouchCanvasDesigner extends View
        implements SimpleGestureDetectorDoubleTap.OnDoubleTapListener {
    

    private Paint mPaint;
    private Matrix mMatrixIndentity;

    private int backgroundColor;
    private int mWidth, mHeight;

    private ImplDoublingBufferDrawing implDoublingBufferDrawing;

    public SingleTouchCanvasDesigner(Context context) {
        super(context);
        initializer();
    }

    public SingleTouchCanvasDesigner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializer();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        this.mHeight = MeasureSpec.getSize(heightMeasureSpec);
        initializeDoublingBufferDrawer();
    }

    private void initializer() {
        backgroundColor = getBackground() == null ? Color.WHITE : ((ColorDrawable) getBackground()).getColor();
        initializePaint();
        mMatrixIndentity = new Matrix();

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                GestureDetector gestureDetector =
                        new GestureDetector(getContext()
                                , new SimpleGestureDetectorDoubleTap(SingleTouchCanvasDesigner.this));
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
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
        implDoublingBufferDrawing = new ImplDoublingBufferDrawing(mWidth, mHeight);
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        String message = "";
        switch (actionMasked) {
            case MotionEvent.ACTION_UP:
                message = "ONTOUCH - ACTION_UP";
                break;
            case MotionEvent.ACTION_DOWN:
                message = "ONTOUCH - ACTION_DOWN";
                break;
        }
        showToastMessage(message);
        return true;
    }


    private void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * {@link SimpleGestureDetectorDoubleTap.OnDoubleTapListener}
     * */
    @Override
    public boolean onDoubleTap(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        String message = "";
        switch (actionMasked) {
            case MotionEvent.ACTION_UP:
                message = "ONDOUBLETAP - ACTION_UP";
                break;
            case MotionEvent.ACTION_DOWN:
                message = "ONDOUBLETAP - ACTION_DOWN";
                break;
        }
        showToastMessage(message);
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        String message = "";
        switch (actionMasked) {
            case MotionEvent.ACTION_UP:
                message = "SINGLE_T_CONF - ACTION_UP";
                break;
            case MotionEvent.ACTION_DOWN:
                message = "SINGLE_T_CONF - ACTION_DOWN";
                break;
        }
        showToastMessage(message);
        return true;
    }

    /**
     * {@link SimpleGestureDetectorDoubleTap.OnDoubleTapListener}
     * */
    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        int actionMasked = event.getActionMasked();
        String message = "";
        switch (actionMasked) {
            case MotionEvent.ACTION_UP:
                message = "ONDOUBLETAPEVENT - ACTION_UP";
                break;
            case MotionEvent.ACTION_DOWN:
                message = "ONDOUBLETAPEVENT - ACTION_DOWN";
                break;
        }
        showToastMessage(message);
        return true;
    }

    /**
     * {@link SimpleGestureDetectorDoubleTap.OnDoubleTapListener}
     * */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    /**
     * {@link SimpleGestureDetectorDoubleTap.OnDoubleTapListener}
     * */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
}
