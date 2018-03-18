package ceri.m1ilsen.applicationprojetm1.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ceri.m1ilsen.applicationprojetm1.R;
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
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
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
        mTextMessage = (TextView) view.findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
        //File file=new File("/src/java/mots.txt.txt");
        ReadFileWord();
        return view;
    }

    public void ReadFileWord(){

        btnLireMots=(Button) view.findViewById(R.id.button10);
        btnLireMots.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try {
                    InputStream fis = getResources().openRawResource(R.raw.mots);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader bufferedReader = new BufferedReader(isr);
                    String sb = "";
                    int position=0;
                    String t="";
                    // LineNumberReader lnr = new LineNumberReader(bufferedReader);
                    int linenumber = 0;
                    String mot="";
                    // Random random=new Random();
                    List<String> lines = new ArrayList<String>();

                    Intent intent = new Intent(view.getContext(), DoExerciceActivity.class);
                    while ((sb= bufferedReader.readLine()) != null){

                        lines.add(sb);

                    }
                    bufferedReader.close();
                    for (String m: lines  ) {
                        //System.out.println("chaine file: "+m);
                        intent.putExtra("Word", lines.get(i));
                        intent.putExtra("WordTotal", lines.size());
                        intent.putExtra("PositionWord", i+1);

                        startActivity(intent);

                    }
                    //i++;


                    //mot=lines.get(i);


                    // int index=random.nextInt(lines.size());

                        /*if (sb.equals(lines.get(index))) {
                            mot=sb;
                            position = linenumber;
                        }*/


                    // System.out.println("Total number of lines : " + linenumber+" la position est "+position+" le mot est "+mot);

                    // lnr.close();
                    //  bufferedReader.close();
                    //Transfere des données vers l'activité DoExerciceActivity


                } catch (IOException e) {
                    e.printStackTrace();
                }
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
