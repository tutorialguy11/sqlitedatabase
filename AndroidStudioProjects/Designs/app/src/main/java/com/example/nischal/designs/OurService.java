import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.nischal.designs.JsonParsingActivity;
import com.example.nischal.designs.UserInfo;
import com.example.nischal.designs.UserlistAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OurService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        fetchData();
    }

    public void fetchData() {
        String fetchUrl = "http://10.0.2.2/Userinfo/select.php"; //server and emulator in same device/laptop
        AQuery aQuery = new AQuery(this);

        aQuery.ajax(fetchUrl, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray array, AjaxStatus status) {
                super.callback(url, array, status);
                Log.i("response", "response" + array);

                ArrayList<UserInfo> list = new ArrayList<>();

                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);
                        UserInfo info = new UserInfo();
                        info.id = object.getString("id");
                        info.username = object.getString("username");
                        info.password = object.getString("password");
                        info.email = object.getString("email");
                        info.address = object.getString("address");
                        info.phone = object.getString("phone");
                        list.add(info);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

    }
}
