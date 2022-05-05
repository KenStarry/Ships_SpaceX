package com.example.shipsspacex;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

public class ShipDetailsBottomSheet extends BottomSheetDialogFragment {

    private String shipNameData, shipIDData, shipModelData, shipTypeData,
            activeData, weightLbsData, weightKgData, homePortData,
            statusData, speedKnData, courseDegData, shipImageData;
    private TextView bottomTitle, shipDetailsID, shipDetailsModel, shipDetailsType, shipDetailsWeight, shipDetailsSpeed, shipSummaryText;
    private ImageView shipDescImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialogFragment bottomSheetDialogFragment = new BottomSheetDialogFragment();
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.ship_details_bottom_sheet_layout, container, false);
        bottomTitle = v.findViewById(R.id.bottomTitle);
        shipDetailsID = v.findViewById(R.id.shipDetailsID);
        shipDetailsModel = v.findViewById(R.id.shipDetailsModel);
        shipDetailsType = v.findViewById(R.id.shipDetailsType);
        shipDetailsWeight = v.findViewById(R.id.shipDetailsWeight);
        shipDetailsSpeed = v.findViewById(R.id.shipDetailsSpeed);
        shipSummaryText = v.findViewById(R.id.shipSummaryText);
        shipDescImage = v.findViewById(R.id.shipDescImage);

        Bundle data = getArguments();

        if (data != null) {
            //  Getting the data from the Bundle class
            shipNameData = data.getString("ShipNameData");
            shipIDData = data.getString("ShipIDData");
            shipModelData = data.getString("ShipModelData");
            shipTypeData = data.getString("ShipTypeData");
            activeData = data.getString("ActiveData");
            weightLbsData = data.getString("WeightLbsData");
            weightKgData = data.getString("WeightKgData");
            homePortData = data.getString("HomePortData");
            statusData = data.getString("StatusData");
            speedKnData = data.getString("SpeedKnData");
            courseDegData = data.getString("CourseDegData");
            shipImageData = data.getString("ImageData");
        }
        bottomTitle.setText(shipNameData);
        shipDetailsID.setText(new StringBuilder().append("ID : ").append(shipIDData));
        shipDetailsModel.setText(new StringBuilder().append("Model : ").append(shipModelData));
        shipDetailsType.setText(new StringBuilder().append("Type : ").append(shipTypeData));
        shipDetailsWeight.setText(new StringBuilder().append("Weight (kg) : ").append(weightKgData));
        shipDetailsSpeed.setText(new StringBuilder().append("Speed : ").append(speedKnData));

        //  Summary text
        shipSummaryText.setText(new StringBuilder().append("The ").append(shipNameData).append(" is a ")
                .append(shipTypeData)
                .append(" ship whose home port, ")
                .append(homePortData)
                .append(", is one of the magnificent landing ports in the world. Currently, the ship's active status is ")
                .append(activeData));
        Picasso.get().load(shipImageData).into(shipDescImage);

        return v;
    }
}
