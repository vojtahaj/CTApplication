package umte.fim.uhk.cz.ctapplication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import umte.fim.uhk.cz.ctapplication.R;

public class CTFragment extends Fragment implements View.OnClickListener {
    TextView NL, NR, FR, FL, Y1, Y2, Y3, G, RL, RR;
    Button btnRed, btnGo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ct, container, false);
        NL = view.findViewById(R.id.textViewNL);
        FL = view.findViewById(R.id.textViewFL);
        NR = view.findViewById(R.id.textViewNR);
        FR = view.findViewById(R.id.textViewFR);
        Y1 = view.findViewById(R.id.textViewY1);
        Y2 = view.findViewById(R.id.textViewY2);
        Y3 = view.findViewById(R.id.textViewY3);
        G = view.findViewById(R.id.textViewG);
        RL = view.findViewById(R.id.textViewRL);
        RR = view.findViewById(R.id.textViewRR);

        btnGo = view.findViewById(R.id.btn_start);
        btnRed = view.findViewById(R.id.btn_red);

        btnGo.setOnClickListener(this);
        btnRed.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_red:
                System.out.println("rozsvicena cervena");
                //send writer.send("R0\r\nR1\r\n");
                break;
            case R.id.btn_start:
                System.out.println("startuji");
                //send writer.start();
        }

    }
}
