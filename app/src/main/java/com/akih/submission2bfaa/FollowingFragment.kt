package com.akih.submission2bfaa

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.akih.submission2bfaa.adapter.UserAdapter
import com.akih.submission2bfaa.data.Users
import com.akih.submission2bfaa.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    private var _binding : FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username : String

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val view = binding.root
        val args = arguments
        username = args?.getString(DetailUsersActivity.EXTRA_USERNAME).toString()
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : UserAdapter.OnClickItemCallback{
            override fun onClickedItem(data: Users) {
                startActivity(Intent(activity, DetailUsersActivity::class.java).also {
                    it.putExtra(DetailUsersActivity.EXTRA_USERNAME, data.login)
                })
            }

        })
        binding.apply {
            rvFollowing.setHasFixedSize(true)
            rvFollowing.layoutManager = LinearLayoutManager(activity)
            rvFollowing.adapter = adapter
        }
        showProgressBar(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner, Observer {
            if (it != null){
                if (it.isEmpty()){
                    binding.ivFollowing.visibility = View.VISIBLE
                    binding.rvFollowing.visibility = View.GONE
                    showProgressBar(false)
                    Toast.makeText(activity, resources.getString(R.string.notFoundFollowing), Toast.LENGTH_SHORT).show()
                }else{
                    adapter.setList(it)
                    binding.rvFollowing.visibility = View.VISIBLE
                    binding.ivFollowing.visibility = View.GONE
                    showProgressBar(false)
                }
            }
        })
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showProgressBar(state : Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}