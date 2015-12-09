package com.minimize.android.rxrecycleradapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rx.subjects.PublishSubject;

/**
 * Created by ahmedrizwan on 09/12/2015.
 */
public class RxAdapter<T, V extends ViewDataBinding> extends RecyclerView.Adapter<RecyclerViewHolder> {

    private int mItem_layout;
    private List<T> mDataSet;

    private PublishSubject<Item> mPublishSubject;

    public RxAdapter(@LayoutRes final int item_layout, final List<T> dataSet) {
        mItem_layout = item_layout;
        mDataSet = dataSet;
        mPublishSubject = PublishSubject.create();
    }

    public rx.Observable<Item> asObservable(){
        return mPublishSubject.asObservable();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(final ViewGroup parent,
                                                 final int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(mItem_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        mPublishSubject.onNext(new Item((V) holder.mViewDataBinding, mDataSet.get(position), position));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return super.getItemViewType(position);
    }

    public List<T> getDataSet() {
        return mDataSet;
    }

    public class Item {

        public V getViewDataBinding() {
            return mViewDataBinding;
        }

        public T getItem() {
            return mItem;
        }

        private final V mViewDataBinding;
        private final T mItem;
        private final int mPosition;


        public Item(final V viewHolder, final T item, final int position) {
            mViewDataBinding = viewHolder;
            mItem = item;
            mPosition = position;
        }

        public int getPosition() {
            return mPosition;
        }
    }
}
