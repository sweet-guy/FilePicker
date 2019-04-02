package com.wdcloud.selectfiletest.util;




import android.app.Activity;

import com.wdcloud.selectfiletest.ConstantConfig;
import com.wdcloud.selectfiletest.R;

import java.util.ArrayList;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.fragments.BaseFragment;
import droidninja.filepicker.models.sort.SortingTypes;
import droidninja.filepicker.utils.Orientation;

/**
 * Created by Umbrella on 2018/7/5.
 */

public class FilePicChooseUtil {
    private int pic_count = 9;
    private int file_count = 3;
    private String pic_hint = "请选择图片";
    private String file_hint = "请选择文件";

    public void setPic_count(int pic_count) {
        this.pic_count = pic_count;
    }

    public int getPic_count() {
        return pic_count;
    }

    public void setFile_count(int file_count) {
        this.file_count = file_count;
    }

    public void setPic_hint(String pic_hint) {
        this.pic_hint = pic_hint;
    }

    public void setFile_hint(String file_hint) {
        this.file_hint = file_hint;
    }

    public void selectPic(Activity activity, ArrayList<String> picPaths) {
        FilePickerBuilder.getInstance()
                .setMaxCount(pic_count)
                .setSelectedFiles(picPaths)
                .setActivityTheme(R.style.FilePickerTheme)
                .setActivityTitle(pic_hint)
                .enableVideoPicker(false)
                .enableCameraSupport(true)
                .showGifs(false)
                .showFolderView(false)
                .enableSelectAll(true)
                .enableImagePicker(true)
                .setCameraPlaceholder(R.mipmap.camera_white)
                .withOrientation(Orientation.UNSPECIFIED)
                .pickPhoto(activity, ConstantConfig.SELECT_PIC_REQUEST_CODE);
    }

    public void selectPic(BaseFragment fragment, ArrayList<String> picPaths) {
        FilePickerBuilder.getInstance()
                .setMaxCount(pic_count)
                .setSelectedFiles(picPaths)
                .setActivityTheme(R.style.FilePickerTheme)
                .setActivityTitle(pic_hint)
                .enableVideoPicker(false)
                .enableCameraSupport(true)
                .showGifs(false)
                .showFolderView(false)
                .enableSelectAll(true)
                .enableImagePicker(true)
                .setCameraPlaceholder(R.mipmap.camera_white)
                .withOrientation(Orientation.UNSPECIFIED)
                .pickPhoto(fragment, ConstantConfig.SELECT_PIC_REQUEST_CODE);

    }

    public void selectPic(BaseFragment fragment) {
        selectPic(fragment,null);
    }


    public void selectPic(Activity activity) {
        selectPic(activity,null);
    }

    public void selectFile(Activity activity, ArrayList<String> docPaths) {
        String[] words = { ".docx",".xls"};
        String[] pdfs = { ".pdf" ,".ppt"};
        FilePickerBuilder.getInstance()
                .setMaxCount(file_count)
                .setSelectedFiles(docPaths)
                .setActivityTheme(R.style.FilePickerTheme)
                .setActivityTitle(file_hint)
                .addFileSupport("World/Excel", words)
                .addFileSupport("PDF/PPT", pdfs, R.mipmap.pdf_blue)
                .enableDocSupport(false)
                .enableSelectAll(true)
                .sortDocumentsBy(SortingTypes.name)
                .withOrientation(Orientation.UNSPECIFIED)
                .pickFile(activity,ConstantConfig.REQUEST_CODE_DOC);
    }
}
