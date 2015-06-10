package at.tba.treasurehunt.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import at.tba.treasurehunt.R;
import at.tba.treasurehunt.controller.AuthenticationController;
import at.tba.treasurehunt.controller.UserDataController;
import at.tba.treasurehunt.dataprovider.IUserLoadedCallback;
import at.tba.treasurehunt.dataprovider.UserDataProvider;
import at.tba.treasurehunt.utils.AlertHelper;
import data_structures.user.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserProfileDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserProfileDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileDialogFragment extends DialogFragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER_NAME = "username";
    private static final String RANK = "rank";
    private static final String XP = "xp";


    // TODO: Rename and change types of parameters
    private String username;
    private String rank;
    private String exp;

    private TextView f_userName;
    private TextView f_level;
    private TextView f_experience;

    private View rootView;
    private View mUserLayout;
    private View mProgressView;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userName
     * @param rank
     * @param experience
     * @return A new instance of fragment UserProfileDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileDialogFragment newInstance(String userName, String rank, String experience) {
        UserProfileDialogFragment fragment = new UserProfileDialogFragment();
        Bundle args = new Bundle();
        args.putString(USER_NAME, userName);
        args.putString(RANK, rank);
        args.putString(XP, experience);
        fragment.setArguments(args);
        return fragment;
    }

    public UserProfileDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(USER_NAME);
            rank = getArguments().getString(RANK);
            exp = getArguments().getString(XP);
        }else{
            dismiss();
        }
        setStyle(STYLE_NO_TITLE, getTheme());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_user_profile_dialog, container, false);
        initLayoutFields();
        fillUserData(username, rank, exp);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }



    private void fillUserData(String uName, String rank, String experience){
        f_userName.setText("Username: "+uName);
        f_level.setText("Rank: "+rank);
        f_experience.setText("Experience: "+experience);
    }


    private void initLayoutFields(){
        this.f_userName = (TextView) rootView.findViewById(R.id.fragment_profile_username);
        this.f_level = (TextView) rootView.findViewById(R.id.fragment_profile_level);
        this.f_experience = (TextView) rootView.findViewById(R.id.fragment_profile_experience);
        Button close = (Button) rootView.findViewById(R.id.close_user_fragment_dialog);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonCloseDialogClick(v);
            }
        });
    }

    public void onButtonCloseDialogClick(View v){
        dismiss();
    }

}
