package ceri.m1ilsen.applicationprojetm1.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.adapter.ExercisesListViewAdapter;
import ceri.m1ilsen.applicationprojetm1.exercise.Exercise;
import ceri.m1ilsen.applicationprojetm1.sqlite.MyApplicationDataSource;
import ceri.m1ilsen.applicationprojetm1.ui.ConfigureExerciseActivity;
import ceri.m1ilsen.applicationprojetm1.ui.DoExerciseActivity;

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
    private List<String> data = new ArrayList<String>();
    private TextView numberOfExercises;

    private TextView mTextMessage;
    private Button readWordsButton;
    private Button readSentencesButton;
    private Button readTextButton;
    private Button customExerciseButton;
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

        final MyApplicationDataSource BD = new MyApplicationDataSource(getActivity());
        BD.open();
        final int currentId = getActivity().getIntent().getIntExtra("connectedUserId",0);
        List<Exercise> exercises = BD.getExerciseByPatientId(currentId);
        List<String> exercisesName = new ArrayList<>();

        for(int i=0; i<exercises.size(); i++) {
            exercisesName.add(exercises.get(i).getName().toString());
        }
        data = exercisesName;
        BD.close();

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
        readWordsButton=(Button) view.findViewById(R.id.readWords);
        readWordsButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                List<String> data = new ArrayList<String>();
                File dataPath;
                dataPath = new File("/storage/emulated/0/App/Recordings/"+getActivity().getIntent().getStringExtra("connectedUserPseudo"));
                data = new ArrayList(Arrays.asList(dataPath.list(new FilenameFilter() {
                    public boolean accept(File directory, String fileName) {
                        return fileName.endsWith(".wav") || fileName.endsWith(".mp3");
                    }
                })));
                if (data.size() > 30) {
                    Toast.makeText(getContext(),"Vous avez trop d'enregistrements. Veuillez en supprimer avant de faire un exercice",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent createExercise = new Intent(getContext(),DoExerciseActivity.class);
                    createExercise.putExtra("patientPseudo",getActivity().getIntent().getExtras().getString("connectedUserPseudo"));
                    createExercise.putExtra("patientId",getActivity().getIntent().getExtras().getInt("connectedUserId"));
                    createExercise.putExtra("task","mots");
                    createExercise.putExtra("isNewExercise",true);
                    createExercise.putExtra("sessionId",getActivity().getIntent().getExtras().getInt("sessionId"));
                    startActivityForResult(createExercise,10000);
                }
            }
        });
        readSentencesButton=(Button) view.findViewById(R.id.readSentences);
        readSentencesButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                List<String> data = new ArrayList<String>();
                File dataPath;
                dataPath = new File("/storage/emulated/0/App/Recordings/"+getActivity().getIntent().getStringExtra("connectedUserPseudo"));
                data = new ArrayList(Arrays.asList(dataPath.list(new FilenameFilter() {
                    public boolean accept(File directory, String fileName) {
                        return fileName.endsWith(".wav") || fileName.endsWith(".mp3");
                    }
                })));
                if (data.size() > 30) {
                    Toast.makeText(getContext(),"Vous avez trop d'enregistrements. Veuillez en supprimer avant de faire un exercice",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent createExercise = new Intent(getContext(),DoExerciseActivity.class);
                    createExercise.putExtra("patientPseudo",getActivity().getIntent().getExtras().getString("connectedUserPseudo"));
                    createExercise.putExtra("patientId",getActivity().getIntent().getExtras().getInt("connectedUserId"));
                    createExercise.putExtra("task","phrases");
                    createExercise.putExtra("isNewExercise",true);
                    createExercise.putExtra("sessionId",getActivity().getIntent().getExtras().getInt("sessionId"));
                    startActivityForResult(createExercise,10000);
                }
            }
        });
        readTextButton=(Button) view.findViewById(R.id.readText);
        readTextButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                List<String> data = new ArrayList<String>();
                File dataPath;
                dataPath = new File("/storage/emulated/0/App/Recordings/"+getActivity().getIntent().getStringExtra("connectedUserPseudo"));
                data = new ArrayList(Arrays.asList(dataPath.list(new FilenameFilter() {
                    public boolean accept(File directory, String fileName) {
                        return fileName.endsWith(".wav") || fileName.endsWith(".mp3");
                    }
                })));
                if (data.size() > 30) {
                    Toast.makeText(getContext(),"Vous avez trop d'enregistrements. Veuillez en supprimer avant de faire un exercice",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent createExercise = new Intent(getContext(),DoExerciseActivity.class);
                    createExercise.putExtra("patientPseudo",getActivity().getIntent().getExtras().getString("connectedUserPseudo"));
                    createExercise.putExtra("patientId",getActivity().getIntent().getExtras().getInt("connectedUserId"));
                    createExercise.putExtra("task","textes");
                    createExercise.putExtra("isNewExercise",true);
                    createExercise.putExtra("sessionId",getActivity().getIntent().getExtras().getInt("sessionId"));
                    startActivityForResult(createExercise,10000);
                }
            }
        });
        customExerciseButton=(Button) view.findViewById(R.id.customExercise);
        customExerciseButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                List<String> data = new ArrayList<String>();
                File dataPath;
                dataPath = new File("/storage/emulated/0/App/Recordings/"+getActivity().getIntent().getStringExtra("connectedUserPseudo"));
                data = new ArrayList(Arrays.asList(dataPath.list(new FilenameFilter() {
                    public boolean accept(File directory, String fileName) {
                        return fileName.endsWith(".wav") || fileName.endsWith(".mp3");
                    }
                })));
                if (data.size() > 30) {
                    Toast.makeText(getContext(),"Vous avez trop d'enregistrements. Veuillez en supprimer avant de faire un exercice",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent configureExercise = new Intent(getContext(), ConfigureExerciseActivity.class);
                    configureExercise.putExtra("patientPseudo",getActivity().getIntent().getExtras().getString("connectedUserPseudo"));
                    configureExercise.putExtra("patientId",getActivity().getIntent().getExtras().getInt("connectedUserId"));
                    configureExercise.putExtra("task","custom");
                    configureExercise.putExtra("isNewExercise",true);
                    configureExercise.putExtra("sessionId",getActivity().getIntent().getExtras().getInt("sessionId"));
                    startActivityForResult(configureExercise, 10000);
                }
            }
        });
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
