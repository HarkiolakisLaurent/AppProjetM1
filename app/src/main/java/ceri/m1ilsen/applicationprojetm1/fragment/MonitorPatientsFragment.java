package ceri.m1ilsen.applicationprojetm1.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.adapter.MonitorPatientsListViewAdapter;
import ceri.m1ilsen.applicationprojetm1.ui.CreatePatientActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MonitorPatientsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MonitorPatientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonitorPatientsFragment extends Fragment {

    private Button newPatient;
    private ListView monitorPatientsListView;
    private ArrayList<String> data = new ArrayList<String>();
    private TextView numberOfPatients;
    private OnFragmentInteractionListener mListener;

    public MonitorPatientsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MonitorPatientsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonitorPatientsFragment newInstance(String param1, String param2) {
        MonitorPatientsFragment fragment = new MonitorPatientsFragment();
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
        final View view = inflater.inflate(R.layout.fragment_monitor_patients, container, false);
        monitorPatientsListView = (ListView) view.findViewById(R.id.patientsList);
        data = new ArrayList();
        data.add("Toto Toto");

        numberOfPatients = (TextView) view.findViewById(R.id.numberOfPatients);
        if (data.size() == 0)
            numberOfPatients.setText("Vous n'avez pas de patients");
        else if (data.size() == 1)
            numberOfPatients.setText("Vous avez "+data.size()+" patient");
        else
            numberOfPatients.setText("Vous avez "+data.size()+" patients");

        newPatient = (Button) view.findViewById(R.id.newPatientButton);
        newPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(view.getContext(), CreatePatientActivity.class);
                startActivity(nextActivity);
            }
        });
        MonitorPatientsListViewAdapter adapter = new MonitorPatientsListViewAdapter(view.getContext(), R.layout.monitor_patient_item_view, data);
        monitorPatientsListView.setAdapter(adapter);
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
