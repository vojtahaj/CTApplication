package umte.fim.uhk.cz.ctapplication.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import umte.fim.uhk.cz.ctapplication.CTActivity;
import umte.fim.uhk.cz.ctapplication.R;

public class MonitorFragment extends Fragment {
    private Button btnClear;
    private TextView txtMonitor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monitor, container, false);

        btnClear = view.findViewById(R.id.btnClearMonitor);
        txtMonitor = view.findViewById(R.id.txtMonitor);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Vymaze jenom textView ne cely arraylist
                txtMonitor.setText("");
                //  Toast.makeText(getActivity(), "cistim",Toast.LENGTH_SHORT).show();
            }
        });

        setMonitor();

        return view;
    }

    @SuppressLint("ResourceAsColor")
    private void setMonitor() {
        Toast.makeText(getActivity(), "monitor vypis", Toast.LENGTH_LONG).show();
        if (CTActivity.monitorLogs.size() > 0)
            for (int i = 0; i < CTActivity.monitorLogs.size(); i++) {
                txtMonitor.setTextColor(Color.YELLOW);
                txtMonitor.append(CTActivity.monitorLogs.get(i).getTime().toString() + ": ");
                if (CTActivity.monitorLogs.get(i).isRecieved())
                    txtMonitor.setTextColor(Color.BLACK);
                else txtMonitor.setTextColor(Color.GREEN);
                txtMonitor.append(CTActivity.monitorLogs.get(i).getMessage() + "\n");
                // System.out.println("Vypisuji do monitooru: " + CTActivity.monitorLogs.size());
            }

    }
}
