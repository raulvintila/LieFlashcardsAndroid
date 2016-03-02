package com.raulvintila.app.lieflashcards.Fragments;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.raulvintila.app.lieflashcards.Communication.CustomModel;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.Database.manager.IDatabaseManager;
import com.raulvintila.app.lieflashcards.MyApplication;
import com.raulvintila.app.lieflashcards.R;

public class SpacedLearningFragment extends Fragment {

    private IDatabaseManager databaseManager;
    private View mFragmentView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseManager = ((MyApplication)getActivity().getApplicationContext()).databaseManager;
        mFragmentView = inflater.inflate(R.layout.spaced_learning, container, false);

        generateDueTodayStats();

        return mFragmentView;
    }

    public void generateDueTodayStats()
    {
        DBDeck deck = databaseManager.getDeckById(CustomModel.getInstance().getDeckId());

        long deck_cards_per_day = deck.getNumber_of_cards_per_day();
        String text;

        TextView deck_total_new_cards = (TextView)mFragmentView.findViewById(R.id.total_new_cards_stats);
        Double new_cards = deck.getNumber_of_cards_per_day() * 0.75;
        text = "<font color =#B0171F>" + new_cards.intValue() + "</font>";
        deck_total_new_cards.setText(Html.fromHtml(text));


        TextView deck_total_cards = (TextView)mFragmentView.findViewById(R.id.total_cards_stats);
        text = "<font color =#009900>" + (deck_cards_per_day - new_cards.intValue()) + "</font>";
        deck_total_cards.setText(Html.fromHtml(text));

        TextView deck_due_today_stats = (TextView) mFragmentView.findViewById(R.id.due_today_stats);
        text = "<font color=#1976D2>" + deck_cards_per_day + "</font>";
        deck_due_today_stats.setText(Html.fromHtml(text));

        TextView estimated_time = (TextView) mFragmentView.findViewById(R.id.estimated_time_stats);
        String estimated_time_string = (int)((new_cards * 47 + (deck_cards_per_day - new_cards) * 34) / 60) + "";
        text = "<font color=#000000>" + estimated_time_string + "</font>";
        estimated_time.setText(Html.fromHtml(text));
    }
}
