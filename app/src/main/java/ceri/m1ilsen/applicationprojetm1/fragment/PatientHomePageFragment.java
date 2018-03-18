package ceri.m1ilsen.applicationprojetm1.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.adapter.ListViewAdapter;
import ceri.m1ilsen.applicationprojetm1.sqlite.CommentsDataSource;
import ceri.m1ilsen.applicationprojetm1.ui.PatientHomePageActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PatientHomePageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PatientHomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientHomePageFragment extends Fragment {

    ListView lv;
    public ListViewAdapter adapter;
    private ArrayList<String> data = new ArrayList<String>();
    private OnFragmentInteractionListener mListener;

    public PatientHomePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
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
        lv = (ListView) view.findViewById(R.id.listResults);
        data= new ArrayList<>();

        data.add(new String("Le DATE à HEURE, vous avez obtenu 75"));
        data.add(new String("Le DATE à HEURE, vous avez obtenu 80"));

        lv.setAdapter(new ListViewAdapter(view.getContext(), R.layout.affichageitem, data));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String dataModel= data.get(position);
                Toast.makeText(view.getContext(), "List item was clicked at " + position, Toast.LENGTH_SHORT).show();

            }
        });

        CommentsDataSource BD = new CommentsDataSource(view.getContext());
        BD.open();
        // String data[] = BD.getName();
        System.out.println("LE pseaudo est hhhhhhhhhhhhhhhhhhhhhhhhhhhhhh"+ BD.getName());
        BD.close();
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
