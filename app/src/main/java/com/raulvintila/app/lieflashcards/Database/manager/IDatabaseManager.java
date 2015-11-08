package com.raulvintila.app.lieflashcards.Database.manager;

import com.raulvintila.app.lieflashcards.Database.dao.DBCard;
import com.raulvintila.app.lieflashcards.Database.dao.DBCardContent;
import com.raulvintila.app.lieflashcards.Database.dao.DBCardProgress;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.Database.dao.DBUser;
import com.raulvintila.app.lieflashcards.Database.dao.DBUserDeck;

import java.util.List;


/**
 * Interface that provides methods for managing the database inside the Application.
 *
 * @author Octa
 */
public interface IDatabaseManager {

    /**
     * Closing available connections
     */
    public void closeDbConnections();

    /**
     * Delete all tables and content from our database
     */
    public void dropDatabase();

    public DBDeck getDeckById(Long deckId);

    public DBUserDeck getUserDeckByDeckId(Long deckId);

    public List<DBDeck> getAllDecks();

    public void deleteDecks();

    public DBDeck insertDeck(DBDeck deck);

    public DBUserDeck insertUserDeck(DBUserDeck userDeck);

    public DBCardProgress insertCardProgress(DBCardProgress cardProgress);

    public DBCard getCardById(Long card_id);

    public DBCardProgress getCardProgressById(Long card_id);

    public DBCardContent insertOrUpdateCardContent(DBCardContent cardContent);

    public DBCardProgress insertOrUpdateCardProgress(DBCardProgress cardProgress);

    /**
     * Insert a user into the DB
     *
     * @param user to be inserted
     */
    public DBUser insertUser(DBUser user);

    public List<DBCard> getAllCards();

    public DBCard insertOrUpdateCard(DBCard card);

    public DBDeck insertOrUpdateDeck(DBDeck deck);

    public DBUserDeck insertOrUpdateUserDeck(DBUserDeck userDeck);

    public boolean deleteDeckById(Long deckId);

    public boolean deleteCardById(Long cardId);

    public DBCard insertCard(DBCard card);

    public DBCardContent getCardContentByCardId(Long card_id);

    public List<DBCard> getCardsByDeckId(Long deckId);

    /**
     * List all the users from the DB
     *
     * @return list of users
     */
    public List<DBUser> listUsers();

    /**
     * Update a user from the DB
     *
     * @param user to be updated
     */
    public void updateUser(DBUser user);

    /**
     * Delete all users with a certain email from the DB
     *
     * @param email of users to be deleted
     */
    public void deleteUserByEmail(String email);

    /**
     * Delete a user with a certain id from the DB
     *
     * @param userId of users to be deleted
     */
    public boolean deleteUserById(Long userId);

    /**
     * @param userId - of the user we want to fetch
     * @return Return a user by its id
     */
    DBUser getUserById(Long userId);

    /**
     * Delete all the users from the DB
     */
    public void deleteUsers();

}
