package com.sungkyul.shebal3;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.util.Log;
import android.widget.Toast;

//테스트 테스트 커밋테스트
public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //뒤로가기 버튼, 디폴드로 true만 해도 백버튼이 생김

        //하단 바 버튼 이동
        bottomNavigationView = findViewById(R.id.bottom_menu);

        replaceFragment(new HomeFragment());



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.homebottom) {
                    replaceFragment(new HomeFragment());
                    return true;
                } else if (item.getItemId() == R.id.twobottom) {
                    replaceFragment(new TwoFragment());
                    return true;
                } else if (item.getItemId() == R.id.onebottom) {
                    replaceFragment(new OneFragment());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            finish();
            return true;
        }
        else if(item.getItemId() == R.id.toolb1){
            Toast.makeText(getApplicationContext(),"버튼1을 누르셨어요",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(item.getItemId() == R.id.toolb2){
            Toast.makeText(getApplicationContext(),"버튼2를 누르셨어요",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }



    //프래그먼트 매니저 사용
    void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

}
