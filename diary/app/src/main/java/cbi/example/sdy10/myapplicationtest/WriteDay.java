package cbi.example.sdy10.myapplicationtest;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class WriteDay extends AppCompatActivity {
    Button btnSubmit, btnSearch, wdDate, wdCategory;
    ImageButton wdFeel, wdWeather;
    EditText wdTitle, wdContents;
    public final int CATEGORY_ID = 0;
    public final int CATEGORY_ID2 = 1;
    Dialog dialog;
    private Context mContext;

    private Integer[] mThumbIds = {
            R.drawable.bbackchim, R.drawable.boolman, R.drawable.danghwang, R.drawable.kk
            , R.drawable.love, R.drawable.sad, R.drawable.sm, R.drawable.uu, R.drawable.vv, R.drawable.why
            , R.drawable.zzang, R.drawable.wwww
    };
    private Integer[] mWeathers = {
            R.drawable.boengae, R.drawable.hrim, R.drawable.onlywindow, R.drawable.rain, R.drawable.rain2, R.drawable.rainbow, R.drawable.rainbowrain
            , R.drawable.snow, R.drawable.sun, R.drawable.three, R.drawable.window, R.drawable.window2
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writeday);

        Intent inIntent = getIntent();
        final int year = inIntent.getIntExtra("Year", 0);
        final int month = inIntent.getIntExtra("Mon", 0);
        final int day = inIntent.getIntExtra("Day", 0);
        final String strday = Integer.toString(year) + Integer.toString(month)
                + Integer.toString(day);
        final String[] cat = new String[]{"학교", "일상", "직장"};


        btnSubmit = findViewById(R.id.btnSubmit);
        wdDate = findViewById(R.id.wdDate);
        wdFeel = findViewById(R.id.wdFeel);
        wdWeather = findViewById(R.id.wdWeather);
        wdTitle = findViewById(R.id.wdTitle);
        wdCategory = findViewById(R.id.wdCategory);
        wdContents = findViewById(R.id.wdContents);

        if (strday != null) {
            wdDate.setText(strday);
        }

        final Calendar cal = Calendar.getInstance();

        wdDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dlg = new DatePickerDialog(WriteDay.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String msg = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                        wdDate.setText(msg);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                dlg.show();
            }

        });
        wdFeel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(CATEGORY_ID);
            }
        });
        wdWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(CATEGORY_ID2);
            }
        });
        wdCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(WriteDay.this);
                dlg.setItems(cat, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        wdCategory.setText(cat[which]);
                    }
                });
                dlg.setPositiveButton("확인", null);
                dlg.show();
            }
        });

        //파일 저장
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //등록버튼 누르면 DB에 저장되고 메인화면으로 돌아감

                Intent outIntent = new Intent(getApplicationContext(), MainActivity.class);

                String SDpath = Environment.getExternalStorageDirectory() + "/mydir";
                String filename = wdDate.getText().toString() + ".txt";

                File dir = new File(SDpath);
                if (!dir.exists()) {
                    dir.mkdir();
                }

                File file = new File(dir, filename);
                try {
                    FileOutputStream outFs = new FileOutputStream(file);
                    String strDate = wdDate.getText().toString();
                    String strFeel = wdFeel.getResources().toString();
                    String strWeather = wdWeather.getResources().toString();
                    String strTitle = wdTitle.getText().toString();
                    String strCategory = wdCategory.getText().toString();
                    String strContents = wdContents.getText().toString();
                    outFs.write((strDate + strFeel + strWeather + strTitle + strCategory + strContents).getBytes());
                    outFs.close();

                    Toast.makeText(getApplicationContext(), filename + "가 생성됨", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                }
                setResult(RESULT_OK, outIntent);
                finish();
            }
        });


    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case CATEGORY_ID:
                AlertDialog.Builder builder;
                Context mContext = this;

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.dlgform, (ViewGroup) findViewById(R.id.layout_root));
                GridView gridview = layout.findViewById(R.id.gridView1);
                gridview.setAdapter(new ImageAdapter(this, CATEGORY_ID));
                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView parent, View v, int position, long id) {
                        wdFeel = findViewById(R.id.wdFeel);
                        wdFeel.setImageResource(mThumbIds[position]);
                    }
                });

                builder = new AlertDialog.Builder(mContext);
                builder.setView(layout);
                builder.setPositiveButton("확인", null);
                dialog = builder.create();
                break;
            case CATEGORY_ID2:
                Context mContext2 = this;

                LayoutInflater inflater2 = (LayoutInflater) mContext2.getSystemService(LAYOUT_INFLATER_SERVICE);
                View layout2 = inflater2.inflate(R.layout.dlgform, (ViewGroup) findViewById(R.id.layout_root));
                GridView gridview2 = layout2.findViewById(R.id.gridView1);
                gridview2.setAdapter(new ImageAdapter(this, CATEGORY_ID2));
                gridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView parent, View v, int position, long id) {
                        wdWeather = findViewById(R.id.wdWeather);
                        wdWeather.setImageResource(mWeathers[position]);

                    }
                });

                builder = new AlertDialog.Builder(mContext2);
                builder.setView(layout2);
                builder.setPositiveButton("확인", null);
                dialog = builder.create();
                break;
            default:
                dialog = null;
        }
        return dialog;
    }

}
