package me.iwf.photopicker.utils;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by root on 2015/11/22.
 * 保存图片
 */
public class SaveBitmap {
    private static final String cacheDir = Settings.imageSavePath;

    public static String saveBitmap(Bitmap bm) {
        File dir = new File(cacheDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String imagePath = "";
        Log.e("TAG", "保存图片");
        String imagename = System.currentTimeMillis() + "";
        File f = new File(cacheDir, "Smk_" + imagename + ".jpg");
        imagePath = cacheDir + "Smk_" + imagename + ".jpg";
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            Log.i("TAG", "已经保存");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return imagePath;
    }
}
