package com.think.searchimage

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.think.searchimage.adapters.MediaAdapter
import com.think.searchimage.constants.OnItemClickListener
import com.think.searchimage.constants.OnNoResultFoundListener
import com.think.searchimage.databinding.ActivityMainBinding
import com.think.searchimage.extentions.showToastLong
import com.think.searchimage.viewmodel.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_coordinates_bottom_sheet.view.*
import kotlinx.android.synthetic.main.shimmer_layout.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MediaAdapter
    private val viewModel: ImageViewModel by viewModels()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

         binding.root.also {
            setContentView(it)
        }


      /*  startShimmer()
        setObservers()
        setRecyclerView()
        setListeners()*/

        setListeners()
    }


    private fun setListeners(){

        bottomSheetBehavior = BottomSheetBehavior.from(binding.includeLayout)



        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {

                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {

                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {

                        binding.fbAdd.show()
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {

                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {

                    }
                    BottomSheetBehavior.STATE_SETTLING -> {

                    }
                }
            }


            override fun onSlide(bottomSheet: View, slideOffset: Float) {
               /* val paramsSearch: RelativeLayout.LayoutParams = binding.llSearch.layoutParams as RelativeLayout.LayoutParams
                val top = appUtils.getDisplayMatrix(activity!!).heightPixels - bottomSheet.top
                paramsSearch.setMargins(0, 0, 0, top - ResourceUtil.instance?.getDimen(R.dimen.dp_110)!!)
                binding.llSearch.layoutParams = paramsSearch

                if (slideOffset > 0.7) {
                    binding.llCreate.visibility = View.INVISIBLE
                    binding.llSearch.visibility=View.INVISIBLE

                } else{
                    binding.llCreate.visibility = View.VISIBLE
                    binding.llSearch.visibility=View.VISIBLE

                }*/
            }

        })

        fb_add.setOnClickListener {
            if(bottomSheetBehavior.state==BottomSheetBehavior.STATE_COLLAPSED){
                bottomSheetBehavior.state=BottomSheetBehavior.STATE_EXPANDED

            }
            else bottomSheetBehavior.state=BottomSheetBehavior.STATE_COLLAPSED


        }

    }
    /*private fun startShimmer(){
        binding.includeShimmer.userShimmer.visibility=View.VISIBLE
        binding.includeShimmer.userShimmer.startShimmerAnimation()
    }
    private fun stopShimmer(){
        binding.includeShimmer.userShimmer.visibility=View.GONE
        binding.includeShimmer.userShimmer.stopShimmerAnimation()
    }*/


   /* private fun setObservers() {
        viewModel.repoLiveData.observe(this) {
            if (it != null) {
                if (it.peekContent() != null) {
                    stopShimmer()
                    adapter.setSource(it.peekContent().items)

                }
            }
        }
        viewModel.failureResponseLiveData.observe(this){
            showToastLong(it.peekContent().message)
        }
    }


    private fun setListeners() {
        binding.searchview.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }


        })
    }


    private fun setRecyclerView() {
        layoutManager = LinearLayoutManager(this)

        binding.rvMedia.layoutManager = layoutManager
        adapter = MediaAdapter(this,object:OnItemClickListener{
            override fun onItemClicked(position: Int) {
                val item= adapter.filteredList[position]
                item.isSelected=!(item.isSelected)
                adapter.notifyItemChanged(position)
            }

        },object :OnNoResultFoundListener{
            override fun onNoResultFound(size: Int) {
                if(size==0)
                Toast.makeText(this@MainActivity,getString(R.string.no_repo),Toast.LENGTH_SHORT).show()
            }

        })
        adapter.hasStableIds()
        binding.rvMedia.adapter = adapter
    }*/



}