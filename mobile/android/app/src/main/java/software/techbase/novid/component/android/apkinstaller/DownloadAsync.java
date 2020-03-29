package software.techbase.novid.component.android.apkinstaller;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Wai Yan on 3/19/20.
 */
public class DownloadAsync extends AsyncTask<String, Integer, String> {

    private String fileName;
    private DownloadAsyncListener listener;

    DownloadAsync(String fileName, DownloadAsyncListener listener) {
        this.fileName = fileName;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.listener.onStartDownload();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    protected String doInBackground(String... url) {

        String downloadedFilePath;

        try {
            URL urlStr = new URL(url[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlStr.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();

            String PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, fileName);

            if (outputFile.exists()) {
                outputFile.delete();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            InputStream inputStream = httpURLConnection.getInputStream();

            int totalSize = httpURLConnection.getContentLength();
            byte[] buffer = new byte[1024];
            int length;
            int per;
            int downloaded = 0;
            while ((length = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, length);
                downloaded += length;
                per = downloaded * 100 / totalSize;
                publishProgress(per);
            }
            fileOutputStream.close();
            inputStream.close();

            downloadedFilePath = outputFile.getAbsolutePath();

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            downloadedFilePath = null;
        } catch (IOException ex) {
            downloadedFilePath = null;
            ex.printStackTrace();
        }

        return downloadedFilePath;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        this.listener.onProgressUpdate(progress[0]);
    }

    @Override
    protected void onPostExecute(String downloadedFilePath) {
        super.onPostExecute(downloadedFilePath);
        listener.onDownloaded(downloadedFilePath);
    }

    public interface DownloadAsyncListener {

        void onStartDownload();

        void onProgressUpdate(int progress);

        void onDownloaded(String downloadedFilePath);
    }
}
