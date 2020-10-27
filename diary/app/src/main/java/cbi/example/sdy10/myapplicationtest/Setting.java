package cbi.example.sdy10.myapplicationtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Setting extends AppCompatActivity {
    Switch aSwitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        aSwitch=findViewById(R.id.switchd);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Intent intent=new Intent(getApplicationContext(), Bimil.class);
                    startActivity(intent);
            }
        });
    }
}
