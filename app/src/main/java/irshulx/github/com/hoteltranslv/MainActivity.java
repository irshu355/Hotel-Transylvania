package irshulx.github.com.hoteltranslv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView txtAdults;
    TextView txtChildren;
    TextView txtInfants;
    TextView lblresult;
    Button btnCalculate;
    int adults, children, infants;
    int rooms = 0;

    private final int MAX_ROOMS_PER_BOOKKING = 3;

    private final int MAX_ADULTS = 3;
    private final int MAX_CHILDREN = 3;
    private final int MAX_INFANTS = 3;
    private final int MA_KIDS_PER_ROOMS = MAX_CHILDREN +MAX_INFANTS;
    private final int MAX_HEADCOUNT = MAX_ROOMS_PER_BOOKKING * (MAX_ADULTS + MAX_CHILDREN + MAX_INFANTS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtAdults = findViewById(R.id.adults);
        txtChildren = findViewById(R.id.children);
        txtInfants = findViewById(R.id.infants);
        lblresult  = findViewById(R.id.result);
        btnCalculate = findViewById(R.id.btnCalculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateRoomsRequired();
            }
        });

    }

    private void calculateRoomsRequired() {

        adults = Integer.parseInt(txtAdults.getText().toString());
        children = Integer.parseInt(txtChildren.getText().toString());
        infants = Integer.parseInt(txtInfants.getText().toString());



        if(adults + children + infants > MAX_HEADCOUNT) {
            lblresult.setText("SORRY, booking exceeds maximum guests");
            return;
        }

        if(adults + children > 7){
            lblresult.setText("SORRY, booking exceeds maximum adult children");
            return;
        }

        if(children > MAX_ROOMS_PER_BOOKKING * MAX_CHILDREN){
            lblresult.setText("SORRY, booking exceeds maximum  children");
            return;
        }

        if(infants > MAX_ROOMS_PER_BOOKKING * MAX_INFANTS){
            lblresult.setText("SORRY, booking exceeds maximum  infants");
            return;
        }




        if(infants + children > 1 && adults ==0){
            lblresult.setText("SORRY, we need atleast an adult for supervision");
            return;
        }

        List<Integer> roomDistribution = new ArrayList<>();



        for(int i = 0; i < MAX_ROOMS_PER_BOOKKING; i++){
            if(children == 0) break;
            roomDistribution.add(children > MAX_CHILDREN ? MAX_CHILDREN : children);
            children -= roomDistribution.get(i);
        }


        if(infants > 0) {
            for (int i = 0; i < MAX_ROOMS_PER_BOOKKING; i++) {

                if(infants ==0) break;

                if(i>=roomDistribution.size()){
                    roomDistribution.add(infants > MAX_INFANTS ?MAX_INFANTS : infants);
                    infants -= roomDistribution.get(i);
                    continue;
                }

                int needed = infants > MAX_INFANTS ? MAX_INFANTS : infants;
                roomDistribution.set(i, roomDistribution.get(i)+ needed);
                infants-=needed;
            }
        }


        if(roomDistribution.size() > adults){
            lblresult.setText("SORRY, we need more adults as we can't let the kids alone in rooms");
            return;
        }


        if(roomDistribution.size() < MAX_ROOMS_PER_BOOKKING){
            float adultPerRooms = adults/3.0f;
            if(adultPerRooms > 3.0f){
                lblresult.setText("SORRY, too many adults");
                return;
            }else{
                if(adultPerRooms % 1 != 0){
                    adultPerRooms++;
                }
                int diff =  (int) adultPerRooms - roomDistribution.size();
                for(int i = 0; i< diff ;i++){
                    roomDistribution.add(0);
                }
            }
        }



        int j = 0;
        while (adults>0){
            if(j>=roomDistribution.size()){
                j = 0;
            }
            roomDistribution.set(j,roomDistribution.get(j) + 1);
            adults--;
            j++;
        }




    }
}

