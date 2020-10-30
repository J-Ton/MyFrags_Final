package com.example.myfrags;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class Fragment4 extends Fragment {

    private FragsData fragsData;
    private Observer<Integer> numberObserver;

    private EditText edit;
    private TextWatcher textWatcher;
    private boolean turnOffWatcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_4, container, false);

        edit = view.findViewById(R.id.editTextNumber);

        fragsData = new ViewModelProvider(requireActivity()).get(FragsData.class);

        numberObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer newInteger) {
                turnOffWatcher = true;
                edit.setText(newInteger.toString());
            }
        };

        fragsData.counter.observe(getViewLifecycleOwner(), numberObserver);

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(!turnOffWatcher){

                    Integer i;
                    try{
                        if (s.toString()!=null && !s.toString().equals("-")){
                            /**
                             * Gdy pole jest puste lub zawiera tylko znak "-"
                             * nie aktualizujemy zmienną "counter"
                             */
                            i = Integer.parseInt( s.toString() );
                            fragsData.counter.setValue(i);
                        }
                        else{
                        }
                    } catch (NumberFormatException e){
                    }
                } else {
                    turnOffWatcher = !turnOffWatcher;
                    EditText et = view.findViewById(R.id.editTextNumber);
                    et.setSelection(et.getText().length());
                }
            }
        };

        edit.addTextChangedListener(textWatcher);

        return view;
    }
}
