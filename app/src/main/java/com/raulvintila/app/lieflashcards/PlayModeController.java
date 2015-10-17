package com.raulvintila.app.lieflashcards;


import com.raulvintila.app.lieflashcards.Adapters.PlayModeAdapter;

import java.util.List;

public class PlayModeController {
    List<PlayMode> play_modes;
    private boolean sub_mode_priority;
    private int added_main_modes;
    private PlayModeAdapter adapter;

    public PlayModeController(List<PlayMode> list, PlayModeAdapter adapter) {
        this.adapter = adapter;

        play_modes = list;
        play_modes.add(new PlayMode("Study","main",2));
        play_modes.add(new PlayMode("Cram", "main",2));
        play_modes.add(new PlayMode("Test","main",2));
    }

    public List<PlayMode> getPlayModesList() { return this.play_modes; }


    public boolean getSubModePriority() {
        return this.sub_mode_priority;
    }

    public void doAction(int position) {
        PlayMode item = play_modes.get(position);

        if (item.getType().equals("main")) {
            doToggleExpand(item, position);
        } else if (item.getType().equals("submode")) {
            doSubModeAction(item);
        }
    }

    private void doToggleExpand(PlayMode play_mode, int position) {
        if (play_mode.getExpanded()) {
            for (int i = 0; i < play_mode.getChildren_number(); i++) {
                play_modes.remove(position + 1);
                adapter.notifyItemRemoved(position + 1);
                //adapter.notifyDataSetChanged();
            }
            play_mode.setExpanded(false);
        } else {
            adapter.ok = true;
            switch (play_mode.getText()) {
                case "Study":
                    for (int i = 0 ; i < play_mode.getChildren_number(); i++) {
                        play_modes.add(position + 1, play_mode.getChildren().get(i));
                        adapter.notifyItemInserted(position + 1);
                    }
                    break;
                case "Cram":
                    sub_mode_priority = true;
                    for (int i = 0 ; i < play_mode.getChildren_number(); i++) {
                        play_modes.add(position + 1, play_mode.getChildren().get(i));
                        adapter.notifyItemInserted(position + 1);
                    }
                    break;
                case "Test":
                    sub_mode_priority = true;
                    for (int i = 0 ; i < play_mode.getChildren_number(); i++) {
                        play_modes.add(position + 1, play_mode.getChildren().get(i));
                        adapter.notifyItemInserted(position + 1);
                    }
                    break;
            }
            play_mode.setExpanded(true);
        }
    }

    private void doSubModeAction(PlayMode play_mode) {

    }
}
