package kr.ac.kookmin.mobilehw1;

import java.math.BigInteger;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * 
 * @author Taesung Jung
 *
 */
public class Main extends Activity {
	
	private EditText lhs;
	private EditText rhs;
	private EditText result;
	private boolean isLhs;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main_layout);
	    
	    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
	    
	    lhs = new EditText(this);
	    lhs.setHint("left operand");
	    lhs.setFocusable(true);
	    lhs.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus)
					isLhs = true;
				else
					isLhs = false;
			}
		});
	    
	    rhs = new EditText(this);
	    rhs.setHint("right operand");

		result = new EditText(this);
	    result.setHint("result");
	    
	    linearLayout.addView(lhs);
	    linearLayout.addView(rhs);
	    
	    // number button layout
	    LinearLayout[] numberLayout = new LinearLayout[2];
	    
	    for(int i = 0; i < numberLayout.length; ++i) {
	    	numberLayout[i] = new LinearLayout(this);
	    	numberLayout[i].setOrientation(LinearLayout.HORIZONTAL);
	    }

	    for(int i = 0; i < 10; ++i) {
	    	Button button = new Button(this);
	    	button.setText("" + i);
	    	button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Button button = (Button) v;

					if(isLhs) {
						String original = lhs.getText().toString();
						lhs.setText(original + button.getText());
					} else {
						String original = rhs.getText().toString();
						rhs.setText(original + button.getText());
					}
					
				}
			});

	    	numberLayout[i / 5].addView(button);
	    }

	    linearLayout.addView(numberLayout[0]);
	    linearLayout.addView(numberLayout[1]);
	    linearLayout.addView(result);
	    
	    Button addButton = new Button(this);
	    addButton.setText("add");
	    addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				BigInteger l = getBigInteger(lhs.getText().toString());
				BigInteger r = getBigInteger(rhs.getText().toString());
				result.setText(l.add(r).toString());
				
			}
		});

	    Button subtractButton = new Button(this);
	    subtractButton.setText("subtract");
	    subtractButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				BigInteger l = getBigInteger(lhs.getText().toString());
				BigInteger r = getBigInteger(rhs.getText().toString());
				result.setText(l.subtract(r).toString());
				
			}
		});

	    Button multiplyButton = new Button(this);
	    multiplyButton.setText("multiply");
	    multiplyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				BigInteger l = getBigInteger(lhs.getText().toString());
				BigInteger r = getBigInteger(rhs.getText().toString());
				result.setText(l.multiply(r).toString());
				
			}
		});

	    Button divideButton = new Button(this);
	    divideButton.setText("divide");
	    divideButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String l = lhs.getText().toString();
				String r = rhs.getText().toString();
				float value = Float.parseFloat(l) / Float.parseFloat(r);
				result.setText(Float.toString(value));
				
			}
		});
	    
	    linearLayout.addView(addButton);
	    linearLayout.addView(subtractButton);
	    linearLayout.addView(multiplyButton);
	    linearLayout.addView(divideButton);
	    
	    isLhs = true;

	    // hide softkeyboard
	    InputMethodManager imm = (InputMethodManager)getSystemService(
	    	      Context.INPUT_METHOD_SERVICE);

	    imm.hideSoftInputFromWindow(lhs.getWindowToken(), 0);
	    imm.hideSoftInputFromWindow(rhs.getWindowToken(), 0);
	}
	
	private static BigInteger getBigInteger(String n) {
		return new BigInteger(n);
	}

}
