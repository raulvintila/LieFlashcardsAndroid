package com.raulvintila.app.lieflashcards.Communication;

import android.content.Context;

import com.raulvintila.app.lieflashcards.Adapters.CardRecyclerListAdapter;
import com.raulvintila.app.lieflashcards.RecyclerItems.DeckRecyclerViewItem;
import com.raulvintila.app.lieflashcards.Adapters.DecksRecyclerListAdapter;
import com.raulvintila.app.lieflashcards.Database.manager.IDatabaseManager;

import java.util.List;


public class CustomModel {

    public interface OnCustomStateListener {
        void stateChanged();
    }

    private String last_multimedia;
    private Long deckId;
    private int fromImage;
   // private boolean refresh;
    private DecksRecyclerListAdapter adapter;
    private static CustomModel mInstance;
    private OnCustomStateListener mListener;
    private int mState;
    private List<DeckRecyclerViewItem> mlist;
    private DeckRecyclerViewItem deckRecyclerViewItem;
    private IDatabaseManager databaseManager;
    private Context context;
    private Long deck_id;

    public void setFromImage(int fromImage){
        this.fromImage = fromImage;
    }

    public int getFromImage(){
        return fromImage;
    }
    public void setLast_multimedia(String last_multimedia) {
        this.last_multimedia = last_multimedia;
    }

    /*public void setRefresh(boolean refresh){
        this.refresh =refresh;
    }

    public boolean getRefresh(){
        return this.refresh;
    }*/
    public String getLast_multimedia() {
        return this.last_multimedia;
    }

    public void setDeck_id( Long deck_id){
        this.deck_id=deck_id;
    }

    public Long getDeck_id(){
        return deck_id;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    public void setDeckId(Long deckId) {
        this.deckId = deckId;
    }

    public Long getDeckId() {
        return this.deckId;
    }

    /*public void setDatabaseManager(IDatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }

    public IDatabaseManager getDatabaseManager(){
        return databaseManager;
    }*/

    public void setAdapter(DecksRecyclerListAdapter adapter) {
        this.adapter = adapter;
    }

    public DecksRecyclerListAdapter getAdapter() {
        return this.adapter;
    }

    private CustomModel() {
        mState = 0;
    }

    public void setList(List<DeckRecyclerViewItem> mlist){
        this.mlist = mlist;
    }

    public void setDeckRecyclerViewItem(DeckRecyclerViewItem deckRecyclerViewItem){
        this.deckRecyclerViewItem = deckRecyclerViewItem;
    }

    public List<DeckRecyclerViewItem> getList(){
        return mlist;
    }

    public DeckRecyclerViewItem getDeckRecyclerViewItem(){
        return deckRecyclerViewItem;
    }

    public static CustomModel getInstance() {
        if(mInstance == null) {
            mInstance = new CustomModel();
        }
        return mInstance;
    }

    public void setListener(OnCustomStateListener listener) {
        mListener = listener;
    }

  /*  public void changeState(boolean state) {
        if(mListener != null) {
            mState = state;
            notifyStateChange();
        }
    }
*/
    public void setmState(int mState){
        this.mState = mState;
        notifyStateChange();

    }

    public int getmState(){
        return mState;
    }


    private void notifyStateChange() {
        mListener.stateChanged();
    }
}
