package com.downloader.views.fragments

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.downloader.R
import com.downloader.adapters.FilesAdapter
import com.downloader.databinding.FragmentFilesBinding
import com.downloader.factory.FilesViewModelFactory
import com.downloader.model.response.FileResponse
import com.downloader.network.ApiInterface
import com.downloader.repository.FilesRepo
import com.downloader.view_model.FilesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class FilesFragment : Fragment() {
    private var binding: FragmentFilesBinding? = null
    private lateinit var filesViewModelFactory: FilesViewModelFactory
    lateinit var filesViewModel: FilesViewModel

    var status = 0
    var handler = Handler()

    private val api: ApiInterface
        get() {
            return ApiInterface()
        }
    private val filesRepo: FilesRepo
        get() {
            return FilesRepo(api)
        }

    companion object {
        var activity: FragmentActivity? = null
        var fragment: FilesFragment? = null

        fun newInstance(activity: FragmentActivity): FilesFragment? {
            FilesFragment.activity = activity
            if (fragment == null)
                fragment = FilesFragment()
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilesBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActivity()
        initViewModel()
        initProgress()
        getFilesApi()
    }

    private fun initActivity() {
        if (FilesFragment.activity == null)
            FilesFragment.activity = activity
    }


    private fun initViewModel() {
        filesViewModelFactory = FilesViewModelFactory(filesRepo)
        filesViewModel = ViewModelProvider(this, filesViewModelFactory)[FilesViewModel::class.java]
    }

    private fun initProgress() {
        filesViewModel.getProgress().observe(viewLifecycleOwner) { aBoolean ->
            if (aBoolean == true) {
                binding!!.filesList.showShimmer()
            } else {
                binding!!.filesList.hideShimmer()
            }
        }
    }

    private fun getFilesApi() {
        filesViewModel.getFiles()
        filesViewModel.slider.observe(viewLifecycleOwner) { response ->
            if (response != null && response.size > 0) {
                setFiles(response)
            }
        }
    }

    private fun setFiles(response: ArrayList<FileResponse>) {
        binding?.filesList?.layoutManager = GridLayoutManager(activity, 3)
        binding?.filesList?.adapter =
            FilesAdapter(
                requireActivity(),
                response,
                object : FilesAdapter.OnItemClickListener {
                    override fun click(position: Int, holder: FilesAdapter.MyViewHolder) {
                        status = 0
                        showDialog(holder)
                    }

                }
            )
    }

    fun showDialog(holder: FilesAdapter.MyViewHolder) {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog)
        val progress: ProgressBar = dialog.findViewById(R.id.progress)
        val value: TextView = dialog.findViewById(R.id.value)
        Thread {
            while (status < 100) {
                status += 1
                try {
                    Thread.sleep(200)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                handler.post {
                    progress.progress = status
                    value.text = "$status %"
                    if (status == 100) {
                        dialog.dismiss()
                        holder.binding.download.setImageResource(R.drawable.tick)
                    }
                }
            }
        }.start()
        dialog.show()
        val window: Window? = dialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }


}