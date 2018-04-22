package ceri.m1ilsen.applicationprojetm1.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.adapter.PatientsListViewAdapter;
import ceri.m1ilsen.applicationprojetm1.adapter.SessionsListViewAdapter;
import ceri.m1ilsen.applicationprojetm1.exercise.Session;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;
import ceri.m1ilsen.applicationprojetm1.user.Patient;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClinicianResultsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClinicianResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClinicianResultsFragment extends Fragment {
    private ListView patientsListView;
    private ListView sessionsListView;
    private ArrayList<String> patientsData = new ArrayList<String>();
    private ArrayList<String> sessionsData = new ArrayList<String>();
    private TextView numberOfSessions;
    private PatientRecordingsFragment.OnFragmentInteractionListener mListener;

    public ClinicianResultsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ClinicianResultsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClinicianResultsFragment newInstance(String param1, String param2) {
        ClinicianResultsFragment fragment = new ClinicianResultsFragment();
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
        final View view = inflater.inflate(R.layout.fragment_clinician_results, container, false);

        patientsListView = (ListView) view.findViewById(R.id.patientsList);
        sessionsListView = (ListView) view.findViewById(R.id.sessionsList);

        final MyApplicationDataSource BD = new MyApplicationDataSource(getActivity());
        BD.open();
        final String currentUser = getActivity().getIntent().getStringExtra("connectedUserPseudo");
        final int currentId = getActivity().getIntent().getIntExtra("connectedUserId",0);
        //int currentId = BD.getClinicianIdByPseudo(currentUser);
        List<Patient> patients = BD.getPatientByClinicianId(currentId);
        ArrayList<String> patientsPseudo = new ArrayList<>();
        for(int i=0; i<patients.size(); i++)
            patientsPseudo.add(patients.get(i).getPseudo().toString());
        patientsData = patientsPseudo;

        PatientsListViewAdapter patientsListViewAdapter = new PatientsListViewAdapter(view.getContext(), R.layout.patient_item_view, patientsData);

        patientsListViewAdapter.setListener(new PatientsListViewAdapter.AdapterListener() {
            public void onClick(String name) {
                int currentUserId = BD.getPatientIdByPseudo(name);
                List<Session> sessions = BD.getSessionByPatientId(currentUserId);
                ArrayList<String> sessionNames = new ArrayList<>();

                for(int i=0; i<sessions.size(); i++) {
                    sessionNames.add(sessions.get(i).getName().toString());
                }
                sessionsData = sessionNames;

                numberOfSessions = (TextView) view.findViewById(R.id.numberOfSessions);
                if (sessionsData.size() == 0)
                    numberOfSessions.setText("Aucune session disponible");
                else if (sessionsData.size() == 1)
                    numberOfSessions.setText(sessionsData.size()+" session est disponible");
                else
                    numberOfSessions.setText(sessionsData.size()+" session sont disponibles");
                SessionsListViewAdapter sessionsListViewAdapter = new SessionsListViewAdapter(getContext(), R.layout.session_item_view, sessionsData);
                sessionsListView.setAdapter(sessionsListViewAdapter);
                ((BaseAdapter)sessionsListView.getAdapter()).notifyDataSetChanged();

            }
        });
        patientsListView.setAdapter(patientsListViewAdapter);

        SessionsListViewAdapter sessionsListViewAdapter = new SessionsListViewAdapter(view.getContext(), R.layout.session_item_view, sessionsData);
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
