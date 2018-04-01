package com.minimize.android.rxrecycleradapter


import android.support.v7.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.functions.Function
import java.util.*


/**
 * Created by ahmedrizwan on 26/12/2015.
 */
class RxDataSourceSectioned<DataType>(var mDataSet: List<DataType>, val mViewHolderInfoList: List<ViewHolderInfo>, val mViewTypeCallback: OnGetItemViewType) {
    /***
     * Call this if you need access to the Adapter!
     * Warning: might return null!
     *
     * @return RxAdapter Instance
     */
    private val rxAdapter: RxAdapterForTypes<DataType> = RxAdapterForTypes(mDataSet, mViewHolderInfoList, mViewTypeCallback)

    /***
     * Call this when you want to bind with multiple Item-Types
     *
     * @param recyclerView RecyclerView instance
     * @param viewHolderInfoList List of ViewHolderInfos
     * @param viewTypeCallback Callback that distinguishes different view Item-Types
     * @return Observable for binding viewHolder
     */
    fun bindRecyclerView(recyclerView: RecyclerView): Observable<TypesViewHolder<DataType>> {
        recyclerView.adapter = rxAdapter
        return rxAdapter.asObservable()
    }

    /***
     * For setting base dataSet
     */
    fun updateDataSet(dataSet: List<DataType>): RxDataSourceSectioned<DataType> {
        mDataSet = dataSet
        return this
    }

    fun asObservable(): Observable<TypesViewHolder<DataType>> {
        return rxAdapter.asObservable()
    }

    /***
     * For updating Adapter
     */
    fun updateAdapter() {
        //update the update
        rxAdapter.updateDataSet(mDataSet)
    }

    // Transformation methods

    fun map(mapper: Function<in DataType, out DataType>): RxDataSourceSectioned<DataType> {
        mDataSet = Observable.fromIterable(mDataSet).map(mapper).toList().blockingGet()
        rxAdapter.updateDataSet(mDataSet)
        return this
    }

    fun filter(predicate: (DataType) -> Boolean): RxDataSourceSectioned<DataType> {
        mDataSet = Observable.fromIterable(mDataSet).filter(predicate).toList().blockingGet()
        rxAdapter.updateDataSet(mDataSet)
        return this
    }

    fun last(): RxDataSourceSectioned<DataType> {
        mDataSet = listOf(Observable.fromIterable(mDataSet).blockingLast())
        return this
    }

    fun lastOrDefault(defaultValue: DataType): RxDataSourceSectioned<DataType> {
        mDataSet = Observable.fromIterable(mDataSet)
                .takeLast(1)
                .defaultIfEmpty(defaultValue)
                .toList()
                .blockingGet()
        return this
    }

    fun limit(count: Int): RxDataSourceSectioned<DataType> {
        mDataSet = Observable.fromIterable(mDataSet).take(count.toLong()).toList().blockingGet()
        return this
    }

    fun repeat(count: Long): RxDataSourceSectioned<DataType> {
        val dataSet = mDataSet
        mDataSet = Observable.fromIterable(dataSet).repeat(count).toList().blockingGet()
        return this
    }

    fun empty(): RxDataSourceSectioned<DataType> {
        mDataSet = Collections.emptyList<DataType>()
        return this
    }

    fun concatMap(func: (DataType) -> Observable<out DataType>): RxDataSourceSectioned<DataType> {
        mDataSet = Observable.fromIterable(mDataSet).concatMap(func).toList().blockingGet()
        return this
    }

    fun concatWith(observable: Observable<out DataType>): RxDataSourceSectioned<DataType> {
        mDataSet = Observable.fromIterable(mDataSet).concatWith(observable).toList().blockingGet()
        return this
    }

    fun distinct(): RxDataSourceSectioned<DataType> {
        mDataSet = Observable.fromIterable(mDataSet).distinct().toList().blockingGet()
        return this
    }

    fun elementAt(index: Long): RxDataSourceSectioned<DataType> {
        mDataSet = listOf(Observable.fromIterable(mDataSet).elementAt(index).blockingGet())
        return this
    }

    fun elementAtOrDefault(index: Long, defaultValue: DataType): RxDataSourceSectioned<DataType> {
        mDataSet = listOf(Observable.fromIterable(mDataSet).elementAt(index, defaultValue)
                .blockingGet())
        return this
    }

    fun first(defaultItem: DataType): RxDataSourceSectioned<DataType> {
        mDataSet = listOf(Observable.fromIterable(mDataSet).first(defaultItem).blockingGet())
        return this
    }

    fun flatMap(func: (DataType) -> Observable<out DataType>): RxDataSourceSectioned<DataType> {
        mDataSet = Observable.fromIterable(mDataSet).flatMap(func).toList().blockingGet()
        return this
    }

    fun reduce(initialValue: DataType, reducer: (DataType, DataType) -> DataType): RxDataSourceSectioned<DataType> {
        mDataSet = listOf(Observable.fromIterable(mDataSet).reduce(initialValue, reducer).blockingGet())
        return this
    }

    fun take(count: Long): RxDataSourceSectioned<DataType> {
        mDataSet = Observable.fromIterable(mDataSet).take(count).toList().blockingGet()
        return this
    }

}