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
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.adapter.PatientsListViewAdapter;
import ceri.m1ilsen.applicationprojetm1.adapter.RecordingsListViewAdapter;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;
import ceri.m1ilsen.applicationprojetm1.user.Patient;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClinicianRecordingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClinicianRecordingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClinicianRecordingsFragment extends Fragment {
    private ListView patientsListView;
    private ListView recordingsListView;
    private ArrayList<String> patientsData = new ArrayList<String>();
    private ArrayList<String> recordingsData = new ArrayList<String>();
    private File dataPath;
    private TextView numberOfRecordings;
    private PatientRecordingsFragment.OnFragmentInteractionListener mListener;

    public ClinicianRecordingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ClinicianRecordingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClinicianRecordingsFragment newInstance(String param1, String param2) {
        ClinicianRecordingsFragment fragment = new ClinicianRecordingsFragment();
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
        final View view = inflater.inflate(R.layout.fragment_clinician_recordings, container, false);

        patientsListView = (ListView) view.findViewById(R.id.patientsList);
        recordingsListView = (ListView) view.findViewById(R.id.recordingsList);

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
        BD.close();

        PatientsListViewAdapter patientsListViewAdapter = new PatientsListViewAdapter(view.getContext(), R.layout.patient_item_view, patientsData);

        patientsListViewAdapter.setListener(new PatientsListViewAdapter.AdapterListener() {
            public void onClick(String name) {
                dataPath = new File("/storage/emulated/0/App/Recordings/"+name+"/");
                recordingsData = new ArrayList(Arrays.asList(dataPath.list(new FilenameFilter() {
                    public boolean accept(File directory, String fileName) {
                        return fileName.endsWith(".wav") || fileName.endsWith(".mp3");
                    }
                })));

                numberOfRecordings = (TextView) view.findViewById(R.id.numberOfRecordings);
                if (recordingsData.size() == 0)
                    numberOfRecordings.setText("Aucun enregistrement disponible");
                else if (recordingsData.size() == 1)
                    numberOfRecordings.setText(recordingsData.size()+" enregistrement est disponible");
                else
                    numberOfRecordings.setText(recordingsData.size()+" enregistrements sont disponibles");
                RecordingsListViewAdapter recordingsListViewAdapter = new RecordingsListViewAdapter(getContext(), R.layout.recording_item_view, recordingsData, dataPath);
                recordingsListView.setAdapter(recordingsListViewAdapter);
                ((BaseAdapter)recordingsListView.getAdapter()).notifyDataSetChanged();

            }
        });
        patientsListView.setAdapter(patientsListViewAdapter);

        RecordingsListViewAdapter recordingsListViewAdapter = new RecordingsListViewAdapter(view.getContext(), R.layout.recording_item_view, recordingsData, dataPath);
        recordingsListView.setAdapter(recordingsListViewAdapter);
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
