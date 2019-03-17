package com.example.hackathon;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllEventsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllEventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllEventsFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AllEventsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllEventsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllEventsFragment newInstance(String param1, String param2) {
        AllEventsFragment fragment = new AllEventsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            user = (User) bundle.getSerializable("informal");
        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private EventsAdapter eventsAdapter;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_events, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.all_events_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        eventsAdapter = new EventsAdapter(view.getContext(),new ArrayList<Event>(), user, 0);
        Log.d("user",user.toString());
        recyclerView.setAdapter(eventsAdapter);
        firebaseFirestore.collection("events").whereEqualTo("endDate",null)/*.orderBy("creation", Query.Direction.ASCENDING)*/
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }
                        Log.d("sccdcsc",queryDocumentSnapshots.toString());
                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                            switch (doc.getType()) {
                                case ADDED:
                                    Event event = doc.getDocument().toObject(Event.class);
                                    event.setEventId(doc.getDocument().getId());
                                    //event.compute();
                                    // if(eventsAdapter.isUserPresent(event.))
                                    Log.d("xds",doc.getNewIndex()+""+doc.getOldIndex());
                                    eventsAdapter.added(event);
                                    Log.d("added", "New city: " + doc.getDocument().getData());
                                    break;
                                case MODIFIED:
                                    Log.d("modified", "Modified city: " + doc.getDocument().getData());
                                    Log.d("modi",doc.getNewIndex()+""+doc.getOldIndex());
                                    break;
                                case REMOVED:
                                    Log.d("removed", "Removed city: " + doc.getDocument().getData());
                                    Log.d("remove",doc.getNewIndex()+""+doc.getOldIndex());
                                    eventsAdapter.removed(doc.getDocument().getId());
                                    break;
                            }

                            try{
                                //Log.w(TAG, "Listen suxxx.",doc.getDocument(),toString());
                            }catch(Exception f){
                                Log.d("cedc","cecd");
                            }


                            //}
                        }
                    }
                });

        firebaseFirestore.collection("informal").document(firebaseAuth.getUid())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }
                        User user = documentSnapshot.toObject(User.class);
                        eventsAdapter.setUser(user);
                        /*for (String s : user.getSaved()) {
                            if (!eventsAdapter.getItems().contains(s)) {
                                firebaseFirestore.collection("events").document(s)
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                Event event = documentSnapshot.toObject(Event.class);
                                                event.setEventId(documentSnapshot.getId());
                                                eventsAdapter.added(event);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });
                            }
                        }*/

                        for (String s : eventsAdapter.getItems()) {
                            if (!user.getSaved().contains(s)) {
                                eventsAdapter.notifySaved(s);
                                //eventsAdapter.notifyItemChanged(eventsAdapter.getItems().indexOf(s));
                            }
                        }

                        /* if(eventsAdapter.getItems().contains(event.getEventId()))*/
                        Log.d("saved", documentSnapshot.getMetadata().toString() + "" + documentSnapshot.getData());
                    }
                });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
