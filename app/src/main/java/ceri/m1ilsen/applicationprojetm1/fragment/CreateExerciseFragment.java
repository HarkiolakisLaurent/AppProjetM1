package ceri.m1ilsen.applicationprojetm1.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.adapter.ExercisesListViewAdapter;
import ceri.m1ilsen.applicationprojetm1.ui.DoExerciceActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateExerciseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateExerciseFragment extends Fragment {

    private ListView exercisesListView;
    private ArrayList<String> data = new ArrayList<String>();
    private TextView numberOfExercises;

    private TextView mTextMessage;
    private Button btnLireMots;
    static int i=0;
    private View view;
    private OnFragmentInteractionListener mListener;

    public CreateExerciseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment CreateExerciseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateExerciseFragment newInstance(String param1, String param2) {
        CreateExerciseFragment fragment = new CreateExerciseFragment();
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
        view = inflater.inflate(R.layout.fragment_create_exercise, container, false);

        exercisesListView = (ListView) view.findViewById(R.id.exercisesList);

        data.add("Exercice 1");

        numberOfExercises = (TextView) view.findViewById(R.id.numberOfExercises);
        if (data.size() == 0)
            numberOfExercises.setText("Aucun exercice disponible");
        else if (data.size() == 1)
            numberOfExercises.setText(data.size()+" exercice est disponible");
        else
            numberOfExercises.setText(data.size()+" exercices sont disponibles");

        ExercisesListViewAdapter exercisesListViewAdapter = new ExercisesListViewAdapter(view.getContext(), R.layout.exercise_item_view, data);
        exercisesListView.setAdapter(exercisesListViewAdapter);

        mTextMessage = (TextView) view.findViewById(R.id.message);
        //File file=new File("/src/java/mots.txt.txt");
        btnLireMots=(Button) view.findViewById(R.id.button10);
        ReadFileWord();
        return view;
    }
    public void ReadFileWord(){
        btnLireMots.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(),DoExerciceActivity.class);
                startActivity(intent);
            }
        });

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
