package com.raulvintila.app.lieflashcards.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;
import com.nispok.snackbar.listeners.EventListener;
import com.raulvintila.app.lieflashcards.Activities.DeckActivity;
import com.raulvintila.app.lieflashcards.Activities.MainActivity;
import com.raulvintila.app.lieflashcards.Communication.CustomModel;
import com.raulvintila.app.lieflashcards.Database.dao.DBCard;
import com.raulvintila.app.lieflashcards.Database.dao.DBDeck;
import com.raulvintila.app.lieflashcards.Database.manager.IDatabaseManager;
import com.raulvintila.app.lieflashcards.SyncClasses.Deck;
import com.raulvintila.app.lieflashcards.UI.ItemTouchHelper.DecksItemTouchHelperAdapter;
import com.raulvintila.app.lieflashcards.UI.ItemTouchHelper.DecksItemTouchHelperViewHolder;
import com.raulvintila.app.lieflashcards.R;
import com.raulvintila.app.lieflashcards.RecyclerItems.DeckRecyclerViewItem;


import net.i2p.android.ext.floatingactionbutton.FloatingActionsMenu;

import java.util.List;

public class DecksRecyclerListAdapter extends RecyclerView.Adapter<DecksRecyclerListAdapter.MainViewHolder>
    implements DecksItemTouchHelperAdapter {

    private List<DeckRecyclerViewItem> mData;
    private IDatabaseManager databaseManager;

    Context mContext;
    RecyclerView mRecyclerView;

    public DecksRecyclerListAdapter(List<DeckRecyclerViewItem> data, Context context, IDatabaseManager databaseManager) {
        CustomModel.getInstance().setList(data);
        mData = data;
        mContext = context;
        this.databaseManager = databaseManager;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);

        MainViewHolder vh = new MainViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final MainViewHolder holder, int position) {

        holder.mPhoto.setImageResource(mData.get(position).getPhotoId());
        holder.mCards.setText(mData.get(position).getCards());
        holder.mName.setText(mData.get(position).getName());
        holder.mTime.setText(mData.get(position).getTime());
    }

    @Override
    public void onItemDismiss(final int position, String action_type) {
        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) ((MainActivity) mContext).findViewById(R.id.multiple_actions);

        final DBDeck deck = databaseManager.getDeckById(mData.get(position).getDeckId());

        final List<DBCard> card_list = deck.getCards();
        for (int i = 0; i < card_list.size(); i++) {
            databaseManager.deleteCardById(card_list.get(0).getId());
        }

        databaseManager.deleteDeckById(mData.get(position).getDeckId());

        final DeckRecyclerViewItem tmp_item = mData.remove(position);
        notifyItemRemoved(position);


        final String action = action_type + "d";
        SnackbarManager.show(
                Snackbar.with(mContext) // context
                        .text("Deck " + tmp_item.getName() + " has been " + action + "!")
                        .swipeToDismiss(true)
                        .actionLabel("Undo")
                        .eventListener(new EventListener() {
                            @Override
                            public void onShow(Snackbar snackbar) {
                                ViewHelper.setTranslationY(menuMultipleActions, -snackbar.getHeight());
                            }

                            @Override
                            public void onShowByReplace(Snackbar snackbar) {
                            }

                            @Override
                            public void onShown(Snackbar snackbar) {
                            }

                            @Override
                            public void onDismiss(Snackbar snackbar) {
                                final Handler handler1 = new Handler();
                                handler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ViewHelper.setTranslationY(menuMultipleActions, 0);
                                    }
                                }, 250);
                            }

                            @Override
                            public void onDismissByReplace(Snackbar snackbar) {
                            }

                            @Override
                            public void onDismissed(Snackbar snackbar) {

                            }
                        })
                        .actionListener(new ActionClickListener() {
                            @Override
                            public void onActionClicked(final Snackbar snackbar) {
                                mData.add(position, tmp_item);
                                notifyItemInserted(position);

                                //CustomModel.getInstance().ok = false;
                                final Handler handler1 = new Handler();

                                databaseManager.insertOrUpdateDeck(deck);

                                for (int i = 0; i < card_list.size(); i++) {
                                    databaseManager.insertOrUpdateCard(databaseManager.getCardById(card_list.get(0).getId()));
                                }

                                if (position == 0) {
                                    mRecyclerView.scrollToPosition(0);
                                } else if (position == mData.size() - 1) {
                                    mRecyclerView.scrollToPosition(getItemCount() - 1);
                                }

                                handler1.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ViewHelper.setTranslationY(menuMultipleActions, 0);
                                    }
                                }, 250);
                            }
                        })
                , (MainActivity) mContext);

    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Log.d(fromPosition + "",toPosition + "");
        DeckRecyclerViewItem prev = mData.remove(fromPosition);
        mData.add(toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onAttachedToRecyclerView (RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder
        implements DecksItemTouchHelperViewHolder,View.OnClickListener{

        public View v;
        public ImageView mPhoto;
        public ImageView mShare;
        public RelativeLayout swipeLayout;
        public TextView mCards;
        public TextView mName;
        public TextView mTime;
        private DeckRecyclerViewItem mDeckRecyclerViewItem;

        public MainViewHolder(View itemView) {
            super(itemView);

            v = itemView.findViewById(R.id.view);
            mPhoto = (ImageView) itemView.findViewById(R.id.photo);
            mShare = (ImageView) itemView.findViewById(R.id.share);
            mCards = (TextView) itemView.findViewById(R.id.cards);
            mName = (TextView) itemView.findViewById(R.id.name);
            mTime = (TextView) itemView.findViewById(R.id.time);
            swipeLayout = (RelativeLayout) itemView.findViewById(R.id.swipe_layout);
            swipeLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), DeckActivity.class);
            CustomModel.getInstance().setDeckRecyclerViewItem(CustomModel.getInstance().getList().get(this.getAdapterPosition()));
            CustomModel.getInstance().setDeckId(CustomModel.getInstance().getList().get(this.getAdapterPosition()).getDeckId());
            v.getContext().startActivity(intent);
        }

        @Override
        public void onItemSelected() {
            swipeLayout.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            swipeLayout.setBackgroundResource(R.drawable.layout_selector);
        }
    }
}
