package umte.fim.uhk.cz.ctapplication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import umte.fim.uhk.cz.ctapplication.R;

public class SettingFragment extends Fragment {

    private Spinner spinnerSequence;
    private Button btnSet;
    private EditText dsecYellow;
    private EditText dsecGreen;
    private RadioButton radioAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        spinnerSequence = view.findViewById(R.id.spinner_sequence);
        String[] sequenceValues = getResources().getStringArray(R.array.CT_set_sequence);

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, sequenceValues);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerSequence.setAdapter(spinnerAdapter);

        dsecYellow = view.findViewById(R.id.delay_y);
        dsecGreen = view.findViewById(R.id.delay_g);
        radioAdd = view.findViewById(R.id.radio_add);

        btnSet = view.findViewById(R.id.btn_set_values);
        btnSet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String builder;

                String s = spinnerSequence.getSelectedItem().toString();


                builder = dsecYellow.getText().toString() + "\r\n" +
                        dsecGreen.getText().toString() + "\r\n" +
                        getValueFromSpinner(s) + "\r\n";
                if (radioAdd.isChecked())
                    builder += "VA\r\n";
                else builder += "VB\r\n";

                Toast.makeText(getActivity(), builder, Toast.LENGTH_LONG).show();
                //todo log.add(builder);
                //todo socket.write(builder);

            }
        });

        return view;
    }

    private String getValueFromSpinner(String s) {
        switch (s) {
            case "Tma":
                return "R0";

            case "Startuj":
                return "S1";
            case "Přibližuj":
                return "S2";
            case "Hned startuj":
                return "S3";
            case "Startuj R/G":
                return "S4";
            case "Ručně":
                return "SE";
            default:
                return "SA";
        }
    }
}
