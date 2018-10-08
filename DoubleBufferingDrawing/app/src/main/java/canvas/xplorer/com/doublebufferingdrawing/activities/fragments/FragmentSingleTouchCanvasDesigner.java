package canvas.xplorer.com.doublebufferingdrawing.activities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import canvas.xplorer.com.doublebufferingdrawing.R;
import canvas.xplorer.com.doublebufferingdrawing.activities.views.SurfaceViewSingleTouchSensorCanvasDesigner;

/**
 *
 **/

public class FragmentSingleTouchCanvasDesigner extends BaseFragment {


    private SurfaceViewSingleTouchSensorCanvasDesigner surfaceView;

    @Override
    public void setTag() {
        this.tag = getClass().getSimpleName();
    }

    public FragmentSingleTouchCanvasDesigner() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentSingleTouchCanvasDesigner.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSingleTouchCanvasDesigner newInstance() {
        return  new FragmentSingleTouchCanvasDesigner();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_canvas_designer, container, false);
        surfaceView = view.findViewById(R.id.single_touch_surface_view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        surfaceView.start();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
