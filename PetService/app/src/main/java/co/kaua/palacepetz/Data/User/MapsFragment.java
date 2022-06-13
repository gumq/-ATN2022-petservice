package co.kaua.palacepetz.Data.User;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.PolyUtil;
import co.kaua.palacepetz.Activitys.APIData.DataParser;
import co.kaua.palacepetz.Activitys.APIData.GetDirections;
import co.kaua.palacepetz.Adapters.NearbyPlacesAdapter;
import co.kaua.palacepetz.Adapters.SearchAutocompleteAdapter;
import co.kaua.palacepetz.Activitys.Interfaces.AutoCompleteAdapterClickListener;
import co.kaua.palacepetz.Activitys.Interfaces.PlacesAdapterClickListener;
import co.kaua.palacepetz.R;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MapsFragment extends Fragment implements TextWatcher, View.OnClickListener, AutoCompleteAdapterClickListener, PlacesAdapterClickListener {

    public static final String TAG = "MapsFragment";

    final String[] placeTypesList = {
            "veterinary_care",
            "pet_store",
            "atm",
            "tourist_attraction"
    };

    final String[] titles = {"THÚ Y", "PET SHOP", "ATM", "Đông người"};


    int[] icons = {
            R.drawable.ic_baseline_pets_24,
            R.drawable.background_medicines,
            R.drawable.ic_baseline_pets_24,
            R.drawable.ic_baseline_pets_24
    };

    final Location[] userLoc = new Location[1];

    HashMap<String, String> place;

    List<HashMap<String, String>> directionsDetails;

    TextView searchBox, estName, destName, travelMode,
            destDuration, destAddress, openStatus, destCall, destRating, destTotalRat;

    TextView walkTime, carTime, trainTime;

    EditText search;

    ImageView clearSearchText, closeSearch, closeBS, estIcon;

    FloatingActionButton my_loc, travelModeBtn;
    RatingBar destRatingBar;

    RelativeLayout card1, card2, card3, card4, sheetHeader, carButton, walkButton, trainButton;

    Dialog searchDialog;

    RecyclerView suggestionsRecView, nearbyPlacesRecView;
    SearchAutocompleteAdapter autocompleteAdapter;
    NearbyPlacesAdapter placesAdapter;

    BottomSheetDialog modalSheet;

    LinearLayout locInfoSheet;
    BottomSheetBehavior<LinearLayout> persistentSheet;

    private FusedLocationProviderClient locationClient;
    LocationRequest locationRequest;

    GoogleMap mMap;

    PlacesClient placesClient;
ImageView ic_btnMicro;
    private Boolean locationPermission = false;
    final private int LOCATION_REQUEST_CODE = 1;
    private FusedLocationProviderClient userLocation;
    private EditText searchField;
    private Marker markerName;
    Marker userLocationMarker;
    Circle userLocationAccuracyCircle;
    FusedLocationProviderClient fusedLocationProviderClient;

    private MarkerOptions markerOptions;
    private Geocoder geocoder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_explore, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        //Places Init
        Places.initialize(requireContext(), getString(R.string.google_maps_key));
        placesClient = Places.createClient(requireContext());


        //Maps Fragment layouts
//        searchBox = view.findViewById(R.id.search_text_view);
        ic_btnMicro = view.findViewById(R.id.ic_btnMicro);
        searchField = view.findViewById(R.id.input_search);
        my_loc = view.findViewById(R.id.current_loc);
        card1 = view.findViewById(R.id.rel_layout_1);
        card2 = view.findViewById(R.id.rel_layout_2);
        card3 = view.findViewById(R.id.rel_layout_3);
        card4 = view.findViewById(R.id.rel_layout_4);


        //Modal bottom sheet dialog
        modalSheet = new BottomSheetDialog(requireContext());
        modalSheet.setContentView(R.layout.nearby_places_dialog);
        closeBS = modalSheet.findViewById(R.id.est_close_btn);
        nearbyPlacesRecView = modalSheet.findViewById(R.id.est_rec_v);
        estName = modalSheet.findViewById(R.id.est_name);
        estIcon = modalSheet.findViewById(R.id.est_icon);


        //Persistent Bottom Sheet
        locInfoSheet = view.findViewById(R.id.loc_info_sheet);
        locInfoSheet.setVisibility(View.INVISIBLE);
        persistentSheet = BottomSheetBehavior.from(locInfoSheet);
        persistentSheet.setPeekHeight(360);
        sheetHeader = view.findViewById(R.id.sheet_header);
        destName = view.findViewById(R.id.dest_name);
        destAddress = view.findViewById(R.id.dest_address);
        openStatus = view.findViewById(R.id.open_now);
        destCall = view.findViewById(R.id.dest_call_no);
        destRating = view.findViewById(R.id.dest_rating);
        destRatingBar = view.findViewById(R.id.dest_rating_bar);
        destTotalRat = view.findViewById(R.id.dest_total_ratings);
        travelModeBtn = view.findViewById(R.id.travel_mode_btn);
        travelModeBtn.setVisibility(View.INVISIBLE);
        travelMode = view.findViewById(R.id.travel_text);
        destDuration = view.findViewById(R.id.dest_duration);
        carButton = view.findViewById(R.id.car_btn);
        walkButton = view.findViewById(R.id.walk_btn);
        trainButton = view.findViewById(R.id.train_btn);
        walkTime = view.findViewById(R.id.walk_time);
        carTime = view.findViewById(R.id.car_time);
        trainTime = view.findViewById(R.id.train_time);


        //Search Dialog
        searchDialog = new Dialog(getContext());
        searchDialog.setContentView(R.layout.search_dialog);
        searchDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        searchDialog.getWindow().getAttributes().gravity = Gravity.TOP;
        search = searchDialog.findViewById(R.id.search_edit_text);
        clearSearchText = searchDialog.findViewById(R.id.clear_img_v);
        closeSearch = searchDialog.findViewById(R.id.back_img_v);
        suggestionsRecView = searchDialog.findViewById(R.id.sug_recycler);


        //Location Request
        locationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        clickListeners();
        getPermission();
        loadInputField();
        if (isNetworkAvailable()) {
            Toast.makeText(requireContext(), "Online", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Offline", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        checkPermission();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // TCP/HTTP/DNS (depending on the port, 53=DNS, 80=HTTP, etc.)
    //  Does not run in UI thread
    public boolean isOnline() {

        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress socketAddress = new InetSocketAddress("8.8.8.8", 53);
            sock.connect(socketAddress, timeoutMs);
            sock.close();

            return true;

        } catch (IOException e) {
            return false;
        }
    }

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.getUiSettings().setMapToolbarEnabled(false);

            persistentSheet.addBottomSheetCallback(bottomSheetCallback);

        }
    };


    //Click listener interface
    @Override
    public void onClick(View view) {

        if (view == ic_btnMicro) {
           getSpeech(view);
            search.requestFocus();
            setAutocompleteAdapter();

        } else if (view == clearSearchText) {
            search.getText().clear();

        } else if (view == closeSearch) {
            searchDialog.dismiss();

        } else if (view == my_loc) {

            if (ActivityCompat.checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }

        } else if (view == card1) {

            if (userLoc[0] != null) {
                int cardNo = 0;
                String url = getPlaceTypeURL(userLoc[0], placeTypesList[cardNo]);
                showNearbyPlaces(cardNo, url);
            }

        } else if (view == card2) {

            if (userLoc[0] != null) {
                int cardNo = 1;
                String url = getPlaceTypeURL(userLoc[0], placeTypesList[cardNo]);
                showNearbyPlaces(cardNo, url);
            }

        } else if (view == card3) {

            if (userLoc[0] != null) {
                int cardNo = 2;
                String url = getPlaceTypeURL(userLoc[0], placeTypesList[cardNo]);
                showNearbyPlaces(cardNo, url);
            }

        } else if (view == card4) {

            if (userLoc[0] != null) {
                int cardNo = 3;
                String url = getPlaceTypeURL(userLoc[0], placeTypesList[cardNo]);
                showNearbyPlaces(cardNo, url);
            }

        } else if (view == closeBS) {
            modalSheet.dismiss();

        } else if (view == walkButton) {
            if (directionsDetails != null) {
                String s = "Walking";
                destDuration.setText(directionsDetails.get(0).get("walking"));
                walkTime.setText(directionsDetails.get(0).get("walking"));
                travelModeBtn.setImageResource(R.drawable.walking_icon);
                travelMode.setText(s);
                walkButton.setSelected(true);
                carButton.setSelected(false);
            }


        } else if (view == carButton) {
            if (directionsDetails != null) {
                String s = "Driving";
                destDuration.setText(directionsDetails.get(0).get("driving"));
                carTime.setText(directionsDetails.get(0).get("driving"));
                travelModeBtn.setImageResource(R.drawable.car_icon);
                travelMode.setText(s);
                carButton.setSelected(true);
                walkButton.setSelected(false);
            }
        }

    }

    private void clickListeners() {

        //search
//        searchBox.setOnClickListener(this);
        ic_btnMicro.setOnClickListener(this);
        clearSearchText.setOnClickListener(this);
        closeSearch.setOnClickListener(this);
        search.addTextChangedListener(this);

        //map buttons
        my_loc.setOnClickListener(this);

        //cards
        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);

        //Modal Sheet
        closeBS.setOnClickListener(this);

        //Persistent Sheet
        carButton.setOnClickListener(this);
        walkButton.setOnClickListener(this);
        trainButton.setOnClickListener(this);
    }


    //Persistent Bottom Sheet
    private void showDstDetails(int cardNo) {

        if (NearbyPlacesAdapter.placeList != null) {

            place = NearbyPlacesAdapter.placeList.get(cardNo);

            String address = place.get("vicinity");
            String name = place.get("name");
            String open = place.get("open_now");
            String rating = place.get("rating");
            String totalRatings = place.get("user_ratings_total") + " reviews";

            final String placeId = place.get("place_id");

            final List<Place.Field> placeFields = Arrays.asList(
                    Place.Field.PHONE_NUMBER,
                    Place.Field.LAT_LNG
            );

            final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);

            final Place[] p = new Place[1];

            placesClient.fetchPlace(request).addOnSuccessListener((response) -> {

                p[0] = response.getPlace();
                if (p[0].getPhoneNumber() != null) {
                    destCall.setText(p[0].getPhoneNumber());
                } else {
                    destCall.setText(getResources().getString(R.string.call_not_available));
                }


            }).addOnFailureListener((exception) -> {
                Log.i(TAG, "Place not found: " + exception.getMessage());
            });

            destName.setText(name);
            destCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+p[0].getPhoneNumber().replace("+84","0")));
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(intent);
                }
            });
            destAddress.setText(address);

            if (rating != null) {
                destRating.setText(rating);
                destRatingBar.setRating(Float.parseFloat(rating));
                destTotalRat.setText(totalRatings);

            } else {
                String message = "Not rated";
                destRating.setText(message);
                destRatingBar.setVisibility(View.INVISIBLE);
                destTotalRat.setVisibility(View.INVISIBLE);
            }

            boolean status = Boolean.parseBoolean(open);
            if (status) {
                openStatus.setText(Html.fromHtml("<font color=#33691e>Open now</font>"));
            } else {
                openStatus.setText(Html.fromHtml("<font color=#900606>Closed</font>"));
            }

            directionsDetails = getDirectionDetails(placeId);

            destDuration.setText(directionsDetails.get(0).get("driving"));
            walkTime.setText(directionsDetails.get(0).get("walking"));
            carTime.setText(directionsDetails.get(0).get("driving"));
            walkButton.setSelected(true);
            carButton.setSelected(false);

            for (int i = 0; i < polyline.get("driving").length; i++) {
                PolylineOptions options = new PolylineOptions();
                options.addAll(PolyUtil.decode(polyline.get("driving")[i]));
                options.color(Color.parseColor("#0d47a1"));
                options.width(15);
                mMap.addPolyline(options);
                getLastLocation();



            }

            travelMode.setText(R.string.travel_default_mode);
            travelModeBtn.setImageResource(R.drawable.car_icon);

            locInfoSheet.setVisibility(View.VISIBLE);
            travelModeBtn.setVisibility(View.VISIBLE);

        }
    }

    HashMap<String, String> durations = new HashMap<>();
    HashMap<String, String[]> polyline = new HashMap<>();

    private List<HashMap<String, String>> getDirectionDetails(String placeId) {

        String mode1 = "walking";
        String mode2 = "driving";

        String url1 = getDirectionURL(userLoc[0], placeId, mode1);
        String url2 = getDirectionURL(userLoc[0], placeId, mode2);

        Log.d(TAG, "getDirectionDetails: " + url1);

        HashMap<String, String> urlsMap = new HashMap<>();
        urlsMap.put("walking", url1);
        urlsMap.put("driving", url2);

        HashMap<String, String> directionsData = null;

        GetDirections task = new GetDirections();
        try {
            directionsData = task.execute(urlsMap).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        DataParser dataParser = new DataParser();

        durations.put("walking", dataParser.destDuration(directionsData.get("walking")));
        durations.put("driving", dataParser.destDuration(directionsData.get("driving")));

        polyline.put("walking", dataParser.parseDirections(directionsData.get("walking")));
        polyline.put("driving", dataParser.parseDirections(directionsData.get("driving")));
      //  Uri gmmIntentUri = Uri.parse("google.navigation:q="+placeId+","+directionsData+"&mode=l");

    //    mMap.moveCamera();

        List<HashMap<String, String>> directionDetails = new ArrayList<>();
        directionDetails.add(durations);

        return directionDetails;
    }

    private final BottomSheetBehavior.BottomSheetCallback bottomSheetCallback =
            new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                    if (slideOffset > 0.5f) {
                        Log.d(TAG, "onSlide: " + slideOffset);
                        animateFab(slideOffset);
                    }
                    if (slideOffset == 1.0f) {
                        travelMode.setVisibility(View.VISIBLE);
                    } else {
                        travelMode.setVisibility(View.INVISIBLE);
                    }
                }
            };

    private void animateFab(float slideOffset) {
        //for slideOffset [0.5,1]
        float a = 2 * slideOffset;
        float b = 1 - (a - 1);
        travelModeBtn.setScaleX(b);
        travelModeBtn.setScaleY(b);
    }

    private String getDirectionURL(Location location, String placeId, String mode) {

        return "https://maps.googleapis.com/maps/api/directions/json?"
                + "origin=" + location.getLatitude() + "," + location.getLongitude()
                + "&destination=place_id:" + placeId + "&mode=" + mode +
                "&key=" + getResources().getString(R.string.google_maps_key)+"&language=vi";

    }


    //Modal Bottom Sheet Dialog
    @Override
    public void onCardClick(int position) {
        mMap.clear();
        placesAdapter.setMarker(position);
        modalSheet.dismiss();
        showDstDetails(position);

    }

    private void showNearbyPlaces(int cardNo, String url) {

        estName.setText(titles[cardNo]);
        estIcon.setImageResource(icons[cardNo]);

        placesAdapter = new NearbyPlacesAdapter(this, mMap);
        placesAdapter.showPlaces(url);
        nearbyPlacesRecView.setAdapter(placesAdapter);

        modalSheet.setCancelable(false);
        modalSheet.show();

    }

    private String getPlaceTypeURL(Location location, String placeType) {

        return "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
                + "location=" + location.getLatitude() + "," + location.getLongitude() +
                "&radius=10000" + "&type=" + placeType +"&sensor=true"+
                "&key=" + getResources().getString(R.string.google_maps_key)+"&language=vi-VN";

    }


    //Search Dialog
    @Override
    public void onSearchedItemClick(int position) {
        getSearchedPlace(position);
        search.getText().clear();
        searchDialog.dismiss();
    }

    private void addSearchMarker(LatLng latLng, String address) {

        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.navigative))
                .title(address));
//    mMap.setOnMapClickListener(new GoogleMap.OnMapLongClickListener() {
//        @Override
//        public void onMapLongClick(@NonNull LatLng latLng) {
//    //            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
//    //            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
//
//
//        }
//    });

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

    }

    private void getSearchedPlace(int position) {

        if (SearchAutocompleteAdapter.placeIDs != null) {

            final String placeId = SearchAutocompleteAdapter.placeIDs.get(position);

            // Specify the fields to return.
            final List<Place.Field> placeFields = Arrays.asList(
                    Place.Field.LAT_LNG,
                    Place.Field.NAME
            );

            // Construct a request object, passing the place ID and fields array.
            final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);

            placesClient.fetchPlace(request).addOnSuccessListener((response) -> {

                Place place = response.getPlace();
                addSearchMarker(place.getLatLng(), place.getName());

            }).addOnFailureListener((exception) -> {
                Log.i(TAG, "Place not found: " + exception.getMessage());
            });

        }

    }

    private void setAutocompleteAdapter() {
        autocompleteAdapter = new SearchAutocompleteAdapter(getContext(), this);
        DividerItemDecoration divider = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        suggestionsRecView.addItemDecoration(divider);
        suggestionsRecView.setAdapter(autocompleteAdapter);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!charSequence.equals("")) {
            autocompleteAdapter.getPredictions(charSequence);
            if (suggestionsRecView.getVisibility() == View.GONE) {
                suggestionsRecView.setVisibility(View.VISIBLE);
            }
        } else {
            if (suggestionsRecView.getVisibility() == View.VISIBLE) {
                suggestionsRecView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }


    //User current location
    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);

            userLoc[0] = locationResult.getLastLocation();
        }
    };


    private void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(requireContext(),
                ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationClient.requestLocationUpdates(
                    locationRequest, locationCallback, Looper.getMainLooper());

        }
    }

    private void stopLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationClient.removeLocationUpdates(locationCallback);
        }
    }

    private void getLastLocation() {

        if (ActivityCompat.checkSelfPermission(requireContext(),
                ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationClient.getLastLocation().addOnSuccessListener(location -> {

                if (location != null) {

                    userLoc[0] = location;

                    mMap.setMyLocationEnabled(true);

                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(location.getLatitude(), location.getLongitude()))
                           // .icon(BitmapDescriptorFactory.fromResource(R.drawable.redcar24))
                           // .icon(BitmapDescriptorFactory.fromResource(R.drawable.redcar24))
//                            .anchor(0.5f,0.5f)
                            .rotation(90.0f));


                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(location.getLatitude(), location.getLongitude()))
                            .title("My location");
                    mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng , 16));

                } else {
                    startLocationUpdates();
                }
            });
        }
    }

//    private void startLocationUpdates() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
//    }
//    LocationCallback locationCallback = new LocationCallback() {
//        @Override
//        public void onLocationResult(LocationResult locationResult) {
//            super.onLocationResult(locationResult);
//            Log.d(TAG, "onLocationResult: " + locationResult.getLastLocation());
//            if (mMap != null) {
//                setUserLocationMarker(locationResult.getLastLocation());
//            }
//        }
//    };
    private void setUserLocationMarker(Location location) {

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        if (userLocationMarker == null) {
            //Create a new marker
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.redcar24));
            markerOptions.rotation(location.getBearing());
            markerOptions.anchor((float) 0.5, (float) 0.5);
            userLocationMarker = mMap.addMarker(markerOptions);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        } else {
            //use the previously created marker
            userLocationMarker.setPosition(latLng);
            userLocationMarker.setRotation(location.getBearing());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        }

        if (userLocationAccuracyCircle == null) {
            CircleOptions circleOptions = new CircleOptions();
            circleOptions.center(latLng);
            circleOptions.strokeWidth(4);
            circleOptions.strokeColor(Color.argb(255, 255, 0, 0));
            circleOptions.fillColor(Color.argb(32, 255, 0, 0));
            circleOptions.radius(location.getAccuracy());
            userLocationAccuracyCircle = mMap.addCircle(circleOptions);
        } else {
            userLocationAccuracyCircle.setCenter(latLng);
            userLocationAccuracyCircle.setRadius(location.getAccuracy());
        }
    }

    //On request permissions result
    private final ActivityResultLauncher<String> permissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
                if (result) {
                    checkGps();

                } else {
                    Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
            });


    //On activity result
    private final ActivityResultLauncher<IntentSenderRequest> resultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(),
                    result -> {
                        Log.d(TAG, "Result code : " + result.getResultCode());
                        if (result.getResultCode() == RESULT_OK) {
                            getLastLocation();
                        } else if (result.getResultCode() == RESULT_CANCELED) {
                            Toast.makeText(getContext(), "Please turn on GPS to get location updates",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });


    private void checkGps() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest
                .Builder().addLocationRequest(locationRequest);
        SettingsClient settingsClient = LocationServices.getSettingsClient(requireContext());

        Task<LocationSettingsResponse> responseTask = settingsClient
                .checkLocationSettings(builder.build());

        responseTask.addOnSuccessListener(locationSettingsResponse -> getLastLocation())
                .addOnFailureListener(e -> {
                    if (e instanceof ResolvableApiException) {
                        IntentSenderRequest intentSenderRequest = new IntentSenderRequest
                                .Builder(((ResolvableApiException) e).getResolution()).build();
                        resultLauncher.launch(intentSenderRequest);
                    }

                });

    }

    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(
                requireContext(), ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {

            checkGps();

        } else if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
            //dialog
        } else {
            // You can directly ask for the permission i.e no use of REQUEST_CODE.
            // The registered ActivityResultCallback gets the result of this request.
            permissionLauncher.launch(
                    ACCESS_FINE_LOCATION);
        }
    }
    private void loadInputField() {
        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    findPlace();
                }
                return false;
            }
        });
    }



    private void findPlace() {
        String search = searchField.getText().toString();

        Geocoder geocoder = new Geocoder(getContext());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(search, 1);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        if (list.size() > 0) {
            Address address = list.get(0);
            moveToPoint(new LatLng(address.getLatitude(), address.getLongitude()),
                    12f, address.getAddressLine(0));
        }
        else {
            Toast.makeText(getContext(), "Can't find searched place", Toast.LENGTH_LONG).show();
        }
    }

    private void getPermission() {
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermission = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), permission, LOCATION_REQUEST_CODE);
        }
    }


    private void moveToPoint(LatLng latLng, float zoom, String name) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (markerName!=null) markerName.remove();

        if (name != "Me") {
            MarkerOptions options = new MarkerOptions().position(latLng).title(name);
            markerName = mMap.addMarker(options);
        }
    }
    // lay giong nói

    public void getSpeech(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, 2);
        } else {
            Toast.makeText(getContext(), "Nhận dạng giọng nói không được hỗ trợ trong thiết bị này", Toast.LENGTH_SHORT).show();
        }
    }

}