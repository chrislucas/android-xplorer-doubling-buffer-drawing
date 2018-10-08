package canvas.xplorer.com.doublebufferingdrawing.activities.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

    protected String tag;

    public BaseFragment() {
        setTag();
    }

    public abstract void setTag();

    public String getTagFragment() {
        return tag;
    }
}
