package canvas.xplorer.com.doublebufferingdrawing.activities.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import canvas.xplorer.com.doublebufferingdrawing.R;
import canvas.xplorer.com.doublebufferingdrawing.activities.fragments.BaseFragment;
import canvas.xplorer.com.doublebufferingdrawing.activities.fragments.FragmentDrawRandomDotsSample;

public class ActivityDrawRandomDots extends AppCompatActivity {

    private final FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_random_dots);
        BaseFragment baseFragment = FragmentDrawRandomDotsSample.newInstance();
        if (fm.findFragmentByTag(baseFragment.getTagFragment()) == null) {
            fm.beginTransaction()
                    .replace(R.id.layout_replace, baseFragment, baseFragment.getTagFragment())
                    .addToBackStack(baseFragment.getTagFragment())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (fm != null && fm.getBackStackEntryCount() == 0) {
            moveTaskToBack(true);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
