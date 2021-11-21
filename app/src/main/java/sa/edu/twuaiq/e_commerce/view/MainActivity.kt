package sa.edu.twuaiq.e_commerce.view

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import sa.edu.twuaiq.e_commerce.R
import sa.edu.twuaiq.e_commerce.databinding.ActivityMainBinding
import java.util.jar.Manifest

/***
 * Before starting the implementation of the Navigation Architecture Component.
you must be aware of the following:
 * Navigation Graph (Destination and Action)
 * Navigation Host Fragment
 * Navigation Controller
 * */


/***
 * Navigation Graph
Navigation graph is an XML file which shows you how the Fragments are related to each other.
It contains the Fragments Destinations, routes, arguments, action, etc.
Using the Navigation graph you can quickly set the Transition Animation by setting up some attributes.
 * */

/**
 * A Navigation Graph consists of:
 * Destinations: The individual screens the user can navigate to and can specify arguments,
actions and deep link URLs to these destinations.
 * Actions: The routes user can take between your app’s destinations.
Which are represented by the arrow sign in the Design view.
 * */


/***
 * Navigation Host Fragment
In our Activity layout, we have to add a fragment as NavHost and need to define, Where the NavHostFragment finds the navigation graph.
You can see the following code in which we define the fragment as NavHostFragment and also define the navigation graph.
 * You must defines which Navigation Graph will be associated with the Navigation Host
 * */


/***
 * Navigation Controller
NavController is the class which deals with the FragmentManager or FragmentTransaction.
NavController manages application navigation with the NavHostFragment.
Navigation flow and destination are determined by the navigation graph owned by the NavController class.
And currently running NavHostFragment directly deal with maintaining back-stack,
action bar, toolbar, navigation drawer icon, etc
 * */
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        /**
         * Every Android app runs in a limited-access sandbox.
         * If your app needs to use resources or information outside of its own sandbox,
         * you can declare a permission and set up a permission request that provides this access.
         * These steps are part of the workflow for using permissions.

        If you declare any dangerous permissions,
        and if your app is installed on a device that runs Android 6.0 (API level 23) or higher,
        you must request the dangerous permissions at runtime by following this steps
         * */

        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
            || ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
            || ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE),0)
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        NavigationUI.setupWithNavController(binding.bottomNavigation,navController)

    }

    // If you want to back to the last fragment from where you come here just user the navigateUp method of NavController
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}