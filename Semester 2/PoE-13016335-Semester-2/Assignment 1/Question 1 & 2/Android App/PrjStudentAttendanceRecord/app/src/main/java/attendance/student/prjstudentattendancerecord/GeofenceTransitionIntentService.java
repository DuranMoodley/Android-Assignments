package attendance.student.prjstudentattendancerecord;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class GeofenceTransitionIntentService extends IntentService
{
    public GeofenceTransitionIntentService()
    {
        super("GeofenceTransitionIS");
    }
    //*********************************************************
    @Override
    protected void onHandleIntent(Intent intent)
    {
        String transitionType;
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if(geofencingEvent.hasError()){
            Log.e("OnHandleIntent","" + geofencingEvent.getErrorCode());
        }
        else
        {
            int transition = geofencingEvent.getGeofenceTransition();

            if(transition == Geofence.GEOFENCE_TRANSITION_ENTER)
            {
                transitionType = "You are in your Lecture Venue. Enjoy !!!";
                sendNotification(transitionType);
            }
            else if(transition == Geofence.GEOFENCE_TRANSITION_EXIT)
            {
                transitionType = "You have left your Lecture Venue. Please Return to your venue to Check Out.";
                sendNotification(transitionType);
            }
        }
    }
    //******************************************************************************************
    private void sendNotification(String notificationDescription)
    {
        Toast.makeText(getApplicationContext(),notificationDescription,Toast.LENGTH_SHORT).show();
    }
}
