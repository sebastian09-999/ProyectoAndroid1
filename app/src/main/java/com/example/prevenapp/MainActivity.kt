package com.example.prevenapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)

        // Abrir el menú lateral al darle clic al icono de hamburguesa
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Cargar la pantalla de "Resumen" por defecto
        if (savedInstanceState == null) {
            replaceFragment(ResumenFragment())
            navView.setCheckedItem(R.id.nav_resumen)
        }

        // Escuchar los clics del menú
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_resumen -> replaceFragment(ResumenFragment())
                R.id.nav_horarios -> replaceFragment(MisHorariosFragment())
                R.id.nav_chat -> replaceFragment(SalasChatFragment())
                R.id.nav_tips -> replaceFragment(PlaceholderFragment.newInstance("Publicar Tips"))
                R.id.nav_reportes -> replaceFragment(PlaceholderFragment.newInstance("Reportes"))
                R.id.nav_recursos -> replaceFragment(PlaceholderFragment.newInstance("Recursos"))
                R.id.nav_perfil -> replaceFragment(PlaceholderFragment.newInstance("Mi Perfil"))
            }
            // Cerrar el menú después de hacer clic
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}