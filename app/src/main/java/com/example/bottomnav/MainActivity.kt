package com.example.bottomnav

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.bottomnav.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        navController = findNavController(R.id.navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.firstFragment){
                binding?.bottomNav?.menu?.findItem(R.id.fragmentOne)?.isChecked = true
            }
            if(destination.id == R.id.secondFragment){
                binding?.bottomNav?.menu?.findItem(R.id.fragmentTwo)?.isChecked = true
            }
        }

        binding?.bottomNav?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.fragmentOne -> navController.navigate(R.id.firstFragment)
                R.id.fragmentTwo -> navController.navigate(R.id.secondFragment)
            }
            return@setOnItemSelectedListener true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.side_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.showToast -> Toast.makeText(
                this,
                "This is Toast shown from Menu Item ",
                Toast.LENGTH_LONG
            ).show()

            R.id.showSnackBar -> binding?.root?.let {
                Snackbar.make(it, "This is SnackBar", Snackbar.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    }