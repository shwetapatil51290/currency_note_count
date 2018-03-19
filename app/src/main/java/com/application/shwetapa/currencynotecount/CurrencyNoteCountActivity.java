package com.application.shwetapa.currencynotecount;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.TableLayout.GONE;
import static android.widget.TableLayout.OnClickListener;
import static android.widget.TableLayout.VISIBLE;

public class CurrencyNoteCountActivity extends AppCompatActivity {

    //Views
    private TableLayout mTableLayout;
    private EditText mAmountET;
    private TextView mTotalCountTV;
    private TextView mTotalAmountTV;
    private Button mEnterBtn;
    private LinearLayout mHeading;
    private LinearLayout mTotalLL;
    private ScrollView mScrollView;

    private int limit;
    private double mAmount;

    //Initializing currency variables
    int mTwoK = 0, mOneK = 0, mFiveHundred = 0, mTwoHundred = 0, mOneHundred = 0, mFifty = 0, mTwenty = 0,
            mTen = 0, mFive = 0, mOne = 0, mFiftyPs = 0, mTwentyFivePs = 0;
    //Initializing arrays
    private String[] currencyNotesArr = {"Rs. 2000", "Rs. 1000", "Rs. 500", "Rs. 200", "Rs. 100",
            "Rs. 50", "Rs. 20", "Rs. 10", "Rs. 5", "Re. 1", "Paise 50", "Paise 25"};
    private String[] totalNotesStringArr = {"2000", "1000", "500", "200", "100", "50", "20", "10", "5", "1", "0.50", "0.25"};
    private double[] totalNotesArr = {2000, 1000, 500, 200, 100, 50, 20, 10, 5, 1, 0.50, 0.25};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_note_count);
        initializeViews();
        mEnterBtn.setOnClickListener(new MyOnClickListener());
    }

    private void initializeViews() {
        mTableLayout = findViewById(R.id.table);
        mAmountET = findViewById(R.id.amountET);
        mEnterBtn = findViewById(R.id.enterButton);
        mTotalCountTV = findViewById(R.id.totalCountTV);
        mTotalAmountTV = findViewById(R.id.totalAmountTV);
        mHeading = findViewById(R.id.heading);
        mTotalLL = findViewById(R.id.totalLL);
        mScrollView = findViewById(R.id.scrollView);
    }

    private class MyOnClickListener implements OnClickListener {

        @Override
        public void onClick(View view) {
            hideKeyboard();

            if (mAmountET.getText().toString().equals("")) {
                Toast.makeText(CurrencyNoteCountActivity.this, "Please enter a valid amount!!", Toast.LENGTH_LONG).show();
                mHeading.setVisibility(GONE);
                mTotalLL.setVisibility(GONE);
                removeTableRows();
            } else if (Double.parseDouble(mAmountET.getText().toString()) > 9999999) {
                Toast.makeText(CurrencyNoteCountActivity.this, "Currency out of stock!!", Toast.LENGTH_LONG).show();
                mAmountET.setText("");
                mHeading.setVisibility(GONE);
                mTotalLL.setVisibility(GONE);
                removeTableRows();
            } else {
                mAmount = Double.parseDouble(mAmountET.getText().toString());
                initializeVariables();
                amountCalculation();
                removeTableRows();
                if (limit == 1) {
                    Toast.makeText(CurrencyNoteCountActivity.this, "Currency out of stock!!", Toast.LENGTH_LONG).show();
                    mAmountET.setText("");
                    mHeading.setVisibility(GONE);
                    mTotalLL.setVisibility(GONE);
                } else {
                    initializeTableRows();
                }
            }
        }
    }

    private void initializeVariables() {
        limit = 0;
        mTwoK = 0;
        mOneK = 0;
        mFiveHundred = 0;
        mTwoHundred = 0;
        mOneHundred = 0;
        mFifty = 0;
        mTwenty = 0;
        mTen = 0;
        mFive = 0;
        mOne = 0;
        mFiftyPs = 0;
        mTwentyFivePs = 0;
    }

    /**
     * Code for calculating amount for currency entered by user
     */
    private void amountCalculation() {
        String[] arr = String.valueOf(mAmount).split("\\.");
        int[] intArr = new int[2];
        intArr[0] = Integer.parseInt(arr[0]);
        intArr[1] = Integer.parseInt(arr[1]);
        int rupees = intArr[0];
        int paise = intArr[1];

        if (paise == 5) {
            paise = paise * 10;
        }

        if (!(paise == 25 || paise == 50 || paise == 75) && !(paise == 0)) {
            Toast.makeText(CurrencyNoteCountActivity.this, "Please enter valid paise amount - 0.25, 0.50, 0.75",
                    Toast.LENGTH_LONG).show();
            mAmountET.setText("");
            mHeading.setVisibility(GONE);
            mTotalLL.setVisibility(GONE);
            return;
        }

        if (String.valueOf(paise).length() >= 3) {
            Toast.makeText(CurrencyNoteCountActivity.this, "Please enter valid paise amount - 0.25, 0.50, 0.75",
                    Toast.LENGTH_LONG).show();
            mAmountET.setText("");
            mHeading.setVisibility(GONE);
            mTotalLL.setVisibility(GONE);
            return;
        }

        if (rupees >= 2000) {
            mTwoK = rupees / 2000;
            rupees = rupees % 2000;
            if (mTwoK >= 21) {
                rupees = rupees + ((mTwoK - 20) * 2000);
                mTwoK = 20;
            }
        }
        if (rupees >= 1000) {
            mOneK = rupees / 1000;
            rupees = rupees % 1000;
            if (mOneK >= 21) {
                rupees = rupees + ((mOneK - 20) * 1000);
                mOneK = 20;
            }
        }
        if (rupees >= 500) {
            mFiveHundred = rupees / 500;
            rupees = rupees % 500;
            if (mFiveHundred >= 21) {
                rupees = rupees + ((mFiveHundred - 20) * 500);
                mFiveHundred = 20;
            }
        }
        if (rupees >= 200) {
            mTwoHundred = rupees / 200;
            rupees = rupees % 200;
            if (mTwoHundred >= 21) {
                rupees = rupees + ((mTwoHundred - 20) * 200);
                mTwoHundred = 20;
            }
        }
        if (rupees >= 100) {
            mOneHundred = rupees / 100;
            rupees = rupees % 100;
            if (mOneHundred >= 21) {
                rupees = rupees + ((mOneHundred - 20) * 100);
                mOneHundred = 20;
            }
        }
        if (rupees >= 50) {
            mFifty = rupees / 50;
            rupees = rupees % 50;
            if (mFifty >= 21) {
                rupees = rupees + ((mFifty - 20) * 50);
                mFifty = 20;
            }
        }
        if (rupees >= 20) {
            mTwenty = rupees / 20;
            rupees = rupees % 20;
            if (mTwenty >= 21) {
                rupees = rupees + ((mTwenty - 20) * 20);
                mTwenty = 20;
            }
        }
        if (rupees >= 10) {
            mTen = rupees / 10;
            rupees = rupees % 10;
            if (mTen >= 21) {
                rupees = rupees + ((mTen - 20) * 10);
                mTen = 20;
            }
        }
        if (rupees >= 5) {
            mFive = rupees / 5;
            rupees = rupees % 5;
            if (mFive >= 21) {
                rupees = rupees + ((mFive - 20) * 5);
                mFive = 20;
            }
        }
        if (rupees >= 1) {
            mOne = rupees;
            rupees = 0;
            if (mOne >= 21) {
                rupees = rupees + ((mOne - 20));
                mOne = 20;
            }
            if (rupees > 15) {
                limit = 1;
            }
        }
        if (paise >= 50 || rupees < 16) {
            mFiftyPs = paise / 50;
            paise = paise % 50;

            rupees = rupees * 2;
            rupees = rupees + mFiftyPs;
            if (rupees >= 21) {
                rupees = rupees - 20;
                mFiftyPs = 20;
            } else {
                mFiftyPs = rupees;
                rupees = 0;
            }
        }
        if (paise >= 25 || ((rupees > 6) && (rupees < 16))) {
            mTwentyFivePs = paise / 25;

            rupees = rupees * 2;
            rupees = rupees + mTwentyFivePs;

            if (rupees >= 21) {
                limit = 1;
            } else {
                mTwentyFivePs = rupees;
            }
        }
    }

    /**
     * Removing table rows from table layout
     */
    private void removeTableRows() {
        mScrollView.setVisibility(GONE);
        int j = mTableLayout.getChildCount() - 1;
        while (mTableLayout.getChildCount() > 0) {
            TableRow row1 = (TableRow) mTableLayout.getChildAt(j);
            mTableLayout.removeView(row1);
            j = mTableLayout.getChildCount() - 1;
        }
    }

    /**
     * Initializing text views in table rows
     */
    private void initializeTableRows() {

        int[] currencyCountArr = {mTwoK, mOneK, mFiveHundred, mTwoHundred, mOneHundred, mFifty, mTwenty, mTen, mFive, mOne, mFiftyPs, mTwentyFivePs};
        for (int z = 0; z < currencyCountArr.length; z++) {
            if (!(currencyCountArr[z] == 0)) {
                TableRow row = new TableRow(getApplicationContext());

                TextView currencyTV = new TextView(getApplicationContext());
                TextView countTV = new TextView(getApplicationContext());
                TextView totalTV = new TextView(getApplicationContext());

                currencyTV.setTextSize(18);
                countTV.setTextSize(18);
                totalTV.setTextSize(18);

                currencyTV.setGravity(Gravity.CENTER_HORIZONTAL);
                countTV.setGravity(Gravity.CENTER_HORIZONTAL);
                totalTV.setGravity(Gravity.CENTER_HORIZONTAL);

                currencyTV.setWidth(70);
                countTV.setWidth(70);
                totalTV.setWidth(70);

                currencyTV.setTextColor(getResources().getColor(R.color.black));
                countTV.setTextColor(getResources().getColor(R.color.black));
                totalTV.setTextColor(getResources().getColor(R.color.black));

                currencyTV.setBackgroundResource(R.color.white);
                countTV.setBackgroundResource(R.color.white);
                totalTV.setBackgroundResource(R.color.white);

                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
                params.setMargins(1, 1, 1, 1);
                currencyTV.setLayoutParams(params);
                countTV.setLayoutParams(params);
                totalTV.setLayoutParams(params);

                currencyTV.setText(currencyNotesArr[z]);
                countTV.setText(String.valueOf(currencyCountArr[z]));
                String total = totalNotesStringArr[z] + "*" + String.valueOf(currencyCountArr[z]);
                totalTV.setText(total);

                row.addView(currencyTV);
                row.addView(countTV);
                row.addView(totalTV);

                mHeading.setVisibility(VISIBLE);
                mTotalLL.setVisibility(VISIBLE);
                mScrollView.setVisibility(VISIBLE);

                mTableLayout.addView(row, new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }

        int totalCount = 0;
        double totalValue = 0;
        for (int i = 0; i < currencyCountArr.length; i++) {
            totalCount = totalCount + currencyCountArr[i];
            totalValue = totalValue + (totalNotesArr[i] * currencyCountArr[i]);
        }
        mTotalCountTV.setText(String.valueOf(totalCount));
        mTotalAmountTV.setText(String.valueOf(totalValue));

        mAmountET.setText("");

    }

    /**
     * Code for hiding keyboard
     */
    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}





