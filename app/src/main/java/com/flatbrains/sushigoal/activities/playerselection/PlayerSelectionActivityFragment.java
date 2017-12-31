package com.flatbrains.sushigoal.activities.playerselection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flatbrains.sushigoal.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlayerSelectionActivityFragment extends Fragment {

    public PlayerSelectionActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player_selection, container, false);
    }
}
