package com.example.medicinedb;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
 EditText medicineName,medicineDate;
 TextView textViewMed;
 Button insertButton, fetchButton;
 Spinner dayTimeSpinner;
 Switch swtch;
 DbConnection dbConnection;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_main);
 dbConnection = new DbConnection(this);
 textViewMed = findViewById(R.id.txtViewMed);
 medicineName = findViewById(R.id.edtTxtMed);
 medicineDate = findViewById(R.id.edtTxtDate);
 insertButton = findViewById(R.id.btnInsert);
 fetchButton = findViewById(R.id.btnFetch);
 fetchButton.setVisibility(View.INVISIBLE);
 dayTimeSpinner = findViewById(R.id.spinner);
 swtch = findViewById(R.id.switcher);
 swtch.setOnCheckedChangeListener(new 
CompoundButton.OnCheckedChangeListener() {
 @Override
 public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
 if(!isChecked){
 fetchButton.setVisibility(View.INVISIBLE);
 insertButton.setVisibility(View.VISIBLE);
 medicineName.setVisibility(View.VISIBLE);
 textViewMed.setVisibility(View.VISIBLE);
 }
 else{
 medicineName.setVisibility(View.INVISIBLE);
 insertButton.setVisibility(View.INVISIBLE);
 textViewMed.setVisibility(View.INVISIBLE);
 fetchButton.setVisibility(View.VISIBLE);
 }
 }
 });
 insertButton.setOnClickListener(new View.OnClickListener() {
 @Override
 public void onClick(View view) {
 String name = medicineName.getText().toString();
 String date = medicineDate.getText().toString();
 String time = dayTimeSpinner.getSelectedItem().toString();
 boolean insert = dbConnection.insertValues(name,date,time);
 if(insert==true){
 Toast.makeText(getApplicationContext(),"Data inserted Successfully",
Toast.LENGTH_SHORT).show();
 medicineName.setText(null);
 medicineDate.setText(null);
 else
 Toast.makeText(getApplicationContext(), "Data insertion unsuccessful",
Toast.LENGTH_SHORT).show();
 }
 });
 fetchButton.setOnClickListener(new View.OnClickListener() {
 @Override
 public void onClick(View view) {
 String date = medicineDate.getText().toString();
 String time = dayTimeSpinner.getSelectedItem().toString();
 String med = "";
 Cursor cu = dbConnection.RetrieveData(date, time);
 cu.moveToFirst();
 do{
 med = med+
(String.valueOf(cu.getString(cu.getColumnIndex("medicineName"))));
 med+="\n";
 }while (cu.moveToNext());
 Toast.makeText(getApplicationContext(), med, Toast.LENGTH_LONG).show();
 }
 });
 }
}
