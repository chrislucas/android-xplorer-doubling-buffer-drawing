package canvas.xplorer.com.doublebufferingdrawing.activities.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import canvas.xplorer.com.doublebufferingdrawing.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSingleTouchCanvasDesignerOnView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSingleTouchCanvasDesignerOnView extends BaseFragment {

    @Override
    public void setTag() {
        tag = getClass().getSimpleName();
    }

    public FragmentSingleTouchCanvasDesignerOnView() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentSingleTouchCanvasDesignerOnView newInstance() {
        return new FragmentSingleTouchCanvasDesignerOnView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_touch_canvas_designer_on_view, container, false);
    }

}
