package com.raulvintila.app.lieflashcards;

import com.raulvintila.app.lieflashcards.Database.dao.DBCard;
import com.raulvintila.app.lieflashcards.Database.dao.DBCardContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class CardContentListFactory {

    private static Map<String, String> cardTypes = new HashMap<>();
    static
    {
        cardTypes.put("QA", "1,1_1,4_4,1_4,4;5,1_5,4_8,1_8,4");
    }

    public static List<DBCardContent> create(DBCard card, String version,
                                             List<String> values, List<String> types)
    {
        List<DBCardContent> cardContents = new ArrayList<>();
        DBCardContent cardContent;

        StringTokenizer tok = new StringTokenizer(card.getLayoutType(), "_");
        while (tok.hasMoreElements())
        {
            cardContent = new DBCardContent();
            cardContent.setName(tok.nextElement().toString());
            cardContent.setValue(values.remove(0));
            cardContent.setType(types.remove(0));
            cardContent.setVersion(version);
            cardContent.setCardId(card.getId());
            cardContents.add(cardContent);
        }
        return cardContents;
    }
}
