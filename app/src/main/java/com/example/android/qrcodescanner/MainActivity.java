package com.example.android.qrcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    NavigationView nav;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        drawerLayout =findViewById(R.id.drawer);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav=findViewById(R.id.navmenu);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new ScanFragment()).commit();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment temp ;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {

                    case R.id.scan:
                        toolbar.setTitle("QR CODE SCANNER");
                        Toast.makeText(getApplicationContext(),"Scanner is Open",Toast.LENGTH_SHORT).show();
                        temp=new ScanFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,temp).commit();

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.favourite:
                        Toast.makeText(getApplicationContext(),"Saved is Open",Toast.LENGTH_SHORT).show();
                        toolbar.setTitle("Saved");
                        temp = new SavedFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,temp).commit();

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.History:
                        toolbar.setTitle("History");
                        Toast.makeText(getApplicationContext(),"History is Open",Toast.LENGTH_SHORT).show();
                        temp = new HistoryFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,temp).commit();

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.Logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        finish();
                        break;
                }
                   return true;
            }
        });
    }
}