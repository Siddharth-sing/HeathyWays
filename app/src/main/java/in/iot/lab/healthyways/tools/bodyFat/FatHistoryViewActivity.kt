package `in`.iot.lab.healthyways.tools.bodyFat


import `in`.iot.lab.healthyways.R
import `in`.iot.lab.healthyways.adapters.FatHistoryAdapter
import `in`.iot.lab.healthyways.room.history.HistoryViewModel
import `in`.iot.lab.healthyways.room.history.entities.FatCalcHistoryEntity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_fat_history.*


class FatHistoryViewActivity : AppCompatActivity(), FatHistoryAdapter.IGCHistoryRVAdapter {


    lateinit var viewModel: HistoryViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fat_history)

        setSupportActionBar(toolbar_fat_history_activity)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title = "Fat Calculator History"
        toolbar_fat_history_activity.setNavigationOnClickListener {
            onBackPressed()
        }


        val fatHistoryRecyclerView = rvAllFatHistory
        val adapter =
            FatHistoryAdapter(this, this)
        fatHistoryRecyclerView.adapter = adapter
        fatHistoryRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(HistoryViewModel::class.java)

        viewModel.allFatHistory.observe(this) {
            it.let{
                adapter.updateList(it)
            }
            when {
                it.isEmpty() -> {
                    fatLaYoga.visibility = View.VISIBLE
                    tvEmptyFat.visibility = View.VISIBLE
                }
            }
        }
    }




    override fun onItemClicked(fatHistory: FatCalcHistoryEntity) {
        Toast.makeText(this, "✅ Deleted Successfully", Toast.LENGTH_SHORT).show()
        viewModel.deleteFatHistory(fatHistory)
    }
}