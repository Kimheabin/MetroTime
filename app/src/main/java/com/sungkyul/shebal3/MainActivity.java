package com.sungkyul.shebal3;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.util.Log;



public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    String TAG = "MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //하단 바 버튼 이동
        bottomNavigationView = findViewById(R.id.bottom_menu);

        replaceFragment(new HomeFragment());



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.onebottom) {
                    replaceFragment(new OneFragment());
                    return true;
                } else if (item.getItemId() == R.id.twobottom) {
                    replaceFragment(new TwoFragment());
                    return true;
                } else if (item.getItemId() == R.id.homebottom) {
                    replaceFragment(new HomeFragment());
                    return true;
                } else if (item.getItemId() == R.id.fourbottom) {
                    replaceFragment(new FourFragment());
                    return true;
                } else if (item.getItemId() == R.id.fivebottom) {
                    replaceFragment(new FiveFragment());
                    return true;
                }
                return false;
            }

        });

    }

    //프래그먼트 매니저 사용
    void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

}
