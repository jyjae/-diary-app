package cbi.example.sdy10.myapplicationtest;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener, View.OnClickListener {

    public static int SUNDAY = 1;

    private TextView mTvCalendarTitle;
    private GridView mGvCalendar;
    private ArrayList<DayInfo> mDayList;
    private CalendarAdapter mCalendarAdapter;

    Calendar mThisMonthCalendar;

    int width, height;
    DisplayMetrics dm;

    Button bLastMonth, bNextMonth, btnWrite, btnsave, btncancel;
    Toolbar toolbar;
    TabHost tabHost;
    DrawerLayout drawer;
    NavigationView navigationView;
    ListView listView;
    LinearLayout linear2, diaryLinear;
    EditText et;
    TextView tvCount, text1, text2;

    ArrayAdapter<String> adapter;
    //txt파일 담는 변수
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkFunction();
        init();

        bLastMonth = findViewById(R.id.btn_previous_calendar);
        bNextMonth = findViewById(R.id.btn_next_calendar);
        btnWrite = findViewById(R.id.btn1);
        mTvCalendarTitle = findViewById(R.id.tv_calendar_title);
        mGvCalendar = findViewById(R.id.gv_calendar);
        mDayList = new ArrayList<DayInfo>();

        dm = getResources().getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabHost = findViewById(R.id.Host);
        tabHost.setup();

        TabHost.TabSpec tabSpecHome = tabHost.newTabSpec("HOME").setIndicator("HOME");
        tabSpecHome.setContent(R.id.tabHome);
        tabHost.addTab(tabSpecHome);

        TabHost.TabSpec tabSpecDate = tabHost.newTabSpec("Date").setIndicator("Date");
        tabSpecDate.setContent(R.id.tabDate);
        tabHost.addTab(tabSpecDate);

        tabHost.setCurrentTab(0);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        bLastMonth.setOnClickListener(this);
        bNextMonth.setOnClickListener(this);
        mGvCalendar.setOnItemClickListener(this);
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WriteDay.class);
                startActivityForResult(intent, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            init();
        }

    }


    public void checkFunction() {
        int permissioninfo = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissioninfo == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "SDCard 쓰기 권한 있음", Toast.LENGTH_SHORT).show();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "권한 설명", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }

    void init() {

        listView = findViewById(R.id.listview);
        tvCount = findViewById(R.id.tvCount);

        File[] listFiles = (new File("/sdcard//mydir").listFiles());
        String SDpath = Environment.getExternalStorageDirectory() + "/mydir";

        File dir = new File(SDpath);
        if (!dir.exists()) {
            dir.mkdir();
        }

        //넣어준 스트링을

        list.clear();   //초기화
        try {
            for (File file : listFiles)                        //리스트에
                list.add(file.getName());
        } catch (Exception e) {
        }


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        listView.setAdapter(adapter);

        tvCount.setText("등록된 메모 개수: " + list.size());
        if (list.size() > 0) {
            text1 = findViewById(R.id.text1);
            text1.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        String str = null;
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                str = "SD Card 쓰기권한 승인";
            else str = "SD Card 쓰기권한 거부";
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(getApplicationContext(), Setting.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 이번달 의 캘린더 인스턴스를 생성한다.
        mThisMonthCalendar = Calendar.getInstance();
        mThisMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        getCalendar(mThisMonthCalendar);
    }

    /*
     * 달력을 셋팅한다.
     * 달력에 보여지는 이번달의 Calendar 객체
     * */
    private void getCalendar(Calendar calendar) {
        int lastMonthStartDay;
        int dayOfMonth;
        int thisMonthLastDay;

        mDayList.clear();

        //이번달 시작일의 요일을 구한다. 시작일이 일요일인 경우 인덱스를 1(일요일)에서
        //8(다음주 일요일)로 바꾼다.
        dayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        Log.e("dayOfMonth", dayOfMonth + "");
        thisMonthLastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.MONTH, -1);
        Log.e("지난달 마지막일", calendar.get(Calendar.DAY_OF_MONTH) + "");

        //지난달의 마지막 일자를 구한다.
        lastMonthStartDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.MONTH, 1);
        Log.e("이번달 시작일", calendar.get(Calendar.DAY_OF_MONTH) + "");

        if (dayOfMonth == SUNDAY)
            dayOfMonth += 7;
        lastMonthStartDay -= (dayOfMonth - 1) - 1;

        Log.e("lastMonthStartDay", lastMonthStartDay + "");
        mTvCalendarTitle.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년"
                + (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");

        DayInfo day;
        Log.e("DayOfMonth", dayOfMonth + "");

        for (int i = 0; i < dayOfMonth - 1; i++) {
            int date = lastMonthStartDay + i;
            day = new DayInfo();
            //day.setDay(Integer.toString(i));
            day.setDay("");
            day.setInMonth(false);
            mDayList.add(day);
        }
        for (int i = 1; i <= thisMonthLastDay; i++) {
            day = new DayInfo();
            day.setDay(Integer.toString(i));
            day.setInMonth(true);

            mDayList.add(day);
        }
        for (int i = 1; i < 35 - (thisMonthLastDay + dayOfMonth - 1) + 1; i++) {
            day = new DayInfo();
            //day.setDay(Integer.toString(i));
            day.setDay("");
            day.setInMonth(false);
            mDayList.add(day);
        }
        initCalendarAdapter();
    }

    /*
     * 지난 달의 Calendar 객체를 반환합니다.
     * @param calendar
     * @return LastMonthCalendar
     * */
    private Calendar getLastMonth(Calendar calendar) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.add(Calendar.MONTH, -1);
        mTvCalendarTitle.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
                + (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");
        return calendar;
    }

    /*
     * 다음달의 Calendar 객체를 반환합니다.
     *
     * @param calendar
     * @return NextMonthCalendar
     * */
    private Calendar getNextMonth(Calendar calendar) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.add(Calendar.MONTH, +1);
        mTvCalendarTitle.setText(mThisMonthCalendar.get(Calendar.YEAR) + "년 "
                + (mThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");
        return calendar;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long arg3) {
        Intent intent = new Intent(getApplicationContext(), WriteDay.class);

        intent.putExtra("Year", mThisMonthCalendar.get(Calendar.YEAR));
        intent.putExtra("Mon", mThisMonthCalendar.get(Calendar.MONTH) + 1);
        intent.putExtra("Day", 0);
        Log.e("1111111.", mThisMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) + "");//전달
        Log.e("1111222.", mTvCalendarTitle.getText().toString());


        startActivityForResult(intent, 0);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_previous_calendar:
                mThisMonthCalendar = getLastMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);
                break;
            case R.id.btn_next_calendar:
                mThisMonthCalendar = getNextMonth(mThisMonthCalendar);
                getCalendar(mThisMonthCalendar);
                break;

        }
    }

    private void initCalendarAdapter() {
        mCalendarAdapter = new CalendarAdapter(this, R.layout.day, mDayList, width, height);
        mGvCalendar.setAdapter(mCalendarAdapter);
    }
}
