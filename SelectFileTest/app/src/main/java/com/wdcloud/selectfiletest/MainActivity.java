package com.wdcloud.selectfiletest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wdcloud.selectfiletest.adapter.SelectFileAdapter;
import com.wdcloud.selectfiletest.adapter.SelectPicAdapter;
import com.wdcloud.selectfiletest.recycleAdapterUtil.BaseQuickAdapter;
import com.wdcloud.selectfiletest.util.DeleteListener;
import com.wdcloud.selectfiletest.util.FilePicChooseUtil;
import com.wdcloud.selectfiletest.util.rxBus.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import droidninja.filepicker.FilePickerConst;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.AsyncProcessor;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static com.wdcloud.selectfiletest.adapter.SelectPicAdapter.clickIndexPic;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {
    private Disposable observer;
    @BindView(R.id.sendmessageactivity_accessory_left_tv)
    TextView sendmessageactivityAccessoryLeftTv;
    @BindView(R.id.sendmessageactivity_accessory_pic_left_tv)
    TextView sendmessageactivityAccessoryPicLeftTv;
    @BindView(R.id.sendmessageactivity_accessory_pic_left_num_tv)
    TextView sendmessageactivityAccessoryPicLeftNumTv;
    @BindView(R.id.sendmessageactivity_accessory_warpgridview)
    RecyclerView sendmessageactivityAccessoryWarpgridview;
    @BindView(R.id.sendmessageactivity_accessory_warpgridview_line)
    TextView sendmessageactivityAccessoryWarpgridviewLine;
    @BindView(R.id.sendmessageactivity_accessory_file_left_tv)
    TextView sendmessageactivityAccessoryFileLeftTv;
    @BindView(R.id.sendmessageactivity_accessory_file_left_num_tv)
    TextView sendmessageactivityAccessoryFileLeftNumTv;
    @BindView(R.id.sendmessageactivity_accessory_file_warpgridview)
    RecyclerView sendmessageactivityAccessoryFileWarpgridview;
    private List<String> list = new ArrayList<>();
    private FilePicChooseUtil chooseUtil;
    private SelectPicAdapter selectPicAdapter;
    private ArrayList<String> selctMedias = new ArrayList<>();
    private List<String> fileBeanList = new ArrayList<>();
    private Unbinder unbind;
    private int max_count = 3;
    private SelectFileAdapter selectFileAdapter;
    private List<String> filelist = new ArrayList<>();
    private ArrayList<String> selectDoclist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbind = ButterKnife.bind(this);
        init();
        registRxbus();
    }

    private void registRxbus() {
        //预览页面广播
        observer = RxBus.getDefault().toFlowable(DeleteListener.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DeleteListener>() {
                    @Override
                    public void accept(DeleteListener deleteListener) throws Exception {
                        int postion = deleteListener.getPostion();
                        removePic(postion);
                    }
                });
    }
    public void disObserver(){
        if (!observer.isDisposed()){
            observer.dispose();
        }
    }
    private void init() {
        list.add(SelectPicAdapter.clickIndexPic);
        filelist.add(SelectFileAdapter.fileclickIndexPic);
        chooseUtil = new FilePicChooseUtil();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        sendmessageactivityAccessoryWarpgridview.setLayoutManager(gridLayoutManager);
        GridLayoutManager filegridLayoutManager = new GridLayoutManager(this, 4);
        filegridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        sendmessageactivityAccessoryFileWarpgridview.setLayoutManager(filegridLayoutManager);
        initpicData();
        initfileData();
    }

    private void initfileData() {
        sendmessageactivityAccessoryFileLeftNumTv.setText("(已添加" + (filelist.size() - 1) + "/3)");
        if (selectFileAdapter == null) {
            selectFileAdapter = new SelectFileAdapter(R.layout.sendmsgaccessorydocadapter_item, filelist, this);
            sendmessageactivityAccessoryFileWarpgridview.setAdapter(selectFileAdapter);
            selectFileAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    int id = view.getId();
                    switch (id) {
                        case R.id.sendmsgaccessorydocadapter_item_linearlayout:
                            startDocSelecView();
                            break;
                        case R.id.sendmsgaccessorydocadapter_item_delete:
                            remove(position);
                            break;
                        case R.id.sendmsgaccessorydocadapter_item_draweeView:
                            break;
                    }
                }
            });
        } else {
            selectFileAdapter.notifyDataSetChanged();
        }
    }

    private void initpicData() {
        sendmessageactivityAccessoryPicLeftNumTv.setText("(已添加" + (list.size() - 1) + "/9)");
        if (selectPicAdapter == null) {
            selectPicAdapter = new SelectPicAdapter(R.layout.addgridview_adapter_item, list, this);
            sendmessageactivityAccessoryWarpgridview.setAdapter(selectPicAdapter);
            selectPicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    int id = view.getId();
                    switch (id) {
                        case R.id.addgridview_adapter_item_linearlayout:
                            MainActivityPermissionsDispatcher.NeedsPermissionWithPermissionCheck(MainActivity.this);
                            startPictureSelecView();
                            break;
                        case R.id.addgridview_adapter_item_delete:
                            removePic(position);
                            Toast.makeText(MainActivity.this, "删除第" + position + "个", Toast.LENGTH_LONG).show();
                            break;
                        case R.id.addgridview_adapter_item_draweeView:
                            startPreview(position);
                            break;
                    }
                }
            });
        } else {

            selectPicAdapter.notifyDataSetChanged();
        }

    }

    @OnClick({R.id.sendmessageactivity_accessory_warpgridview, R.id.sendmessageactivity_accessory_file_warpgridview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sendmessageactivity_accessory_warpgridview:
                break;
            case R.id.sendmessageactivity_accessory_file_warpgridview:
                break;
        }
    }

    @NeedsPermission({Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void NeedsPermission() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void OnShowRationale(final PermissionRequest request) {
    }

    @OnPermissionDenied({Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void OnPermissionDenied() {
    }

    @OnNeverAskAgain({Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void OnNeverAskAgain() {
    }

    public void startPictureSelecView() {
        ArrayList<String> strings = new ArrayList<>();
        strings.addAll(list.subList(0, (list.size() - 1)));
        chooseUtil.setPic_count(9);
        chooseUtil.selectPic(MainActivity.this, strings);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ConstantConfig.SELECT_PIC_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    selctMedias.clear();
                    selctMedias.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                    this.list.clear();
                    list.add(SelectPicAdapter.clickIndexPic);
                    this.list.addAll(0, selctMedias);
                    initpicData();
                }
                break;
            case ConstantConfig.REQUEST_CODE_DOC://文档
                if (resultCode == Activity.RESULT_OK && data != null) {
                    selectDoclist.clear();
                    selectDoclist.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                    this.filelist.clear();
                    this.filelist.add(SelectFileAdapter.fileclickIndexPic);
                    this.filelist.addAll(0, selectDoclist);
                    initfileData();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbind.unbind();
        disObserver();
    }

    private void removePic(int postion) {
        String rePath = list.remove(postion);
        if (selctMedias.contains(rePath)) {
            selctMedias.remove(rePath);
        }
        initpicData();
    }

    private void startPreview(int position) {
        ArrayList<String> previewList = new ArrayList<>();
        previewList.addAll(list.subList(0, list.size() - 1));
        Intent intent = new Intent(this, PreviewActivity.class);
        intent.putExtra(PreviewActivity.POSTION, position);
        intent.putStringArrayListExtra(PreviewActivity.DATA_LIST, previewList);
        this.startActivity(intent);
    }

    public void startDocSelecView() {
        ArrayList<String> strings = new ArrayList<>();
        strings.addAll(filelist.subList(0, (filelist.size() - 1)));
        chooseUtil.setPic_count(max_count);
        chooseUtil.selectFile(this, strings);
    }

    private void remove(int position) {
        String rePath = filelist.remove(position);
//        if (netDocPathList.contains(rePath)){
//            netDocBeanList.remove(netDocPathList.indexOf(rePath));
//            netDocPathList.remove(rePath);
//        }
        if (selectDoclist.contains(rePath)) {
            selectDoclist.remove(rePath);
        }
        initfileData();
    }

}
