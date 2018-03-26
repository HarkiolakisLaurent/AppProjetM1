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
import ceri.m1ilsen.applicationprojetm1.R;
import ceri.m1ilsen.applicationprojetm1.ui.DropFilesActivity;
import ceri.m1ilsen.applicationprojetm1.ui.EditClinicianProfileActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClinicianSettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClinicianSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClinicianSettingsFragment extends Fragment {

    private Button editProfileButton;
    private Button dropFilesButton;
    private OnFragmentInteractionListener mListener;

    public ClinicianSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ClinicianSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClinicianSettingsFragment newInstance(String param1, String param2) {
        ClinicianSettingsFragment fragment = new ClinicianSettingsFragment();
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
        final View view = inflater.inflate(R.layout.fragment_clinician_settings, container, false);
        editProfileButton = (Button) view.findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfile = new Intent(view.getContext(), EditClinicianProfileActivity.class);
                editProfile.putExtra("connectedUserMail",getActivity().getIntent().getStringExtra("connectedUserMail"));
                editProfile.putExtra("connectedUserPseudo",getActivity().getIntent().getStringExtra("connectedUserPseudo"));
                editProfile.putExtra("connectedUserId",getActivity().getIntent().getExtras().getInt("connectedUserId"));
                startActivityForResult(editProfile,10001);
            }
        });
        dropFilesButton = (Button) view.findViewById(R.id.dropFilesButton);
        dropFilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dropFiles = new Intent(view.getContext(), DropFilesActivity.class);
                startActivity(dropFiles);
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
        if ((resultCode == 10001)/* && (resultCode == Activity.RESULT_OK)*/) {
            // mettre à jour les infos courantes
            getActivity().getIntent().putExtra("connectedUserMail",data.getStringExtra("connectedUserMail"));
            getActivity().getIntent().putExtra("connectedUserPseudo",data.getStringExtra("connectedUserPseudo"));
        }
    }
}
