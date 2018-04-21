package ceri.m1ilsen.applicationprojetm1.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.adapter.SessionsListViewAdapter;
import ceri.m1ilsen.applicationprojetm1.exercise.Session;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PatientResultsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PatientResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientResultsFragment extends Fragment {

    private ListView sessionsListView;
    private ArrayList<String> data = new ArrayList<String>();
    private TextView numberOfSessions;
    private OnFragmentInteractionListener mListener;

    public PatientResultsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment PatientResultsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientResultsFragment newInstance(String param1, String param2) {
        PatientResultsFragment fragment = new PatientResultsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_results, container, false);
        sessionsListView = (ListView) view.findViewById(R.id.sessionsList);

        final MyApplicationDataSource BD = new MyApplicationDataSource(getActivity());
        BD.open();
        int currentUserId = getActivity().getIntent().getExtras().getInt("connectedUserId");
        List<Session> sessions = BD.getSessionByPatientId(currentUserId);
        ArrayList<String> sessionNames = new ArrayList<>();

        for(int i=0; i<sessions.size(); i++) {
            sessionNames.add(sessions.get(i).getName().toString());
        }
        data = sessionNames;

        numberOfSessions = (TextView) view.findViewById(R.id.numberOfSessions);
        if (data.size() == 0)
            numberOfSessions.setText("Aucune session disponible");
        else if (data.size() == 1)
            numberOfSessions.setText(data.size()+" session est disponible");
        else
            numberOfSessions.setText(data.size()+" sessions sont disponibles");

        SessionsListViewAdapter sessionsListViewAdapter = new SessionsListViewAdapter(view.getContext(), R.layout.session_item_view, data);
        sessionsListView.setAdapter(sessionsListViewAdapter);
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
