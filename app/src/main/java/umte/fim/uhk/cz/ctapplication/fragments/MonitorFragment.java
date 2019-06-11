package umte.fim.uhk.cz.ctapplication.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import umte.fim.uhk.cz.ctapplication.CTActivity;
import umte.fim.uhk.cz.ctapplication.R;

public class MonitorFragment extends Fragment {
    private Button btnClear;
    private TextView txtMonitor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monitor,container,false);

        btnClear = view.findViewById(R.id.btnClearMonitor);
        txtMonitor = view.findViewById(R.id.txtMonitor);

        setMonitor();

        return view;
    }

    @SuppressLint("ResourceAsColor")
    private void setMonitor() {

        if (CTActivity.monitorLogs.size() > 0)
        for (int i = 0; i < CTActivity.monitorLogs.size(); i++){
            txtMonitor.setTextColor(R.color.colorOrange);
            txtMonitor.setText(CTActivity.monitorLogs.get(i).getTime().toString() + ": ");
            if (CTActivity.monitorLogs.get(i).isRecieved())
                txtMonitor.setTextColor(R.color.colorBlack);
            else txtMonitor.setTextColor(R.color.colorGreen);
            txtMonitor.append(CTActivity.monitorLogs.get(i).getMessage() + "\n");
        }

    }
}
