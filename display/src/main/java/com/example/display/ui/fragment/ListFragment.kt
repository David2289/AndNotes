package com.example.display.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.display.R
import com.example.display.business.model.User
import com.example.display.databinding.ListFragmentBinding
import com.example.display.ui.adapter.UsersAdapter
import com.example.commons.utility.helper.Constants
import com.example.display.ui.viewmodel.ListViewModel
import org.koin.android.ext.android.get

class ListFragment : Fragment() {

    private var viewModel = get<ListViewModel>()
    private lateinit var binding: ListFragmentBinding
    private lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configUsersObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        configUsersUI()
        return binding.root
    }

    private fun configUsersObserver() {
        val loadingObserver = Observer<Boolean> { result ->
            binding.loadingContent.loading.visibility = if (result) View.VISIBLE else View.GONE
        }
        viewModel.isLoadingLiveData.observe(this, loadingObserver)
        val listObserver = Observer<List<User>> {
            adapter.notifyDataSetChanged()
            binding.loadingContent.loading.visibility = View.GONE
        }
        viewModel.userListLiveData.observe(this, listObserver)
    }

    private fun configUsersUI() {
        adapter = UsersAdapter(viewModel.userList) { user ->
            val bundle = bundleOf(Constants.BUNDLE_USER to user)
            Navigation.findNavController(binding.root).navigate(R.id.action_list_to_detail, bundle)
        }
        val llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerview.layoutManager = llm
        binding.recyclerview.adapter = adapter
    }

}