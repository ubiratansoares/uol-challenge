package br.ufs.uolchallenge.data.models;

import com.google.gson.annotations.SerializedName;

import io.reactivex.annotations.Nullable;

/**
 * Created by bira on 11/3/17.
 */

public class NewsContentPayload {

    @SerializedName("title") public String title;
    @SerializedName("share-url") public String shareURL;
    @SerializedName("webview-url") public String webviewURL;
    @SerializedName("updated") public long updatedAt;
    @SerializedName("thumb") @Nullable public String thumbURL;

}
