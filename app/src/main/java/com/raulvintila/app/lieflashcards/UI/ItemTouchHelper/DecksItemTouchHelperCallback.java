package com.raulvintila.app.lieflashcards.UI.ItemTouchHelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.animation.Interpolator;

import com.nineoldandroids.view.ViewHelper;
import com.raulvintila.app.lieflashcards.Adapters.DecksRecyclerListAdapter;
import com.raulvintila.app.lieflashcards.R;


public class DecksItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final DecksItemTouchHelperAdapter mAdapter;
    private int mCachedMaxScrollSpeed = -1;
    private Context mContext;
    private String swipe_action;

    public DecksItemTouchHelperCallback(DecksItemTouchHelperAdapter adapter, Context context) {
        mAdapter = adapter;
        mContext = context;
    }


    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        if (source.getItemViewType() != target.getItemViewType()) {
            return false;
        }

        // Notify the adapter of the move
        mAdapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        // Notify the adapter of the dismissal
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition(), swipe_action);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder){
        //getDefaultUIUtil().clearView(((DecksRecyclerListAdapter.MainViewHolder) viewHolder).swipeLayout);
        super.clearView(recyclerView, viewHolder);
        if (viewHolder instanceof DecksItemTouchHelperViewHolder) {
            // restore idle state
            DecksItemTouchHelperViewHolder itemViewHolder = (DecksItemTouchHelperViewHolder) viewHolder;
            itemViewHolder.onItemClear();
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            if (viewHolder != null) {
                getDefaultUIUtil().onSelected(((DecksRecyclerListAdapter.MainViewHolder) viewHolder).mPhoto);
                if (viewHolder instanceof DecksItemTouchHelperViewHolder) {
                    DecksItemTouchHelperViewHolder itemViewHolder = (DecksItemTouchHelperViewHolder) viewHolder;
                    itemViewHolder.onItemSelected();
                }
            }
        } else {
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG)
                super.onSelectedChanged(viewHolder, actionState);
            if (viewHolder != null) {
                //getDefaultUIUtil().onSelected(((DecksRecyclerListAdapter.MainViewHolder) viewHolder).mPhoto);
                if (viewHolder instanceof DecksItemTouchHelperViewHolder) {
                    DecksItemTouchHelperViewHolder itemViewHolder = (DecksItemTouchHelperViewHolder) viewHolder;
                    itemViewHolder.onItemSelected();
                }
            }
        }
    }

    @Override
    public void onChildDraw(final Canvas c, final RecyclerView recyclerView,
                            final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState,
                            final boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

            final int width = ((DecksRecyclerListAdapter.MainViewHolder)viewHolder).itemView.getWidth();

            if(dX < 0) { // swipping right
                ((DecksRecyclerListAdapter.MainViewHolder) viewHolder).v.setBackgroundResource(R.drawable.delete2);
                swipe_action = "delete";
            } else {
                ((DecksRecyclerListAdapter.MainViewHolder) viewHolder).v.setBackgroundResource(R.drawable.archive2);
                swipe_action = "archive";
            }
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
                if (viewHolder instanceof DecksRecyclerListAdapter.MainViewHolder) {
                    ((DecksRecyclerListAdapter.MainViewHolder) viewHolder).swipeLayout.setTranslationX(dX);
                }

                if(Math.abs(dX) >= width) {
                    final Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((DecksRecyclerListAdapter.MainViewHolder) viewHolder).swipeLayout.setTranslationX(0);
                        }
                    }, 100);

                }
            } else {
                if (viewHolder instanceof DecksRecyclerListAdapter.MainViewHolder) {
                    ViewHelper.setTranslationX(((DecksRecyclerListAdapter.MainViewHolder) viewHolder).swipeLayout, dX);

                    if(Math.abs(dX) >= width) {
                        final Handler handler1 = new Handler();
                        handler1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ViewHelper.setTranslationX(((DecksRecyclerListAdapter.MainViewHolder) viewHolder).swipeLayout,0);
                            }
                        }, 100);
                    }
                }
            }
        } else
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

    }



    @Override
    public int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int viewSize, int viewSizeOutOfBounds, int totalSize, long msSinceStartScroll) {

        int maxScroll = this.getMaxDragScroll(recyclerView);
        int absOutOfBounds = Math.abs(viewSizeOutOfBounds);
        int direction = (int) Math.signum((float) viewSizeOutOfBounds);
        float outOfBoundsRatio = Math.min(1.0F, 1.0F * (float) absOutOfBounds / (float) viewSize);
        int cappedScroll = (int) ((float) (direction * maxScroll) * sDragViewScrollCapInterpolator.getInterpolation(outOfBoundsRatio));
        float timeRatio;
        Log.d("TEST", "msSinceStartScroll: " + msSinceStartScroll);

        if (msSinceStartScroll > 1000L) {

            timeRatio = 1.0F;
            Log.d("TEST", "Upper 200l TIME RATION: " + timeRatio);
        } else {
            //change to 1000.0F
            timeRatio = (float) msSinceStartScroll / 500.0F;
            Log.d("TEST", "TIMERATION : " + timeRatio);
        }

        int value = (int) ((float) cappedScroll * sDragScrollInterpolator.getInterpolation(timeRatio));
        Log.d("TEST", "Value: " + value);
        return value == 0 ? (viewSizeOutOfBounds > 0 ? 1 : -1) : value;

    }


    private int getMaxDragScroll(RecyclerView recyclerView) {
        if (this.mCachedMaxScrollSpeed == -1) {
            this.mCachedMaxScrollSpeed = recyclerView.getResources().getDimensionPixelSize(android.support.v7.recyclerview.R.dimen.item_touch_helper_max_drag_scroll_per_frame);
        }

        return this.mCachedMaxScrollSpeed;
    }


    private static final Interpolator sDragScrollInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            return 2 * t;
//            return t * t;
//            return t * t * t * t * t;
        }
    };

    private static final Interpolator sDragViewScrollCapInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            --t;
            return t * t * t * t * t + 1.0F;
        }
    };
}
