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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
            || ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.CAMERA),0)
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        NavigationUI.setupWithNavController(binding.bottomNavigation,navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
}