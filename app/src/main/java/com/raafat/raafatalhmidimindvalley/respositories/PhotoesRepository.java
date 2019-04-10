package com.raafat.raafatalhmidimindvalley.respositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.raafat.filedownloader.core.JsonArrayRequest;
import com.raafat.filedownloader.core.RequestQueue;
import com.raafat.filedownloader.core.Response;
import com.raafat.raafatalhmidimindvalley.models.Category;
import com.raafat.raafatalhmidimindvalley.models.Photo;
import com.raafat.raafatalhmidimindvalley.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raafat Alhoumaidy on 4/10/2019 @5:00 PM.
 */
public class PhotoesRepository {
    public static final String API_URL = "http://pastebin.com/raw/wgkJgazE";

    private static PhotoesRepository mInstance;
    private MutableLiveData<APIResponse> photesResponse = new MutableLiveData<>();

    public static PhotoesRepository getInstance() {
        if (mInstance == null)
            mInstance = new PhotoesRepository();
        return mInstance;
    }

    public LiveData<APIResponse> getPhotoesList() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                API_URL,
                new Response.SuccessListener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<Photo> photos = new ArrayList<>();
                            for (int i = 0; i < response.length(); ++i) {
                                JSONObject itemJO = response.getJSONObject(i);
                                Photo item = new Photo();
                                item.setId(itemJO.getString("id"));
                                item.setCreatedAt(itemJO.getString("created_at"));
                                item.setWidth(itemJO.getInt("width"));
                                item.setHeight(itemJO.getInt("height"));
                                item.setColor(itemJO.getString("color"));
                                item.setLikes(itemJO.getInt("likes"));
                                item.setLikedByUser(itemJO.getBoolean("liked_by_user"));

                                Photo.Link photoLinks = new Photo.Link();
                                JSONObject photoLinksJO = itemJO.getJSONObject("links");
                                photoLinks.setSelf(photoLinksJO.getString("self"));
                                photoLinks.setDownload(photoLinksJO.getString("download"));
                                photoLinks.setHtml(photoLinksJO.getString("html"));
                                item.setLink(photoLinks);

                                User photoUser = new User();
                                JSONObject userJO = itemJO.getJSONObject("user");
                                photoUser.setId(userJO.getString("id"));
                                photoUser.setUserName(userJO.getString("username"));
                                photoUser.setFullName(userJO.getString("name"));

                                User.ProfileImage profileImage = new User.ProfileImage();
                                profileImage.setLarge(userJO.getJSONObject("profile_image").getString("large"));
                                profileImage.setMedium(userJO.getJSONObject("profile_image").getString("medium"));
                                profileImage.setSmall(userJO.getJSONObject("profile_image").getString("small"));
                                photoUser.setProfileImage(profileImage);

                                User.Link userLinks = new User.Link();
                                userLinks.setSelf(userJO.getJSONObject("links").getString("self"));
                                userLinks.setHtml(userJO.getJSONObject("links").getString("html"));
                                userLinks.setPhotos(userJO.getJSONObject("links").getString("photos"));
                                userLinks.setLikes(userJO.getJSONObject("links").getString("likes"));
                                photoUser.setLink(userLinks);

                                item.setUser(photoUser);

                                Photo.Url photUrls = new Photo.Url();
                                photUrls.setThumb(itemJO.getJSONObject("urls").getString("thumb"));
                                photUrls.setSmall(itemJO.getJSONObject("urls").getString("small"));
                                photUrls.setRegular(itemJO.getJSONObject("urls").getString("regular"));
                                photUrls.setFull(itemJO.getJSONObject("urls").getString("full"));
                                photUrls.setRaw(itemJO.getJSONObject("urls").getString("raw"));
                                item.setUrls(photUrls);

                                JSONArray categoriesJA = itemJO.getJSONArray("categories");
                                List<Category> categories = new ArrayList<>();
                                for (int j = 0; j < categoriesJA.length(); j++) {
                                    Category categoryItem = new Category();
                                    categoryItem.setId(categoriesJA.getJSONObject(j).getInt("id"));
                                    categoryItem.setTitle(categoriesJA.getJSONObject(j).getString("title"));
                                    categoryItem.setPhotoCount(categoriesJA.getJSONObject(j).getInt("photo_count"));

                                    Category.Link categoryLink = new Category.Link();
                                    categoryLink.setPhotos(categoriesJA.getJSONObject(j).getJSONObject("links").getString("photos"));
                                    categoryLink.setSelf(categoriesJA.getJSONObject(j).getJSONObject("links").getString("self"));
                                    categoryItem.setLinks(categoryLink);

                                    categories.add(categoryItem);
                                }
                                item.setCategories(categories);

                                photos.add(item);

                            }

                            photesResponse.postValue(new APIResponse(photos));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            photesResponse.postValue(new APIResponse(e));
                        }
                    }
                }, new Response.ErrorListner() {
            @Override
            public void onErrorResponse(Exception error) {
                photesResponse.postValue(new APIResponse(error));
            }
        });

        RequestQueue.getInstance().add(jsonArrayRequest);

        return photesResponse;
    }

    public static class APIResponse {

        private List<Photo> photos;
        private Throwable error;

        public APIResponse(List<Photo> photos) {
            this.photos = photos;
            this.error = null;
        }

        public APIResponse(Throwable error) {
            this.error = error;
            this.photos = null;
        }

        public List<Photo> getPhotos() {
            return photos;
        }

        public void setPhotos(List<Photo> photos) {
            this.photos = photos;
        }

        public Throwable getError() {
            return error;
        }

        public void setError(Throwable error) {
            this.error = error;
        }
    }

}
