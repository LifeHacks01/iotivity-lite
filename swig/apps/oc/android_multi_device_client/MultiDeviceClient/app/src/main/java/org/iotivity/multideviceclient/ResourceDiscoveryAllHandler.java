package org.iotivity.multideviceclient;

import android.util.Log;

import org.iotivity.OCDiscoveryAllHandler;
import org.iotivity.OCDiscoveryFlags;
import org.iotivity.OCEndpoint;
import org.iotivity.oc.OcUtils;

public class ResourceDiscoveryAllHandler implements OCDiscoveryAllHandler {

    private static final String TAG = ResourceDiscoveryAllHandler.class.getSimpleName();

    private OcfDeviceInfo deviceInfo;

    public ResourceDiscoveryAllHandler(OcfDeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    @Override
    public OCDiscoveryFlags handler(String anchor, String uri, String[] types, int interfaceMask, OCEndpoint endpoints, int resourcePropertiesMask, boolean more) {

        Log.d(TAG, "anchor: " + anchor + ", uri: " + uri);
        OcfResourceInfo resourceInfo = new OcfResourceInfo(anchor, uri, types, interfaceMask, resourcePropertiesMask);

        OCEndpoint ep = endpoints;
        while (ep != null) {
            String endpointStr = OcUtils.endpointToString(ep);
            Log.d(TAG, "\tendpoint: " + endpointStr);
            resourceInfo.addEndpoint(endpointStr);
            ep = ep.getNext();
        }

        deviceInfo.addResourceInfo(resourceInfo);

        if (!more) {
            Log.d(TAG, "----End of discovery response---");
            return OCDiscoveryFlags.OC_STOP_DISCOVERY;
        }

        return OCDiscoveryFlags.OC_CONTINUE_DISCOVERY;
    }
}