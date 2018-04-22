package ceri.m1ilsen.applicationprojetm1.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.adapter.MonitorPatientsListViewAdapter;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;
import ceri.m1ilsen.applicationprojetm1.ui.CreatePatientActivity;
import ceri.m1ilsen.applicationprojetm1.user.Patient;

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
    private MonitorPatientsListViewAdapter monitorPatientsListViewAdapter;

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

        final MyApplicationDataSource BD = new MyApplicationDataSource(getActivity());
        BD.open();
        final String currentUser = getActivity().getIntent().getStringExtra("connectedUserPseudo");
        final int currentId = getActivity().getIntent().getIntExtra("connectedUserId",0);
        String mail = getActivity().getIntent().getStringExtra("connectedUserMail");
        //int currentId = BD.getClinicianIdByPseudo(currentUser);
        List<Patient> patients = BD.getPatientByClinicianId(currentId);
        ArrayList<String> patientsPseudo = new ArrayList<>();
        for(int i=0; i<patients.size(); i++)
            patientsPseudo.add(patients.get(i).getPseudo().toString());
        data = patientsPseudo;
        BD.close();

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
                Intent createPatient = new Intent(view.getContext(), CreatePatientActivity.class);
                createPatient.putExtra("connectedUserPseudo",currentUser);
                createPatient.putExtra("connectedUserId",currentId);
                startActivityForResult(createPatient,10000);
            }
        });
        monitorPatientsListViewAdapter = new MonitorPatientsListViewAdapter(view.getContext(), R.layout.monitor_patient_item_view, data, mail);
        monitorPatientsListView.setAdapter(monitorPatientsListViewAdapter);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        //Check the result and request code here and update ur activity class
        if ((requestCode == 10000) /* && (resultCode == Activity.RESULT_OK)*/) {
            // recreate your fragment here
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.detach(this).attach(this);
            fragmentTransaction.commit();
        }
    }
}
