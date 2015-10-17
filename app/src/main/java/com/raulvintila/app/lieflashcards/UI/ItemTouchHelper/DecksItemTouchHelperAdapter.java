package com.raulvintila.app.lieflashcards.UI.ItemTouchHelper;


public interface DecksItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position, String action_type);
}
