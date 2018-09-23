package com.example.albert.dotasearch;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RVEmptyObserver extends RecyclerView.AdapterDataObserver {
    private View emptyView;
    private View recyclerViewWrapper;
    private RecyclerView recyclerView;

    /**
     * Constructor to set an Empty View for the RV
     */
    public RVEmptyObserver(RecyclerView recyclerView, View emptyView, View recyclerViewWrapper) {
        this.recyclerView = recyclerView;
        this.emptyView = emptyView;
        this.recyclerViewWrapper = recyclerViewWrapper;
        checkIfEmpty();
    }

    /**
     * Check if Layout is empty and show the appropriate view
     */
    private void checkIfEmpty() {
        if (emptyView != null && recyclerView.getAdapter() != null/* && count > 0*/) {
            boolean emptyViewVisible = recyclerView.getAdapter().getItemCount() == 0;
            emptyView.setVisibility(emptyViewVisible ? View.VISIBLE : View.GONE);
            recyclerViewWrapper.setVisibility(emptyViewVisible ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Abstract method implementations
     */
    @Override
    public void onChanged() {
        checkIfEmpty();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        checkIfEmpty();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        checkIfEmpty();
    }
}
