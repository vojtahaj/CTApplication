package umte.fim.uhk.cz.ctapplication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

import umte.fim.uhk.cz.ctapplication.CTActivity;
import umte.fim.uhk.cz.ctapplication.R;
import umte.fim.uhk.cz.ctapplication.utils.MyMonitorLog;
import umte.fim.uhk.cz.ctapplication.utils.SocketData;

public class SettingFragment extends Fragment {

    private Spinner spinnerSequence;
    private Button btnSet;
    private EditText dsecYellow;
    private EditText dsecGreen;
    private RadioButton radioAdd;

    private SocketData socketData;
    private DataOutputStream outputStream;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        spinnerSequence = view.findViewById(R.id.spinner_sequence);
        String[] sequenceValues = getResources().getStringArray(R.array.CT_set_sequence);

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(Objects.requireNonNull(this.getActivity()), android.R.layout.simple_spinner_item, sequenceValues);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerSequence.setAdapter(spinnerAdapter);

        dsecYellow = view.findViewById(R.id.delay_y);
        dsecGreen = view.findViewById(R.id.delay_g);
        radioAdd = view.findViewById(R.id.radio_add);

        socketData = ((CTActivity) Objects.requireNonNull(getActivity())).getSocketData();
        if (socketData == null) {
            System.out.println("socketdata Null");
        } else {
            System.out.println("socketdata notnull");
            outputStream = socketData.getOutputStream();
        }

        btnSet = view.findViewById(R.id.btn_set_values);
        btnSet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String builder;

                String s = spinnerSequence.getSelectedItem().toString();

                builder = convertRYG(dsecGreen.getText().toString(), dsecYellow.getText().toString());

                builder = builder +
                        getValueFromSpinner(s) + "\r\n";
                if (radioAdd.isChecked())
                    builder += "VA\r\n";
                else builder += "VB\r\n";

                Toast.makeText(getActivity(), builder, Toast.LENGTH_LONG).show();
                sendMessage(builder);

            }
        });

        return view;
    }

    private String convertRYG(String green, String yellow) {
        int gInt = Integer.valueOf(green);
        int yInt = Integer.valueOf(yellow);
        char gChar = (char) gInt;
        char yChar = (char) yInt;
        System.out.println("gInt>char " + gChar);
        System.out.println("yInt>char " + yChar);
        String string = "T";
        //todo update semafor setSequence

        return "T123\r\n";
    }

    private void sendMessage(final String builder) {
        new Thread() {
            @Override
            public void run() {
                try {
                    outputStream.writeBytes(builder);
                    CTActivity.monitorLogs.add(new MyMonitorLog(builder, false));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private String getValueFromSpinner(String s) {
        switch (s) {
            case "Tma":
                return "R0\r\n";
            case "Startuj":
                return "S1\r\n";
            case "Přibližuj":
                return "S2\r\n";
            case "Hned startuj":
                return "S3\r\n";
            case "Startuj R/G":
                return "S4\r\n";
            case "Ručně":
                return "SE\r\n";
            default:
                return "SA\r\n";
        }
    }
}
