package com.esmaeel.anim.Utils;


import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = "EndlessScrollListener";

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    //    private int visibleThreshold = 1; // The minimum amount of items to have below your current scroll position before loading more.
    private int visibleThreshold = 1; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount, currentVisiableItem;
    public int findLastCompeletlyVisiblePosition = 0;
    public int currentPage = 1; // lets make it public to access it anywhere
    private boolean isLastItemDisplaying = false;

    RecyclerViewPositionHelper mRecyclerViewHelper;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        mRecyclerViewHelper = new RecyclerViewPositionHelper(recyclerView);
        findLastCompeletlyVisiblePosition = mRecyclerViewHelper.findLastCompletelyVisibleItemPosition();
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mRecyclerViewHelper.getItemCount();
        firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
        currentVisiableItem = mRecyclerViewHelper.findLastVisibleItemPosition();
        isLastItemDisplaying = mRecyclerViewHelper.isLastItemDisplaying(recyclerView);
        positionChange(currentVisiableItem);
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached
            // Do something
            currentPage++;
            onLoadMore(currentPage, currentVisiableItem);
            loading = true;
        }
    }

    //Start loading
    public abstract void onLoadMore(int currentPage, int visibleItemPosition);

    public abstract void positionChange(int lastVisibleItem);


    public class RecyclerViewPositionHelper {

        final RecyclerView recyclerView;
        final RecyclerView.LayoutManager layoutManager;

        RecyclerViewPositionHelper(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            this.layoutManager = recyclerView.getLayoutManager();
        }

        /**
         * Returns the adapter item count.
         *
         * @return The total number on items in a layout manager
         */
        public int getItemCount() {
            return layoutManager == null ? 0 : layoutManager.getItemCount();
        }

        /**
         * Returns the adapter position of the first visible view. This position does not include
         * adapter changes that were dispatched after the last layout pass.
         *
         * @return The adapter position of the first visible item or {@link RecyclerView#NO_POSITION} if
         * there aren't any visible items.
         */
        public int findFirstVisibleItemPosition() {
            final View child = findOneVisibleChild(0, layoutManager.getChildCount(), false, true);
            return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
        }

        /**
         * Returns the adapter position of the first fully visible view. This position does not include
         * adapter changes that were dispatched after the last layout pass.
         *
         * @return The adapter position of the first fully visible item or
         * {@link RecyclerView#NO_POSITION} if there aren't any visible items.
         */
        public int findFirstCompletelyVisibleItemPosition() {
            final View child = findOneVisibleChild(0, layoutManager.getChildCount(), true, false);
            return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
        }

        /**
         * Returns the adapter position of the last visible view. This position does not include
         * adapter changes that were dispatched after the last layout pass.
         *
         * @return The adapter position of the last visible view or {@link RecyclerView#NO_POSITION} if
         * there aren't any visible items
         */
        public int findLastVisibleItemPosition() {
            final View child = findOneVisibleChild(layoutManager.getChildCount() - 1, -1, false, true);
            return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
        }

        public boolean isLastItemDisplaying(RecyclerView recyclerView) {
            if (recyclerView.getAdapter().getItemCount() != 0) {
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                    return true;
            }
            return false;
        }

        /**
         * Returns the adapter position of the last fully visible view. This position does not include
         * adapter changes that were dispatched after the last layout pass.
         *
         * @return The adapter position of the last fully visible view or
         * {@link RecyclerView#NO_POSITION} if there aren't any visible items.
         */
        public int findLastCompletelyVisibleItemPosition() {
            final View child = findOneVisibleChild(layoutManager.getChildCount() - 1, -1, true, false);
            return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
        }

        View findOneVisibleChild(int fromIndex, int toIndex, boolean completelyVisible,
                                 boolean acceptPartiallyVisible) {
            OrientationHelper helper;
            if (layoutManager.canScrollVertically()) {
                helper = OrientationHelper.createVerticalHelper(layoutManager);
            } else {
                helper = OrientationHelper.createHorizontalHelper(layoutManager);
            }

            final int start = helper.getStartAfterPadding();
            final int end = helper.getEndAfterPadding();
            final int next = toIndex > fromIndex ? 1 : -1;
            View partiallyVisible = null;
            for (int i = fromIndex; i != toIndex; i += next) {
                final View child = layoutManager.getChildAt(i);
                final int childStart = helper.getDecoratedStart(child);
                final int childEnd = helper.getDecoratedEnd(child);
                if (childStart < end && childEnd > start) {
                    if (completelyVisible) {
                        if (childStart >= start && childEnd <= end) {
                            return child;
                        } else if (acceptPartiallyVisible && partiallyVisible == null) {
                            partiallyVisible = child;
                        }
                    } else {
                        return child;
                    }
                }
            }
            return partiallyVisible;
        }


    }
}