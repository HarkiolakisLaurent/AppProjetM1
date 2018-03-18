package ceri.m1ilsen.applicationprojetm1.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.sqlite.CommentsDataSource;
import ceri.m1ilsen.applicationprojetm1.sqlite.Contexte;
import ceri.m1ilsen.applicationprojetm1.ui.PatientResultsActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PatientResultsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PatientResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientResultsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public PatientResultsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
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
        String[] sessions ;
        Button btnExercice = null;
        Button btnEnregist = null;
        Button btnResult = null;
        ListView liste;
        CommentsDataSource BD = new CommentsDataSource(view.getContext());
        BD.open();

        liste = (ListView) view.findViewById(R.id.listResultsSession);
        sessions = BD.getSessions(Contexte.patient);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext() ,
                android.R.layout.simple_list_item_1, sessions);
        liste.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
