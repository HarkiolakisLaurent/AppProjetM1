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
import ceri.m1ilsen.applicationprojetm1.adapter.HomePageListViewAdapter;
import ceri.m1ilsen.applicationprojetm1.exercise.Exercise;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PatientHomePageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PatientHomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientHomePageFragment extends Fragment {

    ListView welcomeResultsListView;
    public HomePageListViewAdapter adapter;
    private List<String> data = new ArrayList<String>();
    private OnFragmentInteractionListener mListener;

    public PatientHomePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment PatientHomePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientHomePageFragment newInstance(String param1, String param2) {
        PatientHomePageFragment fragment = new PatientHomePageFragment();
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
        View view = inflater.inflate(R.layout.fragment_patient_home_page, container, false);

        TextView welcomePatient = (TextView) view.findViewById(R.id.welcomePatient);
        welcomePatient.setText("Bonjour "+getActivity().getIntent().getStringExtra("connectedUserPseudo")+", c'est un plaisir de vous revoir !");

        welcomeResultsListView = (ListView) view.findViewById(R.id.listResults);

        final MyApplicationDataSource BD = new MyApplicationDataSource(getActivity());
        BD.open();
        int currentUserId = getActivity().getIntent().getExtras().getInt("connectedUserId");
        List<Exercise> exercises = BD.getExerciseByPatientId(currentUserId);
        List<String> exerciseNames = new ArrayList<>();

        for(int i=0; i<exercises.size(); i++) {
            if (exercises.get(i).getScore() == 0)
                exerciseNames.add(exercises.get(i).getCreationDate().toString()+", vous avez commencé un exercice");
            else
                exerciseNames.add(exercises.get(i).getCreationDate().toString()+", vous avez obtenu "+exercises.get(i).getScore());
        }
        data = exerciseNames;
        BD.close();

        welcomeResultsListView.setAdapter(new HomePageListViewAdapter(view.getContext(), R.layout.home_page_item_view, data));

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
