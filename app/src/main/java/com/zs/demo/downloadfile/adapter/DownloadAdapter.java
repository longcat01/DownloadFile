package com.zs.demo.downloadfile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zs.demo.downloadfile.ListActivity;
import com.zs.demo.downloadfile.R;
import com.zs.demo.downloadfile.download.DownloadInfo;

import java.util.List;

/**
 * Created by zs
 * Date：2018年 09月 11日
 * Time：18:06
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.UploadHolder>  {

    private Context mContext;
    private List<DownloadInfo> mdata;
    private long mTime;

    public DownloadAdapter(Context mContext, List<DownloadInfo> mdata) {
        this.mContext = mContext;
        this.mdata = mdata;
    }

    /**
     * 更新下载进度
     * @param info
     */
    public void updateProgress(DownloadInfo info){
        for (int i = 0; i < mdata.size(); i++){
            if (mdata.get(i).getUrl().equals(info.getUrl())){
                mdata.set(i,info);
                notifyItemChanged(i);
                // 修改刷新的时间间隔
//                if (System.currentTimeMillis() - mTime > 1000){
//                    mTime = System.currentTimeMillis();
//                    notifyItemChanged(i);
//                }
                break;
            }
        }
    }

    /**
     * 更新下载状态
     * @param info
     */
    public void updateStatus(DownloadInfo info){
        for (int i = 0; i < mdata.size(); i++){
            if (mdata.get(i).getUrl().equals(info.getUrl())){
                mdata.set(i,info);
                notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    public UploadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_download_layout,null);
        return new UploadHolder(view);
    }

    @Override
    public void onBindViewHolder(UploadHolder holder, int position) {

        final DownloadInfo info = mdata.get(position);
        if (info.getTotal() == 0){
            holder.main_progress.setProgress(0);
        }else{
            float d = info.getProgress() * holder.main_progress.getMax() / info.getTotal();
            holder.main_progress.setProgress((int) d);
        }
        holder.main_btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListActivity)mContext).download(info.getUrl());
            }
        });

        holder.main_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListActivity)mContext).cancelDownload(info.getUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class UploadHolder extends RecyclerView.ViewHolder{

        private ProgressBar main_progress;
        private TextView main_btn_down;
        private TextView main_btn_cancel;

        public UploadHolder(View itemView) {
            super(itemView);
            main_progress = itemView.findViewById(R.id.main_progress);
            main_btn_down = itemView.findViewById(R.id.main_btn_down);
            main_btn_cancel = itemView.findViewById(R.id.main_btn_cancel);
        }
    }

}