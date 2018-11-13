package com.kobyakov.d2s;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RVEmptyObserverForPlayerInfo extends RecyclerView.AdapterDataObserver {
    private View emptyView;
    private View recyclerViewWrapper;
    private RecyclerView recyclerView;

    public RVEmptyObserverForPlayerInfo(RecyclerView recyclerView, View emptyView, View recyclerViewWrapper) {
        this.recyclerView = recyclerView;
        this.emptyView = emptyView;
        this.recyclerViewWrapper = recyclerViewWrapper;
        checkIfEmpty();
    }

    private void checkIfEmpty() {
        if (emptyView != null && recyclerView.getAdapter() != null/* && count > 0*/) {
            boolean emptyViewVisible = recyclerView.getAdapter().getItemCount() == 0;
            emptyView.setVisibility(emptyViewVisible ? View.VISIBLE : View.GONE);
            recyclerViewWrapper.setVisibility(emptyViewVisible ? View.GONE : View.VISIBLE);
        }
    }

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
