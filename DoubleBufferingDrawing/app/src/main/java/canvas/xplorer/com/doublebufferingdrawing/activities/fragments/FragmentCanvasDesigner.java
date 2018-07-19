package canvas.xplorer.com.doublebufferingdrawing.activities.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import canvas.xplorer.com.doublebufferingdrawing.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentCanvasDesigner.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentCanvasDesigner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCanvasDesigner extends Fragment {



    public FragmentCanvasDesigner() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCanvasDesigner.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCanvasDesigner newInstance() {
        FragmentCanvasDesigner fragment = new FragmentCanvasDesigner();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_canvas_designer, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
