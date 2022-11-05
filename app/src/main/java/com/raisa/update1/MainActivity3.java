package com.raisa.update1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.raisa.update1.Constants.GlobalVariable;
import com.raisa.update1.start.LogIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity3 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    TextView name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main3);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Care Plus");
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView =findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null){


////         name = findViewById(R.id.Uname);
////         email = findViewById(R.id.Uemail);
//         name.setText(GlobalVariable.UserName);
//        email.setText(GlobalVariable.Uid);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);}

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        name = findViewById(R.id.Uname);
        email = findViewById(R.id.Uemail);
        name.setText(GlobalVariable.UserName);
        email.setText(GlobalVariable.Email);
        switch(item.getItemId()){
            case R.id.nav_home:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
                break;


            case R.id.nav_task:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DailyTaskListFragment()).commit();
                break;


            case R.id.nav_calender:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CalenderFragment()).commit();
                break;



            case R.id.nav_member:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddMemberFragment()).commit();
                break;


            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AboutUsFragment()).commit();
                break;


            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SettingsFragment()).commit();
                break;


            case R.id.nav_userManual:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new UserManualFragment()).commit();
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity3.this, LogIn.class);
                startActivity(i);
                finish();
                Toast.makeText(MainActivity3.this, "Logged out!", Toast.LENGTH_SHORT).show();
                break;


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}