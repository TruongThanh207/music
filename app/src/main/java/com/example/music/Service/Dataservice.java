package com.example.music.Service;

import com.example.music.Model.Album;
import com.example.music.Model.BaiHat;
import com.example.music.Model.ChuDe;
import com.example.music.Model.ChuDevaTheLoai;
import com.example.music.Model.CustomTheloai;
import com.example.music.Model.Playlist;
import com.example.music.Model.Quangcao;
import com.example.music.Model.TheLoai;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Dataservice {


    @GET("songbaner.php")
    Call<List<Quangcao>> GetdataBanner();

    @GET("playlist.php")
    Call<List<Playlist>> GetPlaylist();

    @GET("theloai.php")
    Call<List<CustomTheloai>> GetcustomTheloai();

    @GET("theloaichude..php")
    Call<ChuDevaTheLoai> GetDataChuDeVaTheLoai();

    @GET("album.php")
    Call<List<Album>> GetAlbum();

    @GET("baihatyeuthich.php")
    Call<List<BaiHat>> GetBaiHatHot();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> Getdanhsachbaihattheoquangcao(@Field("idquangcao") String idquangcao);

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> Getdanhsachbaihattheoplaylist(@Field("idplaylist") String idplaylist);

    @GET("danhsachcacplaylist.php")
    Call<List<Playlist>> GetListPlaylist();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> Getdanhsachbaihattheotheloai(@Field("idtheloai") String idtheloai);

    @GET("danhsachcacchude.php")
    Call<List<ChuDe>> Getdanhsachchude();

    @FormUrlEncoded
    @POST("danhsachtheloai.php")
    Call<List<TheLoai>> Getalltheloai(@Field("idchude") String idchude);

    @GET("danhsachalbum.php")
    Call<List<Album>> GetdanhsachAlbum();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> Getdanhsachbaihattheoalbum(@Field("idalbum") String idalbum);

    @FormUrlEncoded
    @POST("updatelike.php")
    Call<String> Updatelike(@Field("luotthich") String luotthich, @Field("idbaihat") String idbaihat);
}
