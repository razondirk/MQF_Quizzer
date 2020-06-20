package com.razon.mqfquizzer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

import java.io.InputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String correctAnswer, answerA, answerB, answerC, answerD;
    int[] questionUsed;
    int counter;
    InputStream myInput;
    POIFSFileSystem myFileSystem;
    HSSFWorkbook myWorkBook;
    HSSFSheet myQuestionSheet, myAnswerSheet, myCorrectAnswerSheet;
    Row myQuestionRow, myAnswerRow, myCorrectAnswerRow;
    TextView questionTextBoxToChange, answerATextBoxToChange, answerBTextBoxToChange, answerCTextBoxToChange, answerDTextBoxToChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionUsed = new int[171];
        counter = 0;
        questionUsed[0] = 1;
        questionTextBoxToChange = (TextView) findViewById(R.id.questionTextBox);
        answerATextBoxToChange = (TextView) findViewById(R.id.answerATextbox);
        answerBTextBoxToChange = (TextView) findViewById(R.id.answerBTextbox);
        answerCTextBoxToChange = (TextView) findViewById(R.id.answerCTextbox);
        answerDTextBoxToChange = (TextView) findViewById(R.id.answerDTextbox);
        LoadFile();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        UpdateTheScreen();
    }

    public void LoadFile() {
        try {
            myInput = getAssets().open("mqf.xls");
            myFileSystem = new POIFSFileSystem(myInput);
            myWorkBook = new HSSFWorkbook(myFileSystem);
            myQuestionSheet = myWorkBook.getSheetAt(0);
            myAnswerSheet = myWorkBook.getSheetAt(1);
            myCorrectAnswerSheet = myWorkBook.getSheetAt(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CheckAnswer(int answerSelected) {
        if (answerSelected == 0 && answerA != correctAnswer)
            Toast.makeText(this, correctAnswer, Toast.LENGTH_LONG).show();
        if (answerSelected == 1 && answerB != correctAnswer)
            Toast.makeText(this, correctAnswer, Toast.LENGTH_LONG).show();
        if (answerSelected == 2 && answerC != correctAnswer)
            Toast.makeText(this, correctAnswer, Toast.LENGTH_LONG).show();
        if (answerSelected == 3 && answerD != correctAnswer)
            Toast.makeText(this, correctAnswer, Toast.LENGTH_LONG).show();
        UpdateTheScreen();
    }

    public void UpdateTheScreen() {
        if (counter == 170) {
            Toast.makeText(this, "This is the end of the program", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        int questionNumber = RandomizeTheNumber();
        counter++;
        if (questionUsed[questionNumber] == 1) {
            while (questionUsed[questionNumber] == 1) {
                questionNumber = RandomizeTheNumber();
            }
        }
        questionUsed[questionNumber] = 1;
        myQuestionRow = myQuestionSheet.getRow(questionNumber);
        myAnswerRow = myAnswerSheet.getRow(questionNumber);
        myCorrectAnswerRow = myCorrectAnswerSheet.getRow(questionNumber);
        questionTextBoxToChange.setText(myQuestionRow.getCell(0).getStringCellValue());
        answerATextBoxToChange.setText(myAnswerRow.getCell(0).getStringCellValue());
        answerBTextBoxToChange.setText(myAnswerRow.getCell(1).getStringCellValue());
        answerCTextBoxToChange.setText(myAnswerRow.getCell(2).getStringCellValue());
        answerDTextBoxToChange.setText(myAnswerRow.getCell(3).getStringCellValue());
        correctAnswer = myCorrectAnswerRow.getCell(0).getStringCellValue();
        answerA = myAnswerRow.getCell(0).getStringCellValue();
        answerB = myAnswerRow.getCell(1).getStringCellValue();
        answerC = myAnswerRow.getCell(2).getStringCellValue();
        answerD = myAnswerRow.getCell(3).getStringCellValue();
    }

    public int RandomizeTheNumber() {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(170);
    }

    public void answerAClicked(View view) {
        CheckAnswer(0);
    }

    public void answerBClicked(View view) {
        CheckAnswer(1);
    }

    public void answerCClicked(View view) {
        CheckAnswer(2);
    }

    public void answerDClicked(View view) {
        CheckAnswer(3);
    }
}