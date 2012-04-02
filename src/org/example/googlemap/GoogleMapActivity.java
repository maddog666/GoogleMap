package org.example.googlemap;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class GoogleMapActivity extends MapActivity {
	private static final MapItemizedOverlay itemizedOverlay = null;
	private MapView mapView;
	private MapController controller;
	private MyLocationOverlay overlay;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        Log.w(getClass().getSimpleName(), "onCreate");
        setContentView(R.layout.main);
        initMapView();
        initMyLocation();
        
        List<Overlay> mapOverlays = mapView.getOverlays(); 
        Drawable drawable =
        		this.getResources().getDrawable(R.drawable.androidmarker);
        MapItemizedOverlay itemizedoverlay = 
        		new MapItemizedOverlay(drawable, mapView.getContext());
        
        GeoPoint point = new GeoPoint(
        		(int)(37 * 1E6),
        		(int)(-122 * 1E6));
        //GeoPoint point = new GeoPoint(25081469, 121561874);
        OverlayItem overlayitem = 
        		new OverlayItem(point, "§»®¦Âå°|!", "I am in Taipei!");
        
        itemizedoverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedoverlay);
        
        
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public void onDestroy(){
    	
    	overlay.disableMyLocation();
    	Log.w(getClass().getSimpleName(), "onDestroy");
    	//this.finish();
    	super.onDestroy();
    }
    
    @Override
    public void onStop(){
    	
    	overlay.disableMyLocation();
    	super.onPause();
    }
    
    @Override
    public void onResume(){
    	
    	overlay.enableMyLocation();
    	super.onResume();
    }
    
    protected boolean isRouteDisplayed(){
    	return false;
    }
    
    private void initMapView(){
    	mapView = (MapView) findViewById(R.id.mapview);
    	controller = mapView.getController();
    	mapView.setSatellite(false);
    	mapView.setBuiltInZoomControls(true);
    	
    }
    
    private void initMyLocation(){
    	overlay = new MyLocationOverlay(this, mapView);
    	overlay.enableMyLocation();
    	overlay.runOnFirstFix(new Runnable(){
    		public void run(){
    			controller.setZoom(15);
    			controller.animateTo(overlay.getMyLocation());
    		}
    	});
    	mapView.getOverlays().add(overlay);
    }
    
    
}