package com.example.music.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.music.Acticity.DanhSachBaiHatActivity;
import com.example.music.Acticity.DanhSachChuDeActivity;
import com.example.music.Model.ChuDevaTheLoai;
import com.example.music.Model.CustomTheloai;
import com.example.music.Model.TheLoai;
import com.example.music.R;
import com.example.music.Service.APIservice;
import com.example.music.Service.Dataservice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_chude_theloai extends Fragment {

    View view;
    ChuDevaTheLoai chuDevaTheLoai;
    HorizontalScrollView horizontalScrollView;
    TextView textxemthemchudetheloai;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chude_theloai, container, false);
        horizontalScrollView = view.findViewById(R.id.horizontalchudetheloai);
        textxemthemchudetheloai = view.findViewById(R.id.textxemthemchudetheloai);
        textxemthemchudetheloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhSachChuDeActivity.class);
                startActivity(intent);
            }
        });
        Getdata();
        return view;
    }

    private void Getdata() {
        Dataservice dataservice = APIservice.getService();
        Call<List<CustomTheloai>> callback = dataservice.GetcustomTheloai();
        callback.enqueue(new Callback<List<CustomTheloai>>() {
            @Override
            public void onResponse(Call<List<CustomTheloai>> call, Response<List<CustomTheloai>> response) {
                final ArrayList<CustomTheloai> mangcustomtheloai = (ArrayList<CustomTheloai>) response.body();
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(600,400);
                layout.setMargins(10,10,10,20);
                for (int j=0; j<mangcustomtheloai.size(); j++) {
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if (mangcustomtheloai.get(j).getHinhTheLoai() != null) {
                        Picasso.with(getActivity()).load(mangcustomtheloai.get(j).getHinhTheLoai()).into(imageView);
                    }
                    cardView.setLayoutParams(layout);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    final int finalJ = j;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), DanhSachBaiHatActivity.class);
                            intent.putExtra("theloai", mangcustomtheloai.get(finalJ));
                            startActivity(intent);
                        }
                    });
                }
                horizontalScrollView.addView(linearLayout);
            }

            @Override
            public void onFailure(Call<List<CustomTheloai>> call, Throwable t) {

            }
        });
    }
       /*{
            @Override
            public void onResponse(Call<ChuDevaTheLoai> call, Response<ChuDevaTheLoai> response) {
                chuDevaTheLoai = response.body();

                final ArrayList<TheLoai>  arrayTheLoai = new ArrayList<>();
                arrayTheLoai.addAll(chuDevaTheLoai.getTheLoai());

                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(580,250);
                layout.setMargins(10,10,10,20);

                for (int j=0; j<arrayTheLoai.size(); j++)
                {
                    CardView cardView = new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if(arrayTheLoai.get(j).getHinhTheLoai()!=null)
                    {
                        Picasso.with(getActivity()).load(arrayTheLoai.get(j).getHinhTheLoai()).into(imageView);
                    }
                    cardView.setLayoutParams(layout);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    final int finalJ = j;
                   imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), DanhSachBaiHatActivity.class);
                            intent.putExtra("theloai", arrayTheLoai.get(finalJ));
                            startActivity(intent);
                        }
                    });
                }
                horizontalScrollView.addView(linearLayout);


            }

            @Override
            public void onFailure(Call<ChuDevaTheLoai> call, Throwable t) {

            }
        });*/



}
