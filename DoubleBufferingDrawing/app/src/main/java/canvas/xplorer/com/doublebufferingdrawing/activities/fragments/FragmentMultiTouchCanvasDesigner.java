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
 * Use the {@link FragmentMultiTouchCanvasDesigner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMultiTouchCanvasDesigner extends BaseFragment {


    @Override
    public void setTag() {
        this.tag = getClass().getSimpleName();
    }

    public FragmentMultiTouchCanvasDesigner() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentMultiTouchCanvasDesigner.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMultiTouchCanvasDesigner newInstance() {
        return new FragmentMultiTouchCanvasDesigner();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multi_touch_canvas_designer, container, false);
    }

}
