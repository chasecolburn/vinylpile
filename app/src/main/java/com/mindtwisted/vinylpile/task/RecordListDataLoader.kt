package com.mindtwisted.vinylpile.task

import android.content.Context
import androidx.loader.content.AsyncTaskLoader
import com.mindtwisted.vinylpile.helper.QueryHelper
import com.mindtwisted.vinylpile.model.Record

class RecordListDataLoader(context: Context) : AsyncTaskLoader<List<Record>>(context) {

    private var _records: List<Record>? = null

    override fun loadInBackground(): List<Record>? {
        return QueryHelper.fetchRecords(context)
    }

    override fun deliverResult(records: List<Record>?) {
        _records = records
        if (isStarted) {
            super.deliverResult(records)
        }
    }

    override fun onStartLoading() {
        if (_records != null) {
            deliverResult(_records)
        }
        if (takeContentChanged() || _records == null) {
            forceLoad()
        }
    }

    override fun onStopLoading() {
        super.onStopLoading()
        cancelLoad()
    }

    override fun onReset() {
        onStopLoading()
        _records = null
    }
}