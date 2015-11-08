package com.raulvintila.app.lieflashcards.Utils.Algorithms;


import com.raulvintila.app.lieflashcards.Database.dao.DBCard;
import com.raulvintila.app.lieflashcards.Database.dao.DBCardProgress;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class SpacedLearningAlgoUtils {

    private int rank5_size = 5;
    private int rank4_size = 5;
    private int rank3_size = 5;
    private int rank2_size = 5;
    private int rank1_size = 5;
    private int rank5_it = 0, rank4_it = 0, rank3_it = 0, rank2_it = 0, rank1_it = 0;

    public List<DBCard> getTodayList(List<DBCard> whole_list, int cards_per_day) {

        int cards_per_rank = cards_per_day / 5;
        List<DBCard> rank1 = new ArrayList<DBCard>();
        List<DBCard> rank2 = new ArrayList<DBCard>();
        List<DBCard> rank3 = new ArrayList<DBCard>();
        List<DBCard> rank4 = new ArrayList<DBCard>();
        List<DBCard> rank5 = new ArrayList<DBCard>();
        List<DBCard> today_list = new ArrayList<DBCard>();


        DBCard card, tmp_card;
        DBCardProgress cardProgress;

        // Find all the ready_to_study_cards and place them in the right bucket

        for (int i = 0; i < whole_list.size(); i++) {
           /* card = whole_list.get(i);
            if(readyToStudy(card)) {
                if (card.getCurrent_level() <= 1.5)
                    rank1.add(card);
                else if (card.getCurrent_level() <= 2.5)
                    rank2.add(card);
                else if (card.getCurrent_level() <= 3.5)
                    rank3.add(card);
                else if (card.getCurrent_level() <= 5)
                    rank4.add(card);
                else
                    rank5.add(card);
            }*/
        }

        // Sort the cards accordingly with their priority

        for (int i = 0; i < rank1.size() - 1; i++) {
            for (int j = i; j < rank1.size(); j++) {
                if (secondCardHigherPriorityThanFirst(rank1.get(i), rank1.get(j))) {
                    tmp_card = rank1.get(i);
                    rank1.set(i, rank1.get(j));
                    rank1.set(j, tmp_card);
                }
            }
        }

        for (int i = 0; i < rank2.size() - 1; i++) {
            for (int j = i; j < rank2.size(); j++) {
                if (secondCardHigherPriorityThanFirst(rank2.get(i), rank2.get(j))) {
                    tmp_card = rank2.get(i);
                    rank2.set(i, rank2.get(j));
                    rank2.set(j, tmp_card);
                }
            }
        }

        for (int i = 0; i < rank3.size() - 1; i++) {
            for (int j = i; j < rank3.size(); j++) {
                if (secondCardHigherPriorityThanFirst(rank3.get(i), rank3.get(j))) {
                    tmp_card = rank3.get(i);
                    rank3.set(i, rank3.get(j));
                    rank3.set(j, tmp_card);
                }
            }
        }

        for (int i = 0; i < rank4.size() - 1; i++) {
            for (int j = i; j < rank4.size(); j++) {
                if (secondCardHigherPriorityThanFirst(rank4.get(i), rank4.get(j))) {
                    tmp_card = rank4.get(i);
                    rank4.set(i, rank4.get(j));
                    rank4.set(j, tmp_card);
                }
            }
        }

        for (int i = 0; i < rank5.size() - 1; i++) {
            for (int j = i; j < rank5.size(); j++) {
                if (secondCardHigherPriorityThanFirst(rank5.get(i), rank5.get(j))) {
                    tmp_card = rank5.get(i);
                    rank5.set(i, rank5.get(j));
                    rank5.set(j, tmp_card);
                }
            }
        }

        for (int i = 0; i < cards_per_rank & i < rank1.size(); i++) {
            today_list.add(rank1.remove(0));
        }
        for (int i = 0; i < cards_per_rank & i < rank2.size(); i++) {
            today_list.add(rank2.remove(0));
        }
        for (int i = 0; i < cards_per_rank & i < rank3.size(); i++) {
            today_list.add(rank3.remove(0));
        }
        for (int i = 0; i < cards_per_rank & i < rank4.size(); i++) {
            today_list.add(rank4.remove(0));
        }
        for (int i = 0; i < cards_per_rank & i < rank5.size(); i++) {
            today_list.add(rank5.remove(0));
        }

        while (today_list.size() < cards_per_day) {
            if (rank5.size() > 0) {
                today_list.add(rank5.remove(0));
            } else if (rank4.size() > 0) {
                today_list.add(rank4.remove(0));
            } else if (rank3.size() > 0) {
                today_list.add(rank3.remove(0));
            } else if (rank2.size() > 0) {
                today_list.add(rank2.remove(0));
            } else if (rank1.size() > 0) {
                today_list.add(rank1.remove(0));
            } else {
                break;
            }
        }

        // this has to be rewritten
        /*for (int i = 0; i < 5; i++) {
            if (rank1.size() - 1 >= i)
                today_list.add(rank1.get(i));
            if (rank2.size() - 1 >= i)
                today_list.add(rank2.get(i));
            if (rank3.size() - 1 >= i)
                today_list.add(rank3.get(i));
            if (rank4.size() - 1 >= i)
                today_list.add(rank4.get(i));
            if (rank5.size() - 1 >= i)
                today_list.add(rank5.get(i));
        }*/

        Collections.shuffle(today_list);

        return today_list;
    }

    private boolean secondCardHigherPriorityThanFirst(DBCard first_card, DBCard second_card) {
        double first_card_days;
        double second_card_days;
        long first_card_priority;
        long second_card_priority;
       /* double first_card_level = first_card.getCurrent_level();
        double second_card_level = second_card.getCurrent_level();

        if (first_card_level <= 1)
            first_card_days = first_card_level * 1;
        else if (first_card_level <= 2)
            first_card_days = (first_card_level - 1) * 3;
        else if (first_card_level <= 3)
            first_card_days = (first_card_level - 2) * 5;
        else if (first_card_level <= 4)
            first_card_days = (first_card_level - 3) * 11;
        else if (first_card_level <= 5)
            first_card_days = (first_card_level - 4) * 20;
        else if (first_card_level <= 6)
            first_card_days = (first_card_level - 5) * 120;
        else
            first_card_days = (first_card_level - 6) * 360;


        if (second_card_level <= 1)
            second_card_days = second_card_level * 1;
        else if (second_card_level <= 2)
            second_card_days = (second_card_level - 1) * 3;
        else if (second_card_level <= 3)
            second_card_days = (second_card_level - 2) * 5;
        else if (second_card_level <= 4)
            second_card_days = (second_card_level - 3) * 11;
        else if (second_card_level <= 5)
            second_card_days = (second_card_level - 4) * 20;
        else if (second_card_level <= 6)
            second_card_days = (second_card_level - 5) * 120;
        else
            second_card_days = (second_card_level - 6) * 360;

        first_card_priority = first_card.getLast_study().getTime() + (long)(first_card_days * ( 24 * 60 * 60 * 1000));
        second_card_priority = second_card.getLast_study().getTime() + (long)(second_card_days * ( 24 * 60 * 60 * 1000));

        if (first_card_priority - second_card_priority > 0)
            return true;*/
        return false;
    }

    public boolean readyToStudy(DBCard card) {
       /* double days;
        double level = card.getCurrent_level();
        Calendar today = Calendar.getInstance();

        if (level <= 1)
            days = level * 1;
        else if (level <= 2)
            days = (level - 1) * 3;
        else if (level <= 3)
            days = (level - 2) * 5;
        else if (level <= 4)
            days = (level - 3) * 11;
        else if (level <= 5)
            days = (level - 4) * 20;
        else if (level <= 6)
            days = (level - 5) * 120;
        else
            days = (level - 6) * 360;

        if (today.getTimeInMillis() - (card.getLast_study().getTime() + days * ( 24 * 60 * 60 * 1000)) >=  -1 * ( 24 * 60 * 60 * 1000))
            return true;
*/
        return false;
    }

    public boolean readyToRestudy(DBCard card) {
       /* double days;
        double level = card.getCurrent_level();
        Calendar today = Calendar.getInstance();

        if (level <= 1)
            days = level * 1;
        else if (level <= 2)
            days = (level - 1) * 3;
        else if (level <= 3)
            days = (level - 2) * 5;
        else if (level <= 4)
            days = (level - 3) * 11;
        else if (level <= 5)
            days = (level - 4) * 20;
        else if (level <= 6)
            days = (level - 5) * 120;
        else
            days = (level - 6) * 360;

        double diff = today.getTimeInMillis() - (card.getLast_study().getTime() + days * ( 24 * 60 * 60 * 1000));

        if (diff <= ( 24 * 60 * 60 * 1000) && diff >= -( 24 * 60 * 60 * 1000)  )
            return true;*/

        return false;
    }

    public double updateLevel(double level, int choice, double volatility, int nr_repeats) {
      /*  switch (choice) {
            case 0:
                double d = getLevel(level, volatility);
                return d;
            case 1:
                if (level < 1)
                    return level + 0.75;
                else
                    return level + 1;
            case 2:
                return level + 2 * repeatModifier(nr_repeats);
        }*/
        return level;
    }

    public double repeatModifier(int nr_repeats) {
        switch (nr_repeats) {
            case 0:
                return 1;
            case 1:
                return 0.85;
            case 2:
                return 0.75;
            case 3:
                return 0.55;
            case 4:
                return 0.4;
            default:
                return 0.3;
        }
    }

    public double getLevel(double level, double volatility) {
        return (level - volatility)<0?0:(level - volatility);
    }

    public double getVolatility(double volatility, int nr_repeats) {

        double change;
        if (volatility >= 0.35)
            change = 0.2;
        else
            change = 0.1;

        switch(nr_repeats) {
            case 0:
                break;
            case 1:
                change *= 0.5;
                break;
            case 2:
                change *= 0.25;
                break;
            case 3:
                change *= 0;
                break;
            case 4:
                change *= -0.5;
                break;
            default:
                change *= -1;
        }
        if(volatility <= 0)
            return 0;
        else if(volatility >= 1)
            return 1;

        return volatility + change;
    }
}
