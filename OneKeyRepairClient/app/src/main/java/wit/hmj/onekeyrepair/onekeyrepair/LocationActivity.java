package wit.hmj.onekeyrepair.onekeyrepair;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;

import wit.hmj.onekeyrepair.BaseActivity;
import wit.hmj.onekeyrepair.R;

public class LocationActivity extends BaseActivity implements LocationSource, AMapLocationListener, View.OnClickListener {

    MapView mapView;
    AMap mAMap;


    private TextView addressText, titleText;
    private EditText addressEdit;

    private double latitude, longitude;

    private OnLocationChangedListener mListener;
    //声明AMapLocationClient类对象
    private AMapLocationClient mlocationClient;
    //声明定位回调监听器
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private void setUpMap() {
        mAMap.setLocationSource(this);// 设置定位监听
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(
                BitmapDescriptorFactory.fromResource(R.mipmap.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        // 自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(0);
        myLocationStyle.describeContents();
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        // 将自定义的 myLocationStyle 对象添加到地图上
        mAMap.setMyLocationStyle(myLocationStyle);
        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        mAMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        mAMap.getUiSettings().setTiltGesturesEnabled(false);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        mAMap = mapView.getMap();
        setUpMap();
        //这货就是取消地图缩放按钮的
        mAMap.getUiSettings().setZoomControlsEnabled(false);
        addressText = (TextView) findViewById(R.id.address);
        titleText = (TextView) findViewById(R.id.title);
        addressEdit = (EditText) findViewById(R.id.editText);
        titleText.setText("输入详细地址");
        findViewById(R.id.confirm).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);

    }


    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            // 设置定位监听
            mlocationClient.setLocationListener(this);
            // 设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            // 只是为了获取当前位置，所以设置为单次定位
            mLocationOption.setOnceLocation(true);
            // 设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                addressText.setText(aMapLocation.getAddress());
                mAMap.moveCamera(CameraUpdateFactory.zoomTo(19));
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": "
                        + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirm) {
            String address = addressText.getText().toString()+addressEdit.getText().toString();
            Intent intent = new Intent();
            intent.putExtra("address",address);
            setResult(102,intent);
            finish();
        } else if (v.getId() == R.id.back) {
            finish();
        }
    }
}
