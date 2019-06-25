package umte.fim.uhk.cz.ctapplication.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import umte.fim.uhk.cz.ctapplication.CTActivity;
import umte.fim.uhk.cz.ctapplication.R;

public class MonitorFragment extends Fragment {

    private TextView txtMonitor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monitor, container, false);

        Button btnClear = view.findViewById(R.id.btnClearMonitor);
        txtMonitor = view.findViewById(R.id.txtMonitor);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Vymaze jenom textView ne cely arraylist
                txtMonitor.setText("");
            }
        });

        setMonitor();

        return view;
    }

    @SuppressLint("ResourceAsColor")
    private void setMonitor() {
        txtMonitor.setText("");
        Toast.makeText(getActivity(), "monitor vypis", Toast.LENGTH_LONG).show();
        if (CTActivity.monitorLogs.size() > 0)
            for (int i = 0; i < CTActivity.monitorLogs.size(); i++) {
                SpannableStringBuilder builder = new SpannableStringBuilder();

                SpannableString date = SpannableString.valueOf(CTActivity.monitorLogs.get(i).getTime() + ": ");
                date.setSpan(new ForegroundColorSpan(Color.BLACK), 0, date.length(), 0);
                builder.append(date);

                SpannableString message = SpannableString.valueOf(CTActivity.monitorLogs.get(i).getMessage());


                if (CTActivity.monitorLogs.get(i).isRecieved()) {
                    message.setSpan(new ForegroundColorSpan(Color.GREEN), 0, message.length(), 0);
                    builder.append(message);

                } else {
                    message.setSpan(new ForegroundColorSpan(Color.RED), 0, message.length(), 0);
                    builder.append(message);

                }
                txtMonitor.append(builder);

            }

    }
}
