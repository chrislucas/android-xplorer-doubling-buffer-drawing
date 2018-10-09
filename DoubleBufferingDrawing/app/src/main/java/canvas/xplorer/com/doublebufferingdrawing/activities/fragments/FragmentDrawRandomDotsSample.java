package canvas.xplorer.com.doublebufferingdrawing.activities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.TimeUnit;

import canvas.xplorer.com.doublebufferingdrawing.R;
import canvas.xplorer.com.doublebufferingdrawing.activities.views.SurfaceViewAutomaticDrawDots;


public class FragmentDrawRandomDotsSample extends BaseFragment {


    private SurfaceViewAutomaticDrawDots surfaceViewDrawDots;

    @Override
    public void setTag() {
        tag = getClass().getSimpleName();
    }

    public FragmentDrawRandomDotsSample() {
        // Required empty public constructor
    }


    public static FragmentDrawRandomDotsSample newInstance() {

        return new FragmentDrawRandomDotsSample();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_draw_random_dots_sample, container, false);
        surfaceViewDrawDots = view.findViewById(R.id.surface_to_draw);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        surfaceViewDrawDots.start(20000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("ON_PAUSE", "STOP_DRAWING");
        surfaceViewDrawDots.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("ON_DESTROY", "STOP_DRAWING");
        surfaceViewDrawDots.stop();
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
