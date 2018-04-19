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
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.adapter.RecordingsListViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PatientRecordingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PatientRecordingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientRecordingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ListView recordingsListView;
    private List<String> data = new ArrayList<String>();
    private File dataPath;
    private TextView numberOfRecordings;
    private OnFragmentInteractionListener mListener;

    public PatientRecordingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PatientRecordingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientRecordingsFragment newInstance() {
        PatientRecordingsFragment fragment = new PatientRecordingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_patient_recordings, container, false);
        recordingsListView = (ListView) view.findViewById(R.id.listResults);
        //dataPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        dataPath = new File("/storage/emulated/0/App/Recordings/"+getActivity().getIntent().getStringExtra("connectedUserPseudo"));
        data = new ArrayList(Arrays.asList(dataPath.list(new FilenameFilter() {
            public boolean accept(File directory, String fileName) {
                return fileName.endsWith(".wav") || fileName.endsWith(".mp3");
            }
        })));

        numberOfRecordings = (TextView) view.findViewById(R.id.numberOfRecordings);
        if (data.size() == 0)
            numberOfRecordings.setText("Aucun enregistrement disponible");
        else if (data.size() == 1)
            numberOfRecordings.setText(data.size()+" enregistrement est disponible");
        else
            numberOfRecordings.setText(data.size()+" enregistrements sont disponibles");

        RecordingsListViewAdapter recordingsListViewAdapter = new RecordingsListViewAdapter(view.getContext(), R.layout.recording_item_view, data, dataPath);
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
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
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
