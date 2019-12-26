package com.example.music.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Playlist implements Serializable {

@SerializedName("isPlaylist")
@Expose
private String isPlaylist;
@SerializedName("Ten")
@Expose
private String ten;
@SerializedName("HinhNen")
@Expose
private String hinhNen;
@SerializedName("Icon")
@Expose
private String icon;

public String getIsPlaylist() {
return isPlaylist;
}

public void setIsPlaylist(String isPlaylist) {
this.isPlaylist = isPlaylist;
}

public String getTen() {
return ten;
}

public void setTen(String ten) {
this.ten = ten;
}

public String getHinhNen() {
return hinhNen;
}

public void setHinhNen(String hinhNen) {
this.hinhNen = hinhNen;
}

public String getIcon() {
return icon;
}

public void setIcon(String icon) {
this.icon = icon;
}

}