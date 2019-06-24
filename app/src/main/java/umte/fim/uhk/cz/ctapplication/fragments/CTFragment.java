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

import java.io.DataOutputStream;

import java.io.IOException;
import java.util.Objects;

import umte.fim.uhk.cz.ctapplication.CTActivity;
import umte.fim.uhk.cz.ctapplication.R;
import umte.fim.uhk.cz.ctapplication.model.ChristmasTree;
import umte.fim.uhk.cz.ctapplication.utils.MyMonitorLog;
import umte.fim.uhk.cz.ctapplication.utils.SocketData;

public class CTFragment extends Fragment implements View.OnClickListener {

    private TextView NL;
    private TextView NR;
    private TextView FR;
    private TextView FL;
    private TextView Y1;
    private TextView Y2;
    private TextView Y3;
    private TextView G;
    private TextView RL;
    private TextView RR;
    private Button btnRed, btnGo;

    private ChristmasTree christmasTree;
    private SocketData socketData;
    private DataOutputStream outputStream;

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

        socketData = ((CTActivity) Objects.requireNonNull(getActivity())).getSocketData();
        if (socketData == null) {
            System.out.println("socketdata Null");
        } else {
            System.out.println("socketdata notnull");
            outputStream = socketData.getOutputStream();
        }

        christmasTree = CTActivity.lightImpl.getCT();
        updateTextView(christmasTree);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        christmasTree = CTActivity.lightImpl.getCT();
        updateTextView(christmasTree);
    }

    @Override
    public void onClick(View view) {
        final String s;
        switch (view.getId()) {
            case R.id.btn_red:
                System.out.println("rozsvicena cervena");
                s = "RL1\r\nRR1\r\n";
                break;
            case R.id.btn_start:
                System.out.println("startuji");
                s = christmasTree.getSequence();
                break;
            default:
                s = "SA";
        }
//        Message msg = Message.obtain();
//        msg.obj = s;
//    SocketData.handler.handleMessage(msg);
        new Thread() {
            @Override
            public void run() {
                try {
                    outputStream.writeBytes(s);
                    CTActivity.monitorLogs.add(new MyMonitorLog(s, false));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error ctfragmentClick IO");
                }
            }
        }.start();
    }

    public void updateTextView(ChristmasTree tree) {
        // System.out.println();

        try {
            if (tree.getrR().isState()) {
                RR.setBackgroundColor(getResources().getColor(R.color.colorRed));
            } else RR.setBackgroundColor(getResources().getColor(R.color.colorGray));
            if (tree.getrL().isState()) {
                RL.setBackgroundColor(getResources().getColor(R.color.colorRed));
            } else RL.setBackgroundColor(getResources().getColor(R.color.colorGray));
            if (tree.getG().isState()) {
                G.setBackgroundColor(getResources().getColor(R.color.colorGreen));
            } else G.setBackgroundColor(getResources().getColor(R.color.colorGray));
            if (tree.getY1().isState()) {
                Y1.setBackgroundColor(getResources().getColor(R.color.colorOrange));
            } else Y1.setBackgroundColor(getResources().getColor(R.color.colorGray));
            if (tree.getY2().isState()) {
                Y2.setBackgroundColor(getResources().getColor(R.color.colorOrange));
            } else Y2.setBackgroundColor(getResources().getColor(R.color.colorGray));
            if (tree.getY3().isState()) {
                Y3.setBackgroundColor(getResources().getColor(R.color.colorOrange));
            } else Y3.setBackgroundColor(getResources().getColor(R.color.colorGray));
            if (tree.getnL().isState()) {
                NL.setBackgroundColor(getResources().getColor(R.color.colorOrange));
            } else NL.setBackgroundColor(getResources().getColor(R.color.colorGray));
            if (tree.getfL().isState()) {
                FL.setBackgroundColor(getResources().getColor(R.color.colorOrange));
            } else FL.setBackgroundColor(getResources().getColor(R.color.colorGray));
            if (tree.getfR().isState()) {
                FR.setBackgroundColor(getResources().getColor(R.color.colorOrange));
            } else FR.setBackgroundColor(getResources().getColor(R.color.colorGray));
            if (tree.getnR().isState()) {
                NR.setBackgroundColor(getResources().getColor(R.color.colorOrange));
            } else NR.setBackgroundColor(getResources().getColor(R.color.colorGray));

        } catch (
                Exception exception) {
            exception.printStackTrace();
            System.out.println("CT Fragment is not visible");
        }
        //RL.setText("RL1");
        //RL.setBackgroundResource(R.color.colorOrange);

    }
}
