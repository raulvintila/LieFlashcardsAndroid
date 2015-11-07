package com.raulvintila.app.lieflashcards.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.raulvintila.app.lieflashcards.Activities.AddCardActivity;
import com.raulvintila.app.lieflashcards.Database.dao.DBCard;
import com.raulvintila.app.lieflashcards.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sendroiu on 8/13/15.
 */
public class CardRecyclerListAdapter extends RecyclerView.Adapter<CardRecyclerListAdapter.MyViewHolder> {

    private List<DBCard> mData;
    public Context mContext;
    private AdapterListener adapterListener;


    public CardRecyclerListAdapter(List<DBCard> list,Context context) {
        mData = new ArrayList<>(list);
        this.mContext = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final DBCard card_item = mData.get(position);

        if(mData.get(position).getDifficulty().split("_")[0].equals("audio") )
        {
            holder.question.setText("[Audio]");
        }
        else if (mData.get(position).getDifficulty().split("_")[0].equals("image"))
        {
            holder.question.setText("[Image]");

        }
        else
        {
            holder.question.setText(mData.get(position).getQuestion());
        }
        
        if(mData.get(position).getDifficulty().split("_")[1].equals("audio"))
        {
            holder.answer.setText("[Audio]");
        }
        else if (mData.get(position).getDifficulty().split("_")[1].equals("image"))
        {
            holder.answer.setText("[Image]");
        }
        else
        {
            holder.answer.setText(mData.get(position).getAnswer());
        }
        
        holder.view_holder_listener = new MyViewHolder.ViewHolderListener() {
            @Override
            public void onSelectionLongClicked(int position) {
                adapterListener.onModelObjectClicked(card_item, position);
            }
            @Override
            public void onSelectionClicked(int position){
                Intent intent = new Intent(mContext, AddCardActivity.class);
                intent.putExtra("card_id",mData.get(position).getId());
                mContext.startActivity(intent);
            }
        };
        holder.question_bind(card_item);
        holder.answer_bind(card_item);


    }

    @Override
    public int getItemCount() { return mData.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView question;
        private TextView answer;
        private LinearLayout card_linear_layout;
        public ViewHolderListener view_holder_listener;



        public MyViewHolder(View v) {
            super(v);

            question = (TextView) v.findViewById(R.id.question);
            answer = (TextView) v.findViewById(R.id.answer);
            card_linear_layout = (LinearLayout) v.findViewById(R.id.card_linear_layout);
            card_linear_layout.setOnClickListener(this);
            card_linear_layout.setOnLongClickListener(this);;
        }

        public void question_bind(DBCard card_item) {
            if(card_item.getDifficulty().split("_")[0].equals("audio"))
            {
                question.setText("[Audio]");
            }
            else if (card_item.getDifficulty().split("_")[0].equals("image"))
            {
                question.setText("[Image]");
            }
            else
            {
                question.setText(card_item.getQuestion());
            }



        }

        public void answer_bind(DBCard card_item) {
            if(card_item.getDifficulty().split("_")[1].equals("audio"))
            {
                answer.setText("[Audio]");
            }
            else if (card_item.getDifficulty().split("_")[1].equals("image"))
            {
                answer.setText("[Image]");
            }
            else
            {
                answer.setText(card_item.getAnswer());
            }
        }

        @Override
        public void onClick(View v) {
            view_holder_listener.onSelectionClicked(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            view_holder_listener.onSelectionLongClicked(getAdapterPosition());
            return true;
        }

        public interface ViewHolderListener {
            void onSelectionLongClicked(int position);
            void onSelectionClicked(int position);
        }


    }

    public void setAdapterListener(AdapterListener listener) {
        this.adapterListener = listener;
    }

    public interface AdapterListener {

        void onModelObjectClicked(DBCard item, int position);
    }
    

    public void animateTo(List<DBCard> card_items) {
        applyAndAnimateRemovals(card_items);
        applyAndAnimateAdditions(card_items);
        applyAndAnimateMovedItems(card_items);
    }

    private void applyAndAnimateRemovals(List<DBCard> new_card_items) {
        for (int i = mData.size() - 1; i >= 0; i--) {
            final DBCard card_item = mData.get(i);
            if (!new_card_items.contains(card_item)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<DBCard> new_card_items) {
        for (int i = 0, count = new_card_items.size(); i < count; i++) {
            final DBCard card_item = new_card_items.get(i);
            if (!mData.contains(card_item)) {
                addItem(i, card_item);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<DBCard> new_card_items) {
        for (int toPosition = new_card_items.size() - 1; toPosition >= 0; toPosition--) {
            final DBCard card_item = new_card_items.get(toPosition);
            final int fromPosition = mData.indexOf(card_item);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public DBCard removeItem(int position) {
        final DBCard card_item = mData.remove(position);
        notifyItemRemoved(position);
        return card_item;
    }

    public void addItem(int position, DBCard card_item) {
        mData.add(position, card_item);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final DBCard card_item = mData.remove(fromPosition);
        mData.add(toPosition, card_item);
        notifyItemMoved(fromPosition, toPosition);
    }
}
