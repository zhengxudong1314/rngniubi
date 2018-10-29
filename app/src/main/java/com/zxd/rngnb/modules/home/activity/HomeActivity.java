package com.zxd.rngnb.modules.home.activity;

import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zxd.rngnb.MyApplication;
import com.zxd.rngnb.R;
import com.zxd.rngnb.base.activity.BaseActivity;
import com.zxd.rngnb.base.presenter.IBasePresenter;
import com.zxd.rngnb.modules.home.fragment.HomeFragment;
import com.zxd.rngnb.modules.hot.fragment.HotFragment;
import com.zxd.rngnb.modules.me.fragment.MeFragment;
import com.zxd.rngnb.utils.UIUtils;
import com.zxd.rngnb.widget.BottomNavigationViewEx;

import butterknife.BindView;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.navigation)
    BottomNavigationViewEx navigation;

    private Fragment currentFragment;
    private boolean mFirst;
    private ColorStateList colorStateList;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setCurrentFragment(HomeFragment.class);
                    navigation.setIconTintList(0, colorStateList);
                    return true;
                case R.id.navigation_dashboard:
                    setCurrentFragment(HotFragment.class);
                    navigation.setIconTintList(1, colorStateList);
                    return true;
                case R.id.navigation_notifications:
                    setCurrentFragment(MeFragment.class);
                    navigation.setIconTintList(2, colorStateList);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void doSomething() {
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        BottomNavigationViewHelper.disableShiftMode(navigation);
        //设置默认选择
        mFirst = true;
        navigation.setSelectedItemId(R.id.navigation_home);
        //设置底部导航文字和Icon颜色
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };
        int[] colors = new int[]{getResources().getColor(R.color.colorText),
                getResources().getColor(R.color.colorAccent)
        };
        colorStateList = new ColorStateList(states, colors);
        navigation.setItemTextColor(colorStateList);
        navigation.setIconTintList(0, colorStateList);
    }

    @Override
    protected IBasePresenter initPresenter() {
        return null;
    }

    public void reset() {
        mFirst = true;
        MyApplication.getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (navigation != null) {
                    navigation.setSelectedItemId(R.id.navigation_home);
                }
            }
        }, 100);
    }

    public void setCurrentFragment(Class<? extends Fragment> clazz) {
        if (clazz == null) {
            return;
        }
        FragmentManager fm = getSupportFragmentManager();
        if (mFirst) {
            mFirst = false;
            FragmentTransaction ft = fm.beginTransaction();
            Fragment fragment = null;
            try {
                fragment = clazz.newInstance();
            } catch (Exception e) {
                UIUtils.catchCrash(e);
            }
            ft.replace(R.id.fl_container, fragment, clazz.getSimpleName()).commitAllowingStateLoss();
            currentFragment = fragment;
            return;
        }
        FragmentTransaction ft = fm.beginTransaction();

        Fragment fragment = fm.findFragmentByTag(clazz.getSimpleName());

        if (currentFragment != null) {
            if (fragment == currentFragment) {
                return;
            } else {
                ft.hide(currentFragment);
            }
        }

        if (fragment != null) {
            ft.show(fragment);
        } else {
            try {
                fragment = clazz.newInstance();
                ft.add(R.id.fl_container, fragment, clazz.getSimpleName());
            } catch (Exception e) {
                UIUtils.catchCrash(e);
            }
        }

        ft.commit();

        currentFragment = fragment;
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - exitTime > 2000) {
            ToastUtils.showShort(getString(R.string.exit_app));
            exitTime = secondTime;
        } else {
//            System.exit(0);
            ActivityUtils.finishAllActivities();
        }
    }
}
