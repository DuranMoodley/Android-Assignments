/*
FragStudentCheckIn.java
Retrieves student location, saves check in time and communicates with Online database
Student: Duran Moodley  Student Number: 13016335
Lecturer : Rajesh Chanderman
Assignment : 1
Date Updated : 9/4/16
 */
package attendance.student.prjstudentattendancerecord;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragStudentCheckIn extends Fragment implements LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback
{
    private ProgressBar loadingBar;
    private TextView addressText;
    private FloatingActionButton fabCheckIn;
    private FloatingActionButton fabCheckOut;
    private SharedPreferences myprefs;
    private EditText moduleCodeedt;
    private GoogleApiClient googleApiClient;
    private LocationRequest objLocationRequest;
    private Location objLocation;
    private ArrayList<Geofence> geofenceArrayList;
    private int settingsRequestCode;
    //**********************************************************************
    private void createLocationRequest()
    {
        objLocationRequest = LocationRequest.create()
                .setInterval(10000)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
   //**********************************************************************
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //Retrieves user response from location dialog
        final LocationSettingsStates settingsStates = LocationSettingsStates.fromIntent(data);
        if(requestCode == settingsRequestCode)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                //Do Additional functions in verions 2
            }
            else if(resultCode == Activity.RESULT_CANCELED)
            {
                //Do Additional functions in verions 2
            }
        }
    }
    //**********************************************************************
    public FragStudentCheckIn() {
        // Required empty public constructor
    }
    //**********************************************************************
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Only Saved the state if user is checked in
        if (myprefs.getBoolean("Check In", false))
        {
            outState.putString("Text View Data", addressText.getText().toString());
        }
    }
    //**********************************************************************
    private void setUpGoogleApiClient()
    {
        googleApiClient = new GoogleApiClient.Builder(getActivity()).addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();
    }
    //**********************************************************************
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Set the relevant widgets
        View view = inflater.inflate(R.layout.fragment_frag_student_check_in, container, false);
        myprefs = getActivity().getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        addressText = (TextView) view.findViewById(R.id.tvAddress);
        moduleCodeedt = (EditText) view.findViewById(R.id.edtModuleCode);
        loadingBar = (ProgressBar) view.findViewById(R.id.prglocation);
        fabCheckIn = (FloatingActionButton) view.findViewById(R.id.fab_location);
        fabCheckOut = (FloatingActionButton) view.findViewById(R.id.fab_endlocation);
        assert fabCheckIn != null;

        createLocationRequest();
        setUpGoogleApiClient();
        checkProviderEnabled();
        //Check if app was in the stack and user was already checked in
        if (savedInstanceState == null)
        {

            //Check in button Clicked
            fabCheckIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadingBar.setVisibility(View.VISIBLE);
                    addressText.setText("");

                    //Save the module code in shared preference and get user location
                    if (!moduleCodeedt.getText().toString().isEmpty()) {
                        SharedPreferences.Editor editor = myprefs.edit();
                        editor.putString("Module Code", moduleCodeedt.getText().toString());
                        editor.apply();

                        //Check if location settings is on
                        //Obtain user location
                        GeoCoding objGeo = new GeoCoding();
                        objGeo.execute();
                        startGeofenceMonitoring();

                    } else {
                        loadingBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(), R.string.validation_module_code, Toast.LENGTH_LONG).show();
                    }
                }
            });

            //Check out button clicked
            fabCheckOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new CompareLocation().execute();

                }
            });

            //if back button was pressed after user checked, get shared preference in OnStop
            //Check if the user was already checked in
            if (myprefs.getBoolean("Check In", false))
            {
                //Set up widgets if user is checked in and app is closed and re-opened after back button pressed
                fabCheckOut.setVisibility(View.VISIBLE);
                fabCheckIn.setVisibility(View.INVISIBLE);
                moduleCodeedt.setVisibility(View.INVISIBLE);
                addressText.setText(myprefs.getString("CheckInText", "Address"));
                Toast.makeText(getActivity(),"In Check in",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            //Set up widgets if user is checked in and app is closed and re-opened when app is in backstack
            addressText.setText(savedInstanceState.getString("Text View Data"));
            fabCheckOut.setVisibility(View.VISIBLE);
            fabCheckIn.setVisibility(View.INVISIBLE);
            moduleCodeedt.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(),"In last else",Toast.LENGTH_LONG).show();
        }
        return view;
    }
    //**********************************************************************
    private void checkProviderEnabled() {
        //Check if location settings are enabled
        LocationSettingsRequest.Builder locationSettingBuilder = new LocationSettingsRequest.Builder();
        locationSettingBuilder.addLocationRequest(objLocationRequest);

        PendingResult<LocationSettingsResult> settingResults = LocationServices.SettingsApi.checkLocationSettings(googleApiClient,
                locationSettingBuilder.build());

        settingResults.setResultCallback(new ResultCallback<LocationSettingsResult>()
        {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult)
            {
                //if it is not enabled open dialog to allow user to change settings
                //Status code will be sent to OnActivityResult override method
                settingsRequestCode = 0x1;
                final Status status = locationSettingsResult.getStatus();
                if(status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED)
                {
                    try {
                        status.startResolutionForResult(getActivity(),settingsRequestCode);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    //**********************************************************************
    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        startLocationUpdates();
    }
    //**********************************************************************
    @Override
    public void onConnectionSuspended(int i)
    {
        //Blank Method
    }
    //**********************************************************************
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), R.string.error_message, Toast.LENGTH_SHORT).show();
    }
    //**********************************************************************
    @Override
    public void onLocationChanged(Location location) {
        objLocation = location;
    }
    //**********************************************************************
    private void stopLocationUpdates()
    {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
    }
    //**************************************************************************************
    @Override
    public void onPause()
    {
        super.onPause();
        stopLocationUpdates();
    }
    //**************************************************************************************
    @Override
    public void onResume()
    {
        super.onResume();
        if(googleApiClient.isConnected())
        {
            startLocationUpdates();
        }
    }
    //**************************************************************************************
    protected class GeoCoding extends AsyncTask<Location, Void, String>
    {
        public GeoCoding() {
            super();
        }
        //**************************************************************************
        @Override
        protected String doInBackground(Location... params) {
            String myAddress = "";
            String errorMessage = "Location Not Found";
            if (Geocoder.isPresent()) {

                Geocoder objCoder = new Geocoder(getActivity());
                try {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return "";
                    }

                    //Reverse geocode location and get street address
                    if (objLocation != null) {
                        List<Address> objAddressList = objCoder.getFromLocation(objLocation.getLatitude(), objLocation.getLongitude(), 1);
                        if (objAddressList != null) {
                            for (Address addressLoc : objAddressList) {
                                int addressIndex = addressLoc.getMaxAddressLineIndex();
                                for (int count = 0; count <= addressIndex; count++) {
                                    String line = addressLoc.getAddressLine(count);
                                    myAddress += line + "\n";
                                }
                            }
                        }
                    } else {
                        return errorMessage;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                return errorMessage;
            }
            return myAddress;
        }
        //*****************************************************
        @Override
        protected void onPostExecute(String address)
        {
            //Check if address is found
            if (!address.isEmpty())
            {
                if (!address.equalsIgnoreCase("Location Not Found"))
                {
                    //Get data of the student such as check in times and store into share preference to be sent to the database after
                    //check out.
                    String date;
                    String checkInTime;
                    date = DateFormat.getDateInstance().format(new Date());
                    checkInTime = DateFormat.getTimeInstance().format(new Date());
                    fabCheckIn.setVisibility(View.GONE);
                    fabCheckOut.setVisibility(View.VISIBLE);
                    String studentInformation = "You have now Checked into \n" + address +
                            "\nPlease click the botton Right button to check out";
                    addressText.setText(studentInformation);
                    moduleCodeedt.setVisibility(View.INVISIBLE);
                    loadingBar.setVisibility(View.INVISIBLE);
                    SharedPreferences.Editor editor = myprefs.edit();
                    editor.putString("addressCheckIn",address);
                    editor.putBoolean("Check In", true);
                    editor.putString("Check In time",checkInTime);
                    editor.putString("date check in", date);
                    editor.apply();
                } else {
                    address += "\nPlease Try Again!!\n"+"Please Make Sure your Location and WIFI Settings are on";
                    addressText.setText(address);
                    loadingBar.setVisibility(View.INVISIBLE);
                }
            } else {
                address += "\nPlease Try Again!!\n"+"Please Make Sure your Location and WIFI Settings are on";
                addressText.setText(address);
                loadingBar.setVisibility(View.INVISIBLE);
            }
        }
    }
    //***********************************************************************
    protected class CompareLocation extends AsyncTask<Location, Void, String>
    {
        @Override
        protected void onPreExecute() {
            Toast.makeText(getActivity(),"Please wait...",Toast.LENGTH_LONG).show();
        }
        //********************************************************************************8
        @Override
        protected String doInBackground(Location... params)
        {
            String myAddress = "";
            String errorMessage = "Location Not Found";
            if (Geocoder.isPresent()) {

                Geocoder objCoder = new Geocoder(getActivity());
                try {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return "";
                    }

                    //Reverse geocode location and get street address
                    if (objLocation != null) {
                        List<Address> objAddressList = objCoder.getFromLocation(objLocation.getLatitude(), objLocation.getLongitude(), 1);
                        if (objAddressList != null) {
                            for (Address addressLoc : objAddressList) {
                                int addressIndex = addressLoc.getMaxAddressLineIndex();
                                for (int count = 0; count <= addressIndex; count++) {
                                    String line = addressLoc.getAddressLine(count);
                                    myAddress += line + "\n";
                                }
                            }
                        }
                    } else {
                        return errorMessage;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                return errorMessage;
            }
            return myAddress;
        }
        //**********************************************************************************
        @Override
        protected void onPostExecute(String s)
        {
            s = "Durban";
            if(!s.equalsIgnoreCase("Location Not Found"))
            {
                if(!s.equalsIgnoreCase("Durban"))
                {
                    Toast.makeText(getActivity(),"Please Return to your Check in Venue to Check out Current " +
                                                   s + "\nOld " + myprefs.getString("addressCheckIn",""),Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getActivity(), R.string.confirmation_check_out_successful, Toast.LENGTH_LONG).show();
                    fabCheckOut.setVisibility(View.GONE);
                    fabCheckIn.setVisibility(View.VISIBLE);

                    //Retrieve the revelevant information and save in shared preference
                    //Send data to Online Database
                    String checkOut = DateFormat.getTimeInstance().format(new Date());
                    addressText.setText(R.string.information_to_user);
                    moduleCodeedt.setVisibility(View.VISIBLE);
                    SharedPreferences.Editor editor = myprefs.edit();
                    editor.putString("Check Out time", checkOut);
                    editor.apply();
                    SendData objData = new SendData();
                    objData.execute();
                }
            }
        }
    }
    //**********************************************************************************
    @Override
    public void onStop() {
        //Add Notification
        super.onStop();
        googleApiClient.disconnect();
        if (myprefs.getBoolean("Check In", false)) {
            SharedPreferences.Editor neweditor = myprefs.edit();
            neweditor.putBoolean("CheckOutButton", true);
            neweditor.putString("CheckInText", addressText.getText().toString());
            neweditor.apply();
        }
    }
    //***********************************************************************
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, objLocationRequest, this);
    }
    //***********************************************************************
    private void startGeofenceMonitoring()
    {
        //Sets up the Geofence around the class room
        if(objLocation != null)
        {
            geofenceArrayList = new ArrayList<>();
            geofenceArrayList.add(new Geofence.Builder()
                    .setRequestId("myfence")
                    .setCircularRegion(objLocation.getLatitude(), objLocation.getLongitude(), 50)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setNotificationResponsiveness(1000)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build());

            GeofencingRequest geofencingRequest = new GeofencingRequest.Builder()
                    .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                    .addGeofences(geofenceArrayList).build();

            Intent intent = new Intent(getActivity(), GeofenceTransitionIntentService.class);
            PendingIntent pendingIntent = PendingIntent.getService(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (!googleApiClient.isConnected()) {
                Toast.makeText(getActivity(), "Google API Client not connected", Toast.LENGTH_LONG).show();
            } else {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                LocationServices.GeofencingApi.addGeofences(
                        googleApiClient,
                        geofencingRequest,
                        pendingIntent).
                        setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                if (!status.isSuccess()) {
                                    Toast.makeText(getActivity(),R.string.error_message, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        }
    }
    //***************************************************************
    @Override
    public void onStart()
    {
        super.onStart();
        googleApiClient.connect();
    }
   //********************************************************************************
    public class SendData extends AsyncTask<String, Void, String>
    {
        SharedPreferences myprefs;
        //********************************************************************************
        @Override
        protected String doInBackground(String... params)
        {
            //get data from share preference and save into student object
            DateFormat df = new SimpleDateFormat("hh:mm", Locale.US);
            Date dateTimeCheckIn = null;
            myprefs = getActivity().getSharedPreferences("myPreference", Context.MODE_PRIVATE);
            Student objStudent = new Student(myprefs.getString("Student Number",""),
                    myprefs.getString("Student Name",""),
                    myprefs.getString("date check in",""),
                    myprefs.getString("Module Code",""),
                    myprefs.getString("Course Code",""),
                    myprefs.getString("Check Out time",""));

            objStudent.setTimeOfCheckIn(myprefs.getString("Check In time",""));
            String line;
            String entireLine = "";
            String app_data ;
            HttpURLConnection urlConnection;
            //Create connection to the url
            try {
                URL url = new URL("http://www.duran.dx.am/saveAttendanceDetails.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                //Write the data/post which is the student number to the url
                //get data from object
                OutputStream outputStream = urlConnection.getOutputStream();
                BufferedWriter objWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                app_data = URLEncoder.encode("studNumber","UTF-8")+"="+URLEncoder.encode(objStudent.getStudNumber(),"UTF-8")+"&"+
                        URLEncoder.encode("studModuleCode","UTF-8")+"="+URLEncoder.encode(objStudent.getModuleCode(),"UTF-8")+"&"+
                        URLEncoder.encode("checkIn","UTF-8")+"="+URLEncoder.encode(String.valueOf(objStudent.getTimeOfCheckIn()),"UTF-8")+"&"+
                        URLEncoder.encode("checkOut","UTF-8")+"="+URLEncoder.encode(objStudent.getTimeOfCheckOut(),"UTF-8")+"&"+
                        URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(objStudent.getDateOfCheckin(),"UTF-8")+"&"+
                        URLEncoder.encode("CourseCode","UTF-8")+"="+URLEncoder.encode(objStudent.getCourseCode(),"UTF-8")+"&"+
                        URLEncoder.encode("StudentName","UTF-8")+"="+URLEncoder.encode(objStudent.getStudName(),"UTF-8");

                objWriter.write(app_data);
                objWriter.flush();
                objWriter.close();
                outputStream.close();

                //Retrieve the input from the url
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader objReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                while ((line = objReader.readLine()) != null) {
                    entireLine += line;
                }

                objReader.close();
                inputStream.close();
                urlConnection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return entireLine;
        }
        //********************************************************************************
        @Override
        protected void onPostExecute(String s)
        {
            SharedPreferences.Editor editor = myprefs.edit();
            editor.putString("Module Code","");
            editor.putString("Check Out time","");
            editor.putBoolean("Check In",false);
            editor.apply();
            startActivity(new Intent(getActivity(),StudentMain.class));
            getActivity().finish();
        }
    }
}
