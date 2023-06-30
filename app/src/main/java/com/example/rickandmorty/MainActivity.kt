package com.example.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.rickandmorty.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfig: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.hostingFragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        navController.setGraph(R.navigation.nav_graph)
        appBarConfig = AppBarConfiguration(navController.graph, binding.parentDrawerLayout)
        setupActionBarWithNavController(navController, appBarConfig)


        binding.navigationView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.getPageOfCharacters  -> {
                    if ((binding.hostingFragment.findNavController().currentDestination?.label == "Rick And Morty API"))
                    {
                        findNavController(R.id.hostingFragment).navigate(R.id.action_homeFragment_to_showAllCharactersFragment)
                    }else if ((binding.hostingFragment.findNavController().currentDestination?.label == "Single character")){
                        findNavController(R.id.hostingFragment).navigate(R.id.action_singleCharacterFragment_to_showAllCharactersFragment)
                    }
                }
                R.id.getCharacterById -> {
                    if ((binding.hostingFragment.findNavController().currentDestination?.label == "Page of characters")){
                        findNavController(R.id.hostingFragment).navigate(R.id.action_showAllCharactersFragment_to_singleCharacterFragment)
                    }else if (binding.hostingFragment.findNavController().currentDestination?.label == "Rick And Morty API"){
                        findNavController(R.id.hostingFragment).navigate(R.id.action_homeFragment_to_singleCharacterFragment)
                    }
                }
            }

            NavigationUI.onNavDestinationSelected(it, navController)
            binding.parentDrawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.hostingFragment)
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }
}