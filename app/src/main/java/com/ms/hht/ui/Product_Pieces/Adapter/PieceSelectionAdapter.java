package com.ms.hht.ui.Product_Pieces.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ms.hht.R;
import com.ms.hht.data.response.PieceSelectionResponse;
import com.ms.hht.ui.Product_Pieces.Activity.ProductPieceSelecton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class PieceSelectionAdapter extends RecyclerView.Adapter<PieceSelectionAdapter.MyViewHolder> {

    Context context;
    int number ;
    List<PieceSelectionResponse.DataItem> pieceData;

    public PieceSelectionAdapter(Context context, List<PieceSelectionResponse.DataItem> pieceData) {
        this.context = context;
        this.pieceData = pieceData;
    }

    @NonNull
    @Override
    public PieceSelectionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.piece_selection_item_file, parent, false);
        return new PieceSelectionAdapter.MyViewHolder(view1);

    }

    @Override
    public void onBindViewHolder(@NonNull PieceSelectionAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.PieceTypeName.setText(pieceData.get(position).getName());

        if (pieceData.get(position).isPieceSelected()){
            holder.pieceCheck.setVisibility(View.VISIBLE);
            try {
                JSONObject jo = new JSONObject((Map) pieceData.get(position).getObject_data());
                JSONArray ja = jo.getJSONArray("features");
                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                System.out.println(jo);// new JSONArray(jo.get("features"));
                for (int i=0 ; i<ja.length() ; i++){
                   JSONObject jsonObject = (JSONObject) ja.get(i);
                   if (jsonObject.getString("feature_name").equalsIgnoreCase("Lining")){
                       holder.secondRelativeLay.setVisibility(View.VISIBLE);
                       JSONObject jso = (JSONObject) jsonObject.get("selected_value");
                       holder.liningName.setText("Lining: "+jso.getString("fabric_name"));
                       Glide.with(context).load(Uri.parse(jso.getString("fabric_image"))).into(holder.liningImage);
                   }
                   if (jsonObject.getString("feature_name").equalsIgnoreCase("Fabric")){
                        holder.firstRelativeLay.setVisibility(View.VISIBLE);
                        JSONObject jso = (JSONObject) jsonObject.get("selected_value");
                        holder.fabricName.setText("Fabric: "+jso.getString("fabric_name"));
                        Glide.with(context).load(Uri.parse(jso.getString("fabric_image"))).into(holder.fabricImage);
                    }

                }
//                if (ja.getString(""))
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        else {
            holder.pieceCheck.setVisibility(View.INVISIBLE);
        }
        for (int k=0 ; k<pieceData.size() ; k++){
            if (!pieceData.get(k).isPieceSelected()){
                ProductPieceSelecton.pieceSelectonBinding.PieceCustomizeBtn.setText("Customize");
                break;
            }
            else {
                ProductPieceSelecton.pieceSelectonBinding.PieceCustomizeBtn.setText("Add to cart");
            }
        }
    }
    @Override
    public int getItemCount() {
        return pieceData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout firstRelativeLay,secondRelativeLay;
        TextView  PieceTypeName,fabricName,liningName;
        ImageView pieceCheck,fabricImage,liningImage;
        LinearLayout PieceItemCArd;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            PieceItemCArd = itemView.findViewById(R.id.PieceItemCArd);
            PieceTypeName = itemView.findViewById(R.id.PieceTypeName);
            firstRelativeLay = itemView.findViewById(R.id.firstRelativeLay);
            secondRelativeLay = itemView.findViewById(R.id.secondRelativeLay);
            fabricName = itemView.findViewById(R.id.fabricName);
            liningName = itemView.findViewById(R.id.liningName);
            fabricImage = itemView.findViewById(R.id.fabricImage);
            liningImage = itemView.findViewById(R.id.liningImage);
            pieceCheck = itemView.findViewById(R.id.pieceCheck);
        }
    }
}




