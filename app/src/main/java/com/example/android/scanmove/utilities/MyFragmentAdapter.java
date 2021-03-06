package com.example.android.scanmove.utilities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.example.android.scanmove.R;
import com.example.android.scanmove.activities.ExploreFragment;
import com.example.android.scanmove.activities.FavoritePlaceFragment;
import com.example.android.scanmove.activities.InterestedFragment;
import com.example.android.scanmove.activities.MainActivity;
import com.example.android.scanmove.activities.MapFragment;
import com.example.android.scanmove.activities.ScanQRFragment;

/**
 * Created by 'Chayut' on 19/10/2559.
 * This is CustomFragment Adapter that attached to {@link MainActivity}
 * ปล. Class นี้เขียนด้วย The Singleton Pattern
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {

//    @SuppressLint("StaticFieldLeak")
//    private volatile static MyFragmentAdapter mFragmentAdapterInstance;

    private int[] imageResId = {
            R.drawable.ic_qr_code,
            R.drawable.ic_fav_stroke,
            R.drawable.ic_star,
            R.drawable.ic_map,
            R.drawable.ic_compass_with_white_needles
    };

    private Context context;
    private static final int FRAGMENT_VIEW_COUNT = 5;

     public MyFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

//    public static MyFragmentAdapter getFragmentAdapterInstance(FragmentManager fm, Context context){
//        if (mFragmentAdapterInstance == null) {
//            synchronized (MyFragmentAdapter.class){
//                if (mFragmentAdapterInstance == null) {
//                    mFragmentAdapterInstance = new MyFragmentAdapter(fm, context);
//                }
//            }
//        }
//        return mFragmentAdapterInstance;
//    }


    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position The position of the Fragment to attach
     * @return A Fragment Object that refer to position
     */
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new ScanQRFragment();
            case 2 : return new InterestedFragment();
            case 3 : return new MapFragment();
            case 1 : return new FavoritePlaceFragment();
            default: return new ExploreFragment();
        }
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return FRAGMENT_VIEW_COUNT;
    }


    /**
     * This method may be called by the ViewPager to obtain a title string
     * to describe the specified page. This method may return null
     * indicating no title for this page. The default implementation returns
     * null.
     *
     * @param position The position of the title requested
     * @return A title for the requested page
     */
    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);

        // Generate title based on item position
        // return tabTitles[position];

        // getDrawable(int i) is deprecated, use getDrawable(int i, Theme theme) for min SDK >=21
        // or ContextCompat.getDrawable(Context context, int id) if you want support for older versions.
        // Drawable image = context.getResources().getDrawable(iconIds[position], context.getTheme());
        // Drawable image = context.getResources().getDrawable(imageResId[position]);

        Drawable image = ContextCompat.getDrawable(context, imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());

        String pageName;
        switch (position){
            case 0 : pageName = context.getString(R.string.scan_fragment_title); break;
            case 1 : pageName = context.getString(R.string.favorite_fragment_title); break;
            case 2 : pageName = context.getString(R.string.interested_fragment_title); break;
            case 3 : pageName = context.getString(R.string.map_fragment_title); break;
            default: pageName = context.getString(R.string.explore_fragment_title);
        }

        SpannableString TabTitle = new SpannableString(" \n" + pageName) ;

        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        TabTitle.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return TabTitle;
    }
}
