package com.akih.submission2bfaa

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.akih.submission2bfaa.adapter.UserAdapter
import com.akih.submission2bfaa.data.Users
import com.akih.submission2bfaa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UsersViewModel
    private lateinit var adapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : UserAdapter.OnClickItemCallback{
            override fun onClickedItem(data: Users) {
                startActivity(Intent(this@MainActivity, DetailUsersActivity::class.java).also {
                    it.putExtra(DetailUsersActivity.EXTRA_USERNAME, data.login)
                })
            }

        })
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UsersViewModel::class.java)

        binding.rvListGithub.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvListGithub.setHasFixedSize(true)
        binding.rvListGithub.adapter = adapter
        binding.ivHome.visibility = View.VISIBLE
        viewModel.getSearchUsers().observe(this, Observer {
            if(it != null){
                if (it.isEmpty()){
                    binding.ivHome.visibility = View.VISIBLE
                    binding.rvListGithub.visibility = View.GONE
                    showProgressBar(false)
                    Toast.makeText(this, resources.getString(R.string.notFound), Toast.LENGTH_LONG).show()
                }else{
                    adapter.setList(it)
                    binding.rvListGithub.visibility = View.VISIBLE
                    binding.ivHome.visibility = View.GONE
                    showProgressBar(false)
                }
            }
        })

        supportActionBar?.title = resources.getString(R.string.github)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_options, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.setSearchUsers(query)
                binding.ivHome.visibility = View.GONE
                showProgressBar(true)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showProgressBar(state : Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}