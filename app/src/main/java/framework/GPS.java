package framework;

import android.location.Location;

/**
 * Created by BEN on 17/12/2015.
 */
public interface GPS{

    public void onLocationChanged();

    public Location getLocation();

    public double getLongitude();

    public double getLatitude();
}
