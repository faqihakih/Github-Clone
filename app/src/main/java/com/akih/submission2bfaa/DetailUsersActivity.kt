package com.akih.submission2bfaa

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.akih.submission2bfaa.adapter.ViewPagerAdapter
import com.akih.submission2bfaa.databinding.ActivityDetailUsersBinding
import com.bumptech.glide.Glide

class DetailUsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUsersBinding
    private lateinit var viewModel: DetailUsersViewModel
    companion object{
        val EXTRA_USERNAME = "extra_username"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = resources.getString(R.string.app_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUsersViewModel::class.java)

        viewModel.setDetailUser(username.toString())
        viewModel.getDetailUser().observe(this, Observer {
            if (it != null){
                binding.apply {
                    tvCompany.text = if (it.company != null) it.company else resources.getString(R.string.response)
                    tvName.text = if (it.name != null) it.name else resources.getString(R.string.response)
                    tvLocation.text = if (it.location != null) it.location else resources.getString(R.string.response)
                    tvUsername.text = it.login
                    tvResultFollowers.text = it.followers.toString()
                    tvResultFollowing.text = it.following.toString()
                    tvResultRepo.text = it.public_repos.toString()
                    Glide.with(binding.root)
                        .load(it.avatar_url)
                        .into(imgUser)
                }
            }
        })
        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = viewPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}