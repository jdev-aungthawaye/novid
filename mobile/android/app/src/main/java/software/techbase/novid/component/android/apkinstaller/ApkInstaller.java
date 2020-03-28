package software.techbase.novid.component.android.apkinstaller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;

import java.io.File;
import java.text.MessageFormat;

import software.techbase.novid.R;
import software.techbase.novid.component.android.xlogger.XLoggerKt;

/**
 * Created by Wai Yan on 3/19/20.
 */
public class ApkInstaller {

    public static void install(Context mContext,
                               @NonNull String url,
                               @NonNull String fileName) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_download_progress, null);
        AlertDialog mDialog = new AlertDialog
                .Builder(mContext)
                .setView(view)
                .setCancelable(false)
                .create();

        AppCompatTextView lblDownloadStatus = view.findViewById(R.id.lblDownloadStatus);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        new DownloadAsync(fileName, new DownloadAsync.DownloadAsyncListener() {

            @Override
            public void onStartDownload() {
                mDialog.show();
            }

            @Override
            public void onProgressUpdate(int progress) {
                lblDownloadStatus.setText(MessageFormat.format("Downloading...{0} %", progress));
                progressBar.setIndeterminate(false);
                progressBar.setProgress(progress);
            }

            @Override
            public void onDownloaded(String downloadedFilePath) {
                mDialog.hide();
                if (downloadedFilePath != null) {
                    ApkInstaller.openNewVersion(mContext, downloadedFilePath);
                } else {
                    XLoggerKt.debug("Download error.");
                }
            }
        }).execute(url);
    }

    private static void openNewVersion(Context mContext, String fileName) {

        Activity activity = (Activity) mContext;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(ApkInstaller.getUriFromFile(mContext, fileName), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        mContext.startActivity(intent);
        activity.finish();
    }

    private static Uri getUriFromFile(Context mContext, String fileName) {

        if (Build.VERSION.SDK_INT < 24) {
            return Uri.fromFile(new File(fileName));
        } else {
            return FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", new File(fileName));
        }
    }
}
