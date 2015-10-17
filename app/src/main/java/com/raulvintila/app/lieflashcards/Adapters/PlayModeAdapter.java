package com.raulvintila.app.lieflashcards.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.raulvintila.app.lieflashcards.DeckModeChange;
import com.raulvintila.app.lieflashcards.PlayMode;
import com.raulvintila.app.lieflashcards.PlayModeController;
import com.raulvintila.app.lieflashcards.R;

import java.util.List;

import de.greenrobot.event.EventBus;

public class PlayModeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<PlayMode> mData;
    public boolean ok = false;
    PlayModeController controller;
    private String default_mode;

    public PlayModeAdapter(List<PlayMode> list) {
        mData = new ArrayList<PlayMode>();
        controller = new PlayModeController(mData, this);
        default_mode = "StudyNormal";
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.play_submode, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (mData.get(position).getType().equals("submode")) {

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)((MyViewHolder)holder).textView.getLayoutParams();
            params.setMargins(26,0,0,0);
            ((MyViewHolder)holder).textView.setLayoutParams(params);
            ((MyViewHolder)holder).textView.setTextColor(Color.RED);

            ((MyViewHolder) holder).da = new MyViewHolder.terface() {
                @Override
                public void doAction(int position) {
                    String main_mode;
                    PlayMode aux = mData.get(position);
                    int i = 1;
                    while (aux.getType().equals("submode")) {
                        aux = mData.get(position - i);
                        i++;
                    }
                    EventBus.getDefault().post(new DeckModeChange(aux.getText(),mData.get(position).getText()));
                }
            };
        }
        else {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)((MyViewHolder)holder).textView.getLayoutParams();
            params.setMargins(13, 0, 13, 0);
            ((MyViewHolder)holder).textView.setLayoutParams(params);
            // #ChangeThis , this should take the colorPrimary, not be hardcoded
            ((MyViewHolder)holder).textView.setTextColor(Color.parseColor("#ff1ca337"));

            ((MyViewHolder) holder).da = new MyViewHolder.terface() {
                @Override
                public void doAction(int position) {
                    controller.doAction(position);
                }
            };

        }
        ((MyViewHolder) holder).textView.setText(mData.get(position).getText());
    }

    @Override
    public int getItemCount() { return mData.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        public terface da;
        public boolean expanded;

        public MyViewHolder(View v) {
            super(v);

            expanded = false;
            RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.rl);
            textView = (TextView) v.findViewById(R.id.item_text);
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    da.doAction(getAdapterPosition());
                }
            });
        }

        public interface terface {
            void doAction(int position);
        }

    }
}
