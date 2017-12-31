package com.flatbrains.sushigoal.activities.playerselection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.flatbrains.sushigoal.R;
import com.flatbrains.sushigoal.activities.game.GameActivity;
import com.flatbrains.sushigoal.database.dao.PlayerDAO;
import com.flatbrains.sushigoal.database.model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerSelectionActivity extends AppCompatActivity {

    private Spinner knownPlayerSpinner;
    private PlayerDAO playerDAO;
    private Button addNewPlayerButton;
    private Button addKnowPlayerButton;
    private ListView playersListView;
    private List<Player> playersList = new ArrayList<>();
    private Button playButton;
    ArrayAdapter<Player> adapterSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_selection);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (playerDAO == null) {
            playerDAO = new PlayerDAO(this);
            playerDAO.open();
        }

        adapterSpinner = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, playersList);
        playersListView = findViewById(R.id.playerList);
        playersListView.setAdapter(adapterSpinner);
        knownPlayerSpinner = findViewById(R.id.knownPlayer);
        addKnowPlayersOnSpinner();

        /*
         *  Ajout nouveau joueur
         */
        addNewPlayerButton = findViewById(R.id.addNewPlayer);
        addNewPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText newPlayerName = findViewById(R.id.newplayer);
                long idPlayer;
                if (playerDAO.existPlayer(newPlayerName.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), R.string.exist_player, Toast.LENGTH_LONG).show();
                } else {
                    idPlayer = playerDAO.createPlayer(newPlayerName.getText().toString().trim());
                    playersList.add(playerDAO.getPlayerById(idPlayer));
                    newPlayerName.getText().clear();// clear name
                    adapterSpinner.notifyDataSetChanged();
                }

            }
        });

        /*
         * Ajout joueur déjà connu
         */
        addKnowPlayerButton = findViewById(R.id.addKnownPlayer);
        addKnowPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playersList.add(playerDAO.getPlayerByName(knownPlayerSpinner.getSelectedItem().toString()));
                adapterSpinner.notifyDataSetChanged();
            }
        });

        /*
         * Lancement partie
         */
        playButton = findViewById(R.id.play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPlay = new Intent(getBaseContext(), GameActivity.class);
                ArrayList<String> playersNameList = new ArrayList<>(playersList.size());
                for (Player player : playersList) {
                    playersNameList.add(player.getName());
                }
                toPlay.putStringArrayListExtra("players", playersNameList);
                startActivity(toPlay);

            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerDAO.close();
    }

    public void addKnowPlayersOnSpinner() {
        List<Player> knownPlayers = playerDAO.getAllPlayers();
        ArrayAdapter<Player> dataAdapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, knownPlayers);
        knownPlayerSpinner.setAdapter(dataAdapter);
    }


}
