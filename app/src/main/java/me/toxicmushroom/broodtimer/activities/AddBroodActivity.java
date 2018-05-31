package me.toxicmushroom.broodtimer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collection;

import me.toxicmushroom.broodtimer.dialogs.InputDialog;
import me.toxicmushroom.broodtimer.MainActivity;
import me.toxicmushroom.broodtimer.R;
import me.toxicmushroom.broodtimer.data.Broden;
import me.toxicmushroom.broodtimer.data.MyDBHandler;

/**
 * Created by Merlijn on 27/12/2017.
 */

public class AddBroodActivity extends AppCompatActivity implements InputDialog.InputDialogListener {

    public static int editing = 0;
    public static String valueOfEditing;
    TextView one;
    TextView two;
    TextView three;
    TextView four;
    TextView five;
    TextView six;
    TextView seven;
    TextView eight;
    TextView nine;
    TextView ten;
    EditText broodNaamEditor;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_brood);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Brood Loader");
        dbHandler = new MyDBHandler(this, null, null, 0); //deze params worden door de class gedaan
        broodNaamEditor = findViewById(R.id.reminder_title);
        one = findViewById(R.id.tvSettingsPhaseOne);
        two = findViewById(R.id.tvSettingsPhaseTwo);
        three = findViewById(R.id.tvSettingsPhaseThree);
        four = findViewById(R.id.tvSettingsPhaseFour);
        five = findViewById(R.id.tvSettingsPhaseFive);
        six = findViewById(R.id.tvSettingsPhaseSix);
        seven = findViewById(R.id.tvSettingsPhaseSeven);
        eight = findViewById(R.id.tvSettingsPhaseEight);
        nine = findViewById(R.id.tvSettingsPhaseNine);
        ten = findViewById(R.id.tvSettingsPhaseTen);
        Broden initBrood = dbHandler.getLatestBrood();
        if (initBrood != null) {
            broodNaamEditor.setText(initBrood.get_broodnaam());
            one.setText(String.valueOf(initBrood.getFase1()));
            two.setText(String.valueOf(initBrood.getFase2()));
            three.setText(String.valueOf(initBrood.getFase3()));
            four.setText(String.valueOf(initBrood.getFase4()));
            five.setText(String.valueOf(initBrood.getFase5()));
            six.setText(String.valueOf(initBrood.getFase6()));
            seven.setText(String.valueOf(initBrood.getFase7()));
            eight.setText(String.valueOf(initBrood.getFase8()));
            nine.setText(String.valueOf(initBrood.getFase9()));
            ten.setText(String.valueOf(initBrood.getFase10()));
        }
        Button btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(v -> {
            boolean accept = true;
            Collection<TextView> tvList = Arrays.asList(one, two, three, four, five, six, seven, eight, nine, ten);
            for (TextView view : tvList) {
                if (view.getContext().toString() == null) accept = false;
            }
            if (accept && !broodNaamEditor.getText().toString().equalsIgnoreCase("")) {
                for (Broden broodje : dbHandler.getAlleBroden()) {
                    if (broodje.get_broodnaam().equalsIgnoreCase(broodNaamEditor.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "De broodnaam: '" + broodNaamEditor.getText().toString() + "' is al in gebruik.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                Broden broodje = new Broden();
                broodje.set_broodnaam(broodNaamEditor.getText().toString());
                broodje.setFases(
                        Integer.valueOf(one.getText().toString()),
                        Integer.valueOf(two.getText().toString()),
                        Integer.valueOf(three.getText().toString()),
                        Integer.valueOf(four.getText().toString()),
                        Integer.valueOf(five.getText().toString()),
                        Integer.valueOf(six.getText().toString()),
                        Integer.valueOf(seven.getText().toString()),
                        Integer.valueOf(eight.getText().toString()),
                        Integer.valueOf(nine.getText().toString()),
                        Integer.valueOf(ten.getText().toString())
                );
                dbHandler.addBrood(broodje);
                startActivity(new Intent(getApplicationContext(), LoadBroodActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "Je hebt niet alle waardes ingevuld!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void openDialog() {
        InputDialog inputDialog = new InputDialog();
        inputDialog.show(getSupportFragmentManager(), "test123");
    }

    public void setPhaseOne(View view) {
        editing = 1;
        valueOfEditing = one.getText().toString();
        openDialog();
    }

    public void setPhaseTwo(View view) {
        editing = 2;
        valueOfEditing = two.getText().toString();
        openDialog();
    }

    public void setPhaseThree(View view) {
        editing = 3;
        valueOfEditing = three.getText().toString();
        openDialog();
    }

    public void setPhaseFour(View view) {
        editing = 4;
        valueOfEditing = four.getText().toString();
        openDialog();
    }

    public void setPhaseFive(View view) {
        editing = 5;
        valueOfEditing = five.getText().toString();
        openDialog();
    }

    public void setPhaseSix(View view) {
        editing = 6;
        valueOfEditing = six.getText().toString();
        openDialog();
    }

    public void setPhaseSeven(View view) {
        editing = 7;
        valueOfEditing = seven.getText().toString();
        openDialog();
    }

    public void setPhaseEight(View view) {
        editing = 8;
        valueOfEditing = eight.getText().toString();
        openDialog();
    }

    public void setPhaseNine(View view) {
        editing = 9;
        valueOfEditing = nine.getText().toString();
        openDialog();
    }

    public void setPhaseTen(View view) {
        editing = 10;
        valueOfEditing = ten.getText().toString();
        openDialog();
    }

    @Override
    public void applyText(String input) {
        switch (editing) {
            case 1:
                one.setText(input);
                break;
            case 2:
                two.setText(input);
                break;
            case 3:
                three.setText(input);
                break;
            case 4:
                four.setText(input);
                break;
            case 5:
                five.setText(input);
                break;
            case 6:
                six.setText(input);
                break;
            case 7:
                seven.setText(input);
                break;
            case 8:
                eight.setText(input);
                break;
            case 9:
                nine.setText(input);
                break;
            case 10:
                ten.setText(input);
                break;
            default:
                break;
        }
    }
}
