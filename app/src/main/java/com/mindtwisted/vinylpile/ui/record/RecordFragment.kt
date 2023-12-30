package com.mindtwisted.vinylpile.ui.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.mindtwisted.vinylpile.R
import com.mindtwisted.vinylpile.model.Record
import com.mindtwisted.vinylpile.task.RecordListDataLoader

class RecordFragment : Fragment(), LoaderManager.LoaderCallbacks<List<Record>> {
//
//    private var _binding: FragmentDashboardBinding? = null
//
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!

    //*********************************************************
    // Fragment methods
    //*********************************************************
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoaderManager.getInstance(requireActivity()).initLoader(1, null, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                RecordListView(getViewModel())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }

    //*********************************************************
    // Loader methods
    //*********************************************************

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<Record>> {
        return RecordListDataLoader(requireContext())
    }

    override fun onLoadFinished(loader: Loader<List<Record>>, data: List<Record>?) {
        getViewModel().recordList.value = data;
    }

    override fun onLoaderReset(loader: Loader<List<Record>>) {
        getViewModel().recordList.value = null;
    }

    //*********************************************************
    // Private methods
    //*********************************************************

    private fun getViewModel() : RecordViewModel {
        return ViewModelProvider(requireActivity())[RecordViewModel::class.java]
    }

    //*********************************************************
    // Compose methods
    //*********************************************************

    @Composable
    fun RecordListView(viewModel: RecordViewModel) {
        val records: List<Record> by viewModel.recordList.observeAsState(initial = emptyList())

        LazyColumn {
            items(records) { item ->
                RecordRow(item)
            }
        }
    }

    @Composable
    fun RecordRow(record: Record) {
        Card(modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth()) {

            Row(modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.album),
                    contentDescription = "Your Content Description",
                    modifier = Modifier.size(40.dp)
                )
                Column(modifier = Modifier.padding(all = 10.dp)) {
                    Text(record.name, fontSize = 26.sp, fontWeight = FontWeight.W700, modifier = Modifier.padding(10.dp))
                    Text(record.artist, color = Color.Gray, modifier = Modifier.padding(10.dp))
                }
            }
        }
    }

    @Preview
    @Composable
    fun PreviewRecordRow() {
        val records = mutableListOf<Record>()
        val firstRecord = Record()
        firstRecord.name = "Test"
        firstRecord.artist = "First artist"
        records.add(firstRecord)

        val secondRecord = Record()
        secondRecord.name = "Test again"
        secondRecord.artist = "Second artist"
        records.add(secondRecord)

        RecordList(records = records)
    }

    @Composable
    fun RecordList(records: List<Record>) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            records.forEach { record ->
                RecordRow(record)
            }
        }
    }

}