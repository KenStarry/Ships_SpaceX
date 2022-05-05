package com.example.shipsspacex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShipsRecyclerAdapter extends RecyclerView.Adapter<ShipsRecyclerAdapter.ViewHolder>{

    private final ShipsRecyclerInterface shipsRecyclerInterface;

    //  Variables for arraylist and context
    ArrayList<ShipsModalClass> shipsModalClassArrayList;
    Context context;

    public ShipsRecyclerAdapter(ArrayList<ShipsModalClass> shipsModalClassArrayList, Context context, ShipsRecyclerInterface shipsRecyclerInterface) {
        this.shipsModalClassArrayList = shipsModalClassArrayList;
        this.context = context;
        this.shipsRecyclerInterface = shipsRecyclerInterface;
    }

    @NonNull
    @Override
    public ShipsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //  Inflating our ships row layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ship_row, parent, false);
        return new ViewHolder(view, shipsRecyclerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //  Binding the Views to our Modal Class
        ShipsModalClass modal = shipsModalClassArrayList.get(position);
        holder.shipStatusText.setText(modal.getShip_status());
        holder.shipNameText.setText(modal.getShip_name());
        holder.shipTypeText.setText(modal.getShip_type());
        //  Picasso Image loader to load our images
        Picasso.get().load(modal.getShip_image()).into(holder.shipImageView);
    }

    @Override
    public int getItemCount() {
        //  Size of the arraylist
        return shipsModalClassArrayList.size();
    }

    //  Function for filtering our arraylist
    public void filterList(ArrayList<ShipsModalClass> filteredList) {
        //  Add our filtered list in our shipsArrayList
        shipsModalClassArrayList = filteredList;
        //  Notify our adapter that there has been a change
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //  Variables for our views
        private TextView shipStatusText, shipNameText, shipTypeText;
        ImageView shipImageView;

        public ViewHolder(@NonNull View itemView, ShipsRecyclerInterface shipsRecyclerInterface) {
            super(itemView);

            //  we will initialize the views here
            shipStatusText = itemView.findViewById(R.id.shipStatusText);
            shipNameText = itemView.findViewById(R.id.shipNameText);
            shipTypeText = itemView.findViewById(R.id.shipTypeText);
            shipImageView = itemView.findViewById(R.id.ship_imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //   make sure the recyclerview interface isn't null
                    if (shipsRecyclerInterface != null) {

                        int pos = getAdapterPosition();

                        //  If the position is valid
                        if (pos != RecyclerView.NO_POSITION) {
                            shipsRecyclerInterface.onItemClick(pos);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    if (shipsRecyclerInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            shipsRecyclerInterface.onItemLongClick(pos);
                        }
                    }

                    return true;
                }
            });
        }
    }
}
