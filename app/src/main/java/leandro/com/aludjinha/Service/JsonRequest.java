package leandro.com.aludjinha.Service;

import android.content.Context;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import android.support.v4.util.ArrayMap;

/**
 * Created by Leandro.Araujo on 30/10/17.
 */

public class JsonRequest {

    private static final int MY_SOCKET_TIMEOUT_MS = 3000;

    public static void jsonObjectRequest(Context context, int method, final String url, JSONObject jsonObject, final ArrayMap<String, String> header, final PostCommentResponseListener responseListener){
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(method,url, jsonObject, response -> responseListener.requestCompleted(response.toString(), url), error -> {
            try {
                String mError = error.getCause().getMessage();
                if(mError != null) {
                    if(mError.contains("Unable to resolve host")){
                        responseListener.requestError("Verifique sua conexão de internet!", url);
                    } else {
                        responseListener.requestError(error.getCause().getMessage(), url);

                    }
                }
                else{
                    throw new Exception();
                }
            }
            catch (Exception e){
                if (error.toString().contains("TimeoutError"))
                {
                    responseListener.requestError("Sua conexão está intermitente. Verifique e tente novamente!", url);
                }
                else if(error.toString().contains("AuthFailureError")){
                    responseListener.requestError("Não foi possível autenticar. Verifique seus dados e tente novamente!", url);
                }
                else {
                    responseListener.requestError("Não foi possível realizar esta ação. Tente novamente!", url);
                }
            }
        }){
            @Override
            public ArrayMap<String, String> getHeaders() {
                if (header != null) {
                    return header;
                } else {
                    ArrayMap<String, String> genericHeader = new ArrayMap<>();
                    genericHeader.put("Content-Type", "application/json");
                    return genericHeader;
                }
            }
        };


        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);

    }

    public interface PostCommentResponseListener{

        void requestCompleted(String json, String request);
        void requestError(String error, String request);
    }

}
