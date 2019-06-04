package com.applozic.mobicomkit.uiwidgets.kommunicate.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import com.applozic.mobicomkit.uiwidgets.kommunicate.models.KmApiResponse;
import com.applozic.mobicomkit.uiwidgets.kommunicate.models.KmAutoSuggestionModel;
import com.applozic.mobicomkit.uiwidgets.kommunicate.services.KmService;
import com.applozic.mobicommons.json.GsonUtils;

import java.util.List;

public class KmAutoSuggestionsAsyncTask extends AsyncTask<Void, Void, KmApiResponse<List<KmAutoSuggestionModel>>> {

    private KmService kmService;
    private KmAutoSuggestionListener listener;

    public KmAutoSuggestionsAsyncTask(Context context, KmAutoSuggestionListener listener) {
        this.listener = listener;
        kmService = new KmService(context);
    }

    @Override
    protected KmApiResponse<List<KmAutoSuggestionModel>> doInBackground(Void... voids) {
        return kmService.getKmAutoSuggestions();
    }

    @Override
    protected void onPostExecute(KmApiResponse<List<KmAutoSuggestionModel>> apiResponse) {
        super.onPostExecute(apiResponse);

        if (listener != null) {
            if (apiResponse != null) {
                if (KmApiResponse.KM_AUTO_SUGGESSTION_SUCCESS_RESPONSE.equals(apiResponse.getCode())) {
                    listener.onSuccess(apiResponse.getData());
                } else {
                    listener.onFailure(apiResponse.getData() != null ? GsonUtils.getJsonFromObject(apiResponse.getData().toArray(), KmAutoSuggestionModel[].class) : "Some error occurred");
                }
            } else {
                listener.onFailure("Some error occurred");
            }
        }
    }

    public interface KmAutoSuggestionListener {
        void onSuccess(List<KmAutoSuggestionModel> autoSuggestionList);

        void onFailure(String error);
    }
}
