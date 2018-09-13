package wit.hmj.onekeyrepair;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zaaach.citypicker.CityPickerActivity;

import java.util.ArrayList;
import java.util.List;

import wit.hmj.onekeyrepair.classificationrepair.ClassificationRepairActivity;
import wit.hmj.onekeyrepair.my.LoginActivity;
import wit.hmj.onekeyrepair.my.MyActivity;
import wit.hmj.onekeyrepair.onekeyrepair.LocationActivity;
import wit.hmj.onekeyrepair.onekeyrepair.OneKeyRepairActivity;

public class MainActivity extends BaseActivity implements View.OnTouchListener, View.OnClickListener {
    private LinearLayout onekeyrepair, my, classificationrepair;
    private ScaleAnimation animation;
    private Button location, feedBack;
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private List<RadioButton> radioButtons = new ArrayList<>();//保存所有的radiobutton
    private int images[];
    private static final int REQUEST_CODE_PICK_CITY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        onekeyrepair = (LinearLayout) findViewById(R.id.OneKeyRepair);
        my = (LinearLayout) findViewById(R.id.my);
        classificationrepair = (LinearLayout) findViewById(R.id.classificationRepair);
        onekeyrepair.setOnTouchListener(this);
        my.setOnTouchListener(this);
        classificationrepair.setOnTouchListener(this);
        location = (Button) findViewById(R.id.location);
        feedBack = (Button) findViewById(R.id.feedBack);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        location.setOnClickListener(this);
        feedBack.setOnClickListener(this);
        onekeyrepair.setOnClickListener(this);
        classificationrepair.setOnClickListener(this);
        my.setOnClickListener(this);
        addImage();

        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return images.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setImageResource(images[position]);
                container.addView(imageView);
                return imageView;
            }
        });

        addRadioButton();
        openThread();
        viewPager.setCurrentItem(0);
        viewPager.setPageTransformer(true, new AlphaPageTranSformer());
        viewPager.setOffscreenPageLimit(3);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setRadioButtonChecked(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addImage() {
        images = new int[3];
        images[0] = R.drawable.viewpager2;
        images[1] = R.drawable.viewpager7;
        images[2] = R.drawable.viewpager8;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            animation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(100);
            animation.setFillAfter(true);
            v.startAnimation(animation);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            animation = new ScaleAnimation(0.9f, 1.0f, 0.9f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(100);
            animation.setFillAfter(true);
            v.startAnimation(animation);
        }
        return false;
    }

    private void openThread() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (viewPager.getCurrentItem() == images.length - 1) {
                                viewPager.setCurrentItem(0);
                            } else {
                                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                            }
                        }
                    });

                }
            }
        }.start();
    }

    private void addRadioButton() {
        radioGroup.removeAllViews();
        radioButtons.clear();
        radioGroup.removeAllViews();
        for (int i = 0; i < images.length; i++) {
            RadioButton btn = new RadioButton(this);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 5);
            btn.setLayoutParams(params);
            btn.setEnabled(false);
            setRadioButtonChecked(i);
            radioGroup.addView(btn);
            radioButtons.add(btn);
        }
        setRadioButtonChecked(0);
    }

    private void setRadioButtonChecked(int i) {
        for (int j = 0; j < radioButtons.size(); j++) {
            if (j == i) {
                radioButtons.get(j).setButtonDrawable(getResources().getDrawable(R.mipmap.image_indicator_focus));
            } else {
                radioButtons.get(j).setButtonDrawable(getResources().getDrawable(R.mipmap.image_indicator));
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location:
                //位置信息展示
                //启动
                startActivityForResult(new Intent(MainActivity.this, CityPickerActivity.class),
                        REQUEST_CODE_PICK_CITY);
                break;
            case R.id.classificationRepair:
                startActivity(new Intent(this, ClassificationRepairActivity.class));
                break;
            case R.id.OneKeyRepair:
                if (!LOGIN) {
                    startActivityForResult(new Intent(this, LoginActivity.class), 110);
                } else {
                    startActivity(new Intent(this, OneKeyRepairActivity.class));
                }
                break;
            case R.id.my:
                startActivity(new Intent(this, MyActivity.class));
                break;
            case R.id.feedBack:
                if (!LOGIN) {
                    startActivityForResult(new Intent(this, LoginActivity.class), 110);
                } else {
                    startActivity(new Intent(MainActivity.this, FeedBackActivity.class));
                }
                break;
        }
    }

    //重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                location.setText(city);
            }
        }
    }


    //viewPager透明效果
    class AlphaPageTranSformer implements ViewPager.PageTransformer {

        private static final float DEFAULT_MIN_ALPHA = 0.3f;
        private float mMinAlpha = DEFAULT_MIN_ALPHA;

        @Override
        public void transformPage(View page, float position) {
            if (position < -1) {
                page.setAlpha(mMinAlpha);
            } else if (position <= 1) {
                if (position < 0) {
                    float factor = mMinAlpha + (1 - mMinAlpha) * (1 + position);
                    page.setAlpha(factor);
                } else {
                    float factor = mMinAlpha + (1 - mMinAlpha) * (1 - position);
                    page.setAlpha(factor);
                    page.setAlpha(factor);
                }
            } else {
                page.setAlpha(mMinAlpha);
            }
        }
    }

}
